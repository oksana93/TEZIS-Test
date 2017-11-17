package com.company.tezistest.core.app;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.app.ClusterManagerAPI;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.security.app.Authentication;
import com.haulmont.thesis.core.app.workflow.ThesisWfEngineAPI;
import com.haulmont.workflow.core.entity.Assignment;
import com.haulmont.workflow.core.entity.Card;
import com.haulmont.workflow.core.entity.TimerEntity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@ManagedBean(UpdateTimer.NAME)
public class UpdateTimerBean implements UpdateTimer {
    private static final String TIME_UNIT_MINUTE = "minute";
    private static final String TIME_UNIT_HOUR = "hour";
    private static final String TIME_UNIT_DAY = "day";
    private static final String TIME_UNIT_MONTH = "month";
    private static final String TIME_UNIT_YEAR = "year";

    protected Log log = LogFactory.getLog(UpdateTimer.class);

    @Inject
    protected ClusterManagerAPI clusterManager;
    @Inject
    protected Authentication authentication;
    @Inject
    protected Persistence persistence;
    @Inject
    protected TimeSource timeSource;
    @Inject
    protected ThesisWfEngineAPI wfEngine;
    @Inject
    protected Messages messages;
    @Inject
    protected UserSessionSource userSessionSource;

    @Override
    public void cancelProcByCard(String procCode, Integer processDueDate, String timeUnitForProcessDueDate, String moduleName) {

        if (!AppContext.isStarted() || !clusterManager.isMaster())
            return;
        authentication.begin();
        Transaction tx = persistence.createTransaction();
        try {
            EntityManager em = persistence.getEntityManager();
            Date oldDueDate = addDateInterval(timeSource.currentTimestamp(), -processDueDate, timeUnitForProcessDueDate);
            Date oldOldDueDate = addDateInterval(timeSource.currentTimestamp(), -2 * processDueDate, timeUnitForProcessDueDate);

            String queryString = " SELECT a.card " +
                    " FROM wf$Assignment a " +
                    " WHERE a.deletedBy IS NULL " +
                    " AND a.finished IS NULL " +
                    " AND a.proc.code = :procCode " +
                    " AND a.name = :moduleName " +
                    " AND a.createTs < :oldDueDate " +
                    " GROUP BY a.card";
            Query query = em.createQuery(queryString, Assignment.class);
            query.setParameter("procCode", procCode);
            query.setParameter("moduleName", moduleName);
            query.setParameter("oldDueDate", oldDueDate);
            List<Card> cardExecutingByProc = query.getResultList(); // -1-

            if (CollectionUtils.isNotEmpty(cardExecutingByProc)) {
                queryString = " SELECT a.card " +
                        " FROM wf$Assignment a " +
                        " WHERE a.deletedBy IS NULL " +
                        " AND a.proc.code = :procCode " +
                        " AND a.name = :moduleName " +
                        " AND a.createTs < :oldDueDate " +
                        " AND (a.createTs > :oldOldDueDate " +
                        " AND a.updateTs > :createTs " +
                        " OR  a.updateTs > :oldDueDate) " +
                        " AND a.card in (:cardExecutingByProc) " +
                        " GROUP BY a.card";
                query = em.createQuery(queryString, Assignment.class);
                query.setParameter("procCode", procCode);
                query.setParameter("moduleName", moduleName);
                query.setParameter("oldOldDueDate", oldOldDueDate);
                query.setParameter("oldDueDate", oldDueDate);
                query.setParameter("cardExecutingByProc", cardExecutingByProc);
                List<Card> cardExecutingByProcAndSuccessOutcome = query.getResultList(); // -2-

                if (CollectionUtils.isNotEmpty(cardExecutingByProcAndSuccessOutcome)) {
                    queryString = " SELECT a " +
                            " FROM wf$Assignment a " +
                            " WHERE a.finished IS NULL " +
                            " AND a.name = :moduleName " +
                            " AND a.card IN (:cards) ";
                    query = em.createQuery(queryString, Assignment.class);
                    query.setParameter("moduleName", moduleName);
                    query.setParameter("cardExecutingByProcAndSuccessOutcome", cardExecutingByProcAndSuccessOutcome);
                    List<Assignment> assignments = query.getResultList(); // -3-
                    for (Assignment assignment : assignments) {
                        Date newDueDate = addDateInterval(assignment.getDueDate(), -processDueDate, timeUnitForProcessDueDate);
                        assignment.setDueDate(newDueDate);
                        em.merge(assignment);

                        queryString = " UPDATE wf$Timer t " +
                                " SET t.dueDate = :newDueDate" +
                                " WHERE t.card = :card";
                        query = em.createQuery(queryString, TimerEntity.class);
                        query.setParameter("newDueDate", newDueDate);
                        query.setParameter("card", assignment.getCard());
                        query.executeUpdate();
                        tx.commit();


                        cardExecutingByProc.removeAll(cardExecutingByProcAndSuccessOutcome);
                    }
                    // -4-
                }
                if (CollectionUtils.isNotEmpty(cardExecutingByProc)) {
                    for (Card card : cardExecutingByProc) {
                        try {
                            wfEngine.cancelProcessInCard(card, messages.getMainMessage("OverdueCanceled"));
                           log.info("Process for card: " + card.getId() + " was canceled");
                        } catch (Exception e) {
                            log.error("Could not cancel process for card: " + card.getId() + " by reason: " + e.getMessage());
                            throw e;
                        }
                    }
                    tx.commitRetaining();
                }
            }
        } finally {
            tx.end();
            authentication.end();
        }
    }

    private Date addDateInterval(Date date, Integer interval, String timeUnit) {
        Date newDate;
        switch (timeUnit) {
            case TIME_UNIT_MINUTE:
                newDate = DateUtils.addMinutes(date, interval);
                break;
            case TIME_UNIT_HOUR:
                newDate = DateUtils.addHours(date, interval);
                break;
            case TIME_UNIT_DAY:
                newDate = DateUtils.addDays(date, interval);
                break;
            case TIME_UNIT_MONTH:
                newDate = DateUtils.addMonths(date, interval);
                break;
            case TIME_UNIT_YEAR:
                newDate = DateUtils.addYears(date, interval);
                break;
            default:
                newDate = DateUtils.addMonths(date, interval);
                break;
        }
        return newDate;
    }
}
