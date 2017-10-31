package com.company.tezistest.service;

import com.haulmont.thesis.core.entity.Task;

import java.sql.Date;
import java.util.UUID;

/**
 * Created by moshi on 27.10.2017.
 */
public interface TaskService {
    String NAME = "tezistest_TaskService";

    Task createTask(Date finishDatePlan, UUID executorId, UUID initiatorId, String taskName);
}
