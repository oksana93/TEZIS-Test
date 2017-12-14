package com.company.tezistest.service.kotlin

import com.company.tezistest.service.TaskService
import com.haulmont.cuba.core.Persistence
import com.haulmont.cuba.core.app.DataService
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.LoadContext
import com.haulmont.cuba.core.global.TimeSource
import com.haulmont.cuba.core.global.UserSessionSource
import com.haulmont.cuba.security.entity.User
import com.haulmont.thesis.core.app.NumerationService
import com.haulmont.thesis.core.entity.Task
import com.haulmont.thesis.core.entity.TsUser
import org.springframework.stereotype.Service
import java.sql.Date
import java.util.*
import javax.inject.Inject

@Service(TaskService.NAME)
class TaskServiceBean: TaskService {
    @Inject
    lateinit var persistence: Persistence;
    @Inject
    lateinit var dataService: DataService;
    @Inject
    lateinit var timeSource: TimeSource;
    @Inject
    lateinit var numerationService: NumerationService

    protected fun findUserById(id: UUID): User {
        val ctx = LoadContext(TsUser::class.java)
        ctx.setQueryString("select u from tm$/User u where u.id = :id and u.active = true")
                .setParameter("id", id);
        ctx.setView("user.edit");
        var entities = dataService.loadList<User>(ctx);
        return entities?.iterator()!!.next();
    }

    @Override
    override fun createTask( finishDatePlan: Date, executorId: UUID, initiatorId: UUID, taskName: String): Task {
        var userInitiator : User? = null;
        var userExecutor : User? = null;
        if (initiatorId == null) {
            var userSessionSource = AppBeans.get<UserSessionSource>(UserSessionSource.NAME);
            userInitiator = userSessionSource.getUserSession().getUser();
        } else {
            userInitiator = findUserById(initiatorId);
            if (userInitiator == null)
                throw RuntimeException("Unknown user (initiator): " + initiatorId);

        }
        if (executorId == null) {
            val userSessionSource = AppBeans.get<UserSessionSource>(UserSessionSource.NAME)
            userExecutor = userSessionSource.getUserSession().getUser();
        } else {
            var userExecutor = findUserById(executorId);
            if (userExecutor == null)
                throw RuntimeException("Unknown user (executor): " + executorId);
        }

        var task = Task();
        task.setTaskName(taskName);
        task.setExecutor(userExecutor);
        task.setInitiator(userInitiator);
        task.setFinishDatePlan(finishDatePlan);
        task.setCreateDateTime(timeSource.currentTimestamp());
        task.setState(",New,");
        task.setNum(numerationService.getNextNumber(task));

        var tx = persistence.createTransaction();
        try {
            var em = persistence.getEntityManager();
            em.persist(task);
            tx.commit();
        } finally {
            tx.end();
        }
        return task;
    }
}