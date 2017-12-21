package com.company.tezistest.core.app;

import com.company.tezistest.entity.ExtSimpleDoc;
import com.haulmont.reports.entity.Report;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Report printing")
public interface DocumentReportJmxMBean {
    static final String NAME = "tezistest_DocumentReportJmxMBean";

    @ManagedOperation(description = "Report printing")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "report", description = "Report"),
            @ManagedOperationParameter(name = "extSimpleDoc", description = "ExtSimpleDoc")})
    ReportOutputDocument printReportByExtSimpleDoc(Report report, ExtSimpleDoc extSimpleDoc);

}
