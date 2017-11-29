package com.company.tezistest.service.kotlin

import com.company.tezistest.service.TaskComponent
import com.haulmont.cuba.core.app.DataService
import javax.inject.Inject

class TaskServiceBean {
    @Inject
    lateinit var taskComponent: TaskComponent;
    @Inject
    lateinit var dataService: DataService;
    @Inject
    lateinit var
}