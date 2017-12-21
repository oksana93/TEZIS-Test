package com.company.tezistest.core.app;

import com.company.tezistest.entity.ExtSimpleDoc;
import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.gui.ReportGuiManager;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ManagedBean(DocumentReportMBean.NAME)
public class DocumentReport implements DocumentReportMBean {
    @Inject
    protected DataService dataService;
    @Inject
    protected ReportGuiManager reportGuiManager;

    protected ExtSimpleDoc getUniqueExtSimpleDoc(ExtSimpleDoc extSimpleDoc) {
        LoadContext ctx = new LoadContext(ExtSimpleDoc.class);
        ctx.setQueryString("select s from tezistest$ExtSimpleDoc s where s.id = :id")
                .setParameter("id", extSimpleDoc.getId());
        ExtSimpleDoc oldExtSimpleDoc = dataService.load(ctx);
        if (oldExtSimpleDoc == null) {
            CommitContext commitContext = new CommitContext(extSimpleDoc);
            return (ExtSimpleDoc) dataService.commit(commitContext).iterator().next();
        }
        return oldExtSimpleDoc;
    }

    protected Report getReportById(Report report) {
        LoadContext ctx = new LoadContext(Report.class);
        ctx.setQueryString("select r from report$Report r where r.name = :reportName")
                .setParameter("reportName", report.getName());
        Report oldReport = dataService.load(ctx);
        if (oldReport == null) {
            CommitContext commitContext = new CommitContext(report);
            return (Report) dataService.commit(commitContext).iterator().next();
        }
        return oldReport;
    }

    @Override
    public void printReportByExtSimpleDoc(Report report, ExtSimpleDoc extSimpleDoc) {
        extSimpleDoc = getUniqueExtSimpleDoc(extSimpleDoc);
        report = getReportById(report);
        Map<String,Object> params = new HashMap<>();
        params.put("entity", extSimpleDoc);
        reportGuiManager.printReport(report, params);
    }
}
