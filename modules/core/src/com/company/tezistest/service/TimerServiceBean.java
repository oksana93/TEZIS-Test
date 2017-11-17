package com.company.tezistest.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.workflow.core.entity.TimerEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

@Service(TimerService.NAME)
public class TimerServiceBean implements TimerService {

    @Inject
    protected Persistence persistence;

    protected String selectTimerStr = "select t from wf$Timer t where t.card.id= ?1";
    protected String updateTimerStr = "update wf$Timer t set t.dueDate= ?1 where t.card.id = ?2";
    protected final int DAYS = 0;
    protected final int HOURS = 0;
    protected final int MINUTES = 5;

    protected Date updateDueDates(Date oldDueDate) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(oldDueDate);
        calendar.add(Calendar.MINUTE, MINUTES);
        calendar.add(Calendar.HOUR, HOURS);
        calendar.add(Calendar.DAY_OF_MONTH, DAYS);
        return calendar.getTime();
    }

    @Override
    public void updateTimerByCardId(UUID cardId) throws ParseException {
        Transaction tx = persistence.createTransaction();
        try {
            EntityManager entityManager = persistence.getEntityManager();

            Query q = entityManager.createQuery(selectTimerStr).setParameter(1, cardId);
            TimerEntity timer = (TimerEntity) q.getFirstResult();
            if (timer != null) {
                Date newDueDates = updateDueDates(timer.getDueDate());
                q = entityManager.createQuery(updateTimerStr)
                        .setParameter(1, newDueDates)
                        .setParameter(2, cardId);
                q.executeUpdate();
                tx.commit();
            }
        } finally {
            tx.end();
        }
    }

//    @Override
//    public void updateTimerByAssignment(Assignment assignment) {
//        Transaction tx = persistence.createTransaction();
//        try {
//            EntityManager entityManager = persistence.getEntityManager();
//            Date oldDueDates = assignment.getDueDate();
//            Date newDueDates = updateDueDates(oldDueDates);
//            Query q = entityManager.createQuery(updateTimerStr)
//                    .setParameter(1, newDueDates)
//                    .setParameter(2, assignment.getCard());
//            q.executeUpdate();
//            tx.commit();
//        } finally {
//            tx.end();
//        }
//    }
}
