package com.company.tezistest.appintegration;

import com.company.tezistest.service.TaskService;
import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.chile.core.datatypes.Datatypes;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.thesis.core.entity.Task;
import com.haulmont.thesis.portal.portalintegration.endpoint.AbstractPortalEndpoint;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.sql.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by user on 11.01.2017.
 */
public class CreateTaskEndpoint extends AbstractPortalEndpoint {

    @Override
    protected Element processRequest(Element requestElement, Document responseDocument, Map<String, Object> preProcessResult) throws Exception {
        TaskService taskService = AppBeans.get(TaskService.NAME);
        Datatype datatype = Datatypes.get(Date.class);
        Date finishDatePlan = (Date) datatype.parse(
                (requestElement.element("finishDatePlan")).getText());
        UUID executorId = StringUtils.isBlank(requestElement.element("executorId").getText()) ?
                null : UUID.fromString(requestElement.element("executorId").getText());
        UUID initiatorId = StringUtils.isBlank(requestElement.element("initiatorId").getText()) ?
                null : UUID.fromString(requestElement.element("initiatorId").getText());
        String taskName = requestElement.element("taskName").getText();

        Task newTask = taskService.createTask(finishDatePlan, executorId, initiatorId, taskName);
        Element taskResponse = responseDocument.addElement("CreateTaskEndpoint", SCHEMA_URI);
        taskResponse.addElement("taskId", newTask.getId().toString());
        taskResponse.addElement("num", newTask.getNum());
        return taskResponse;
    }
}
