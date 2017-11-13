package com.company.tezistest.service;

import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.chile.core.datatypes.Datatypes;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.workflow.core.entity.TimerEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service(TimerService.NAME)
public class TimerServiceBean implements TimerService {

    @Inject
    protected Persistence persistence;

    private String selectTimerStr = "select t from wf$Timer t where t.card.id= ?1";
    private String updateTimerStr = "update wf$Timer t set t.dueDate= ?1 where t.card.id = ?2";
    private String dateForUpdate = "00:05:00";
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void updateTimerByCardId(UUID cardId) throws ParseException {
        Transaction tx = persistence.createTransaction();
        try {
            EntityManager entityManager = persistence.getEntityManager();

            Datatype datatype = Datatypes.get(Date.class);
            Query q = entityManager.createQuery(selectTimerStr).setParameter(1, cardId);
            TimerEntity timer = (TimerEntity) q.getFirstResult();

            Date updateTimerDate = sdf.parse(dateForUpdate);
            if (timer != null) {
                timer.getDueDate()
                        .setTime(
                                timer.getDueDate().getTime() + updateTimerDate.getTime()
                        );
                q = entityManager.createQuery(updateTimerStr)
                        .setParameter(1, timer.getDueDate())
                        .setParameter(2, cardId);
                q.executeUpdate();
                tx.commit();
            }
        } finally {
            tx.end();
        }
    }
}
