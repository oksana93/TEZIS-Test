package com.company.tezistest.service.kotlin

import com.company.tezistest.service.TimerService
import com.haulmont.cuba.core.Persistence
import com.haulmont.workflow.core.entity.TimerEntity
import org.springframework.stereotype.Service
import java.text.ParseException
import java.util.*
import javax.inject.Inject

@Service(TimerService.NAME)
class TimerServiceBean: TimerService {
    @Inject
    lateinit var persistence: Persistence;

    protected var selectTimerStr = "select t from wf\$Timer t where t.card.id= ?1"
    protected var updateTimerStr = "update wf\$Timer t set t.dueDate= ?1 where t.card.id = ?2";
    protected val DAYS = 0;
    protected val HOURS = 0;
    protected val MINUTES = 5;

    protected fun updateDueDates(oldDueDate: Date): Date {
        var calendar = GregorianCalendar();
        calendar.setTime(oldDueDate);
        calendar.add(Calendar.MINUTE, MINUTES);
        calendar.add(Calendar.HOUR, HOURS);
        calendar.add(Calendar.DAY_OF_MONTH, DAYS);
        return calendar.getTime();
    }

    @Throws(ParseException::class)
    override fun updateTimerByCardId(cardId: UUID) {
        var tx = persistence.createTransaction();
        try {
            var entityManager = persistence.getEntityManager();

            var q = entityManager.createQuery(selectTimerStr)
                    .setParameter(1, cardId);
            var timer = q.getFirstResult();
            if (timer != null && timer is TimerEntity) {
                var newDueDates = updateDueDates(timer.getDueDate());
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
}