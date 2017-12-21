package com.company.tezistest.core.app;

import com.company.tezistest.entity.ExtSimpleDoc;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.reports.app.service.ReportService;
import com.haulmont.reports.entity.Report;
import com.haulmont.yarg.reporting.ReportOutputDocument;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ManagedBean(DocumentReportJmxMBean.NAME)
public class DocumentReportJmx implements DocumentReportJmxMBean {
    @Inject
    protected Persistence persistence;
    @Inject
    ReportService reportService;

    private ExtSimpleDoc getExtSimpleDocById(ExtSimpleDoc extSimpleDoc) {
        Transaction tx = persistence.createTransaction();
        try {
            EntityManager em = persistence.getEntityManager();
            ExtSimpleDoc oldExtSimpleDoc = em.find(ExtSimpleDoc.class, extSimpleDoc.getId());
            if (oldExtSimpleDoc == null) {
                tx.commit();
                return extSimpleDoc;
            }
            else {
                return oldExtSimpleDoc;
            }
        } finally {
            tx.end();
        }
    }

    private Report getReportById(Report report) {
        Transaction tx = persistence.createTransaction();
        try {
            EntityManager em = persistence.getEntityManager();
            Report oldReport = em.find(Report.class, report.getId());
            if (oldReport == null) {
                em.persist(report);
                return report;
            }
            else {
                return oldReport;
            }
        } finally {
            tx.end();
        }
    }

    @Override
    public ReportOutputDocument printReportByExtSimpleDoc(Report report, ExtSimpleDoc extSimpleDoc) {
        extSimpleDoc = getExtSimpleDocById(extSimpleDoc);
        report = getReportById(report);
        Map<String, Object> params = new HashMap<>();
        params.put("entity", extSimpleDoc);
        return reportService.createReport(report, params);
    }
}
