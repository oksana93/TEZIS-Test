package com.company.tezistest.service;

import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.thesis.core.app.NumerationService;
import com.haulmont.thesis.core.entity.Task;
import com.haulmont.thesis.core.entity.TsUser;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by moshi on 27.10.2017.
 */
@Service(TaskService.NAME)
public class TaskServiceBean implements TaskService {
    @Inject
    protected TaskComponent taskComponent;
    @Inject
    protected DataService dataService;
    @Inject
    protected TimeSource timeSource;
    @Inject
    protected NumerationService numerationService;


    protected User findUserById(UUID id) {
        LoadContext ctx = new LoadContext(TsUser.class);
        ctx.setQueryString("select u from tm$User u where u.id = :id and u.active = true")
                .setParameter("id", id);
        ctx.setView("user.edit");
        List<User> entities = dataService.loadList(ctx);
        return entities.isEmpty() ? null : entities.iterator().next();
    }

    @Override
    public Task createTask(Date finishDatePlan, UUID executorId, UUID initiatorId, String taskName) {
        User userExecutor;
        User userInitiator;

        if (initiatorId == null) {
            UserSessionSource userSessionSource = AppBeans.get(UserSessionSource.NAME);
            userInitiator = userSessionSource.getUserSession().getUser();
        } else {
            userInitiator = findUserById(initiatorId);
            if (userInitiator == null)
                throw new RuntimeException("Unknown user (initiator): " + initiatorId);

        }
        if (executorId == null) {
            UserSessionSource userSessionSource = AppBeans.get(UserSessionSource.NAME);
            userExecutor = userSessionSource.getUserSession().getUser();
        } else {
            userExecutor = findUserById(executorId);
            if (userExecutor == null)
                throw new RuntimeException("Unknown user (executor): " + executorId);
        }

        Task task = new Task();
        task.setTaskName(taskName);
        task.setExecutor(userExecutor);
        task.setInitiator(userInitiator);
        task.setFinishDatePlan(finishDatePlan);
        task.setCreateDateTime(timeSource.currentTimestamp());
        task.setState(",New,");
        task.setNum(numerationService.getNextNumber(task));

        taskComponent.createTask(task);
        return task;
    }
}
