package com.company.tezistest.appintegration;

import com.company.tezistest.service.TaskService;
import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.chile.core.datatypes.Datatypes;
import com.haulmont.thesis.core.entity.Task;
import com.haulmont.thesis.portal.portalintegration.endpoint.AbstractPortalEndpoint;
import org.dom4j.Document;
import org.dom4j.Element;

import javax.inject.Inject;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.UUID;

/**
 * Created by user on 11.01.2017.
 */
public class CreateTaskEndpoint extends AbstractPortalEndpoint {
    @Inject
    TaskService taskService;

    protected static final String DATE_FORMAT = "dd.MM.YYYY";
    protected SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    @Override
    protected Element processRequest(Element requestElement, Document responseDocument, Map<String, Object> preProcessResult) throws Exception {
        Datatype datatype = Datatypes.get(Date.class);
        Date finishDatePlan = (Date) datatype.parse((requestElement.element("finishDatePlan")).getText());
        UUID executorId = requestElement.element("executorId").getText() == ""? null: UUID.fromString(requestElement.element("executor").getText());
        UUID initiatorId =  requestElement.element("initiatorId").getText() == ""? null: UUID.fromString(requestElement.element("initiator").getText());
        String taskName = requestElement.element("taskName").getText();

        Task newTask = taskService.createTask(finishDatePlan,executorId,initiatorId,taskName);
        Element taskResponse = responseDocument.addElement("CreateTaskEndpoint", SCHEMA_URI);
        taskResponse.addElement("taskId",newTask.getId().toString());
        taskResponse.addElement("num",newTask.getNum());
        return taskResponse;
    }
}
