package com.company.tezistest.service;

import com.company.tezistest.entity.BudgetItem;
import com.company.tezistest.entity.Invoice;
import com.company.tezistest.entity.TermsCollection;
import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.chile.core.datatypes.Datatypes;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.thesis.core.entity.Company;
import org.dom4j.Element;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.sql.Date;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Вячеслав on 01.11.2017.
 */
@Service(InvoiceService.NAME)
public class InvoiceServiceBean implements InvoiceService {

    @Inject
    protected Persistence persistence;

    @Inject
    protected Metadata metadata;

    @Override
    public Invoice createInvoice(String number, UUID budgetItemId, UUID contractorId, Date paymentDate, String amount, String paymentConditions, List paymentTermsList) throws ParseException {
        Invoice invoice = metadata.create(Invoice.class);
        invoice.setNumber(number);
        invoice.setAmount(amount);
        invoice.setPaymentConditions(paymentConditions);
        invoice.setPaymentDate(paymentDate);

        Transaction tx = persistence.createTransaction();
        EntityManager entityManager = persistence.getEntityManager();
        invoice.setBudgetItem(entityManager.find(BudgetItem.class, budgetItemId));
        invoice.setContractor(entityManager.find(Company.class, contractorId));

        if (paymentTermsList != null) {
            Set<TermsCollection> termsCollectionSet = new HashSet<>();
            Element docElement;
            Datatype datatype = Datatypes.get(Date.class);
            for (Object e : paymentTermsList) {
                TermsCollection tc = metadata.create(TermsCollection.class);
                docElement = ((Element) e).element("termsCollection");
                tc.setTerm(StringUtils.isEmpty(docElement.element("term").getText()) ? null : docElement.element("term").getText());
                tc.setDeadline(StringUtils.isEmpty(docElement.element("deadLine").getText()) ? null : (Date) datatype.parse(docElement.element("deadLine").getText()));
                tc.setInvoice(invoice);
                tc.setExpired(StringUtils.isEmpty(docElement.element("expired").getText()) ? null : Boolean.valueOf(docElement.element("expired").getText()));
                termsCollectionSet.add(tc);
            }
            invoice.setPaymentTermsList(termsCollectionSet);
        }
        try {
            entityManager.persist(invoice);
            tx.commit();
        } finally {
            tx.end();
        }
        return invoice;
    }
}
