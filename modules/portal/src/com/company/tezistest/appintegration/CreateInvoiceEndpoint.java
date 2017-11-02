package com.company.tezistest.appintegration;

import com.company.tezistest.entity.Invoice;
import com.company.tezistest.entity.TermsCollection;
import com.company.tezistest.service.InvoiceService;
import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.chile.core.datatypes.Datatypes;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.thesis.portal.portalintegration.endpoint.AbstractPortalEndpoint;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Вячеслав on 01.11.2017.
 */
public class CreateInvoiceEndpoint extends AbstractPortalEndpoint {
    @Override
    protected Element processRequest(Element requestElement, Document responseDocument, Map<String, Object> preProcessResult) throws Exception {
        Datatype datatype = Datatypes.get(Date.class);

        Invoice createdInvoice = AppBeans.get(InvoiceService.class).createInvoice(
                StringUtils.isEmpty(requestElement.element("number").getText()) ? null : requestElement.element("number").getText(),
                StringUtils.isEmpty(requestElement.element("budgetItemId").getText()) ? null : UUID.fromString(requestElement.element("budgetItemId").getText()),
                StringUtils.isEmpty(requestElement.element("contractorId").getText()) ? null : UUID.fromString(requestElement.element("contractorId").getText()),
                StringUtils.isEmpty(requestElement.element("paymentDate").getText()) ? null : (Date) datatype.parse(requestElement.element("paymentDate").getText()),
                StringUtils.isEmpty(requestElement.element("amount").getText()) ? null : requestElement.element("amount").getText(),
                StringUtils.isEmpty(requestElement.element("paymentConditions").getText()) ? null : requestElement.element("paymentConditions").getText(),
                requestElement.elements("paymentTermsList") == null ? null : requestElement.elements("paymentTermsList"));

        Element response = responseDocument.addElement("CreateInvoiceResponse", SCHEMA_URI);
        Element invoiceElement = response.addElement("invoice");
        invoiceElement.addElement("number").setText(createdInvoice.getNumber() == null ? "" : createdInvoice.getNumber());
        invoiceElement.addElement("budgetItemId").setText(createdInvoice.getBudgetItem() == null ? "" : createdInvoice.getBudgetItem().getId().toString());
        invoiceElement.addElement("contractorId").setText(createdInvoice.getContractor() == null ? "" : createdInvoice.getContractor().getId().toString());
        invoiceElement.addElement("paymentDate").setText(createdInvoice.getPaymentDate() == null ? "" : createdInvoice.getPaymentDate().toString());
        invoiceElement.addElement("amount").setText(createdInvoice.getAmount() == null ? "" : createdInvoice.getAmount());
        invoiceElement.addElement("paymentConditions").setText(createdInvoice.getPaymentConditions() == null ? "" : createdInvoice.getPaymentConditions());
        Element termCollectionElement = invoiceElement.addElement("paymentTermsList");
        if (createdInvoice.getPaymentTermsList() != null) {
            for (TermsCollection tc : createdInvoice.getPaymentTermsList()) {
               Element term = termCollectionElement.addElement("termsCollection");
               term.addElement("term").setText(tc.getTerm() == null ? "" : tc.getTerm());
               term.addElement("deadLine").setText(tc.getDeadline() == null ? "" : tc.getDeadline().toString());
               term.addElement("expired").setText(tc.getExpired() == null ? "" : tc.getExpired().toString());
            }
        }
        return response;
    }
}
