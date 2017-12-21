package com.company.tezistest.service;

import com.company.tezistest.core.app.DocumentReportJmxMBean;
import com.company.tezistest.entity.ExtSimpleDoc;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.reports.entity.Report;
import com.haulmont.yarg.exception.ReportFormattingException;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;

@Service(DocumentReportService.NAME)
public class DocumentReportServiceBean implements DocumentReportService {
    protected DocumentReportJmxMBean documentReportJmxMBean;

    protected File createTempFile(ReportOutputDocument document, String directory, String prefix) {
        try {
            String tempFileExt = "." + document.getReportOutputType().getId();
            File temporaryFile = new File(directory);
            temporaryFile = File.createTempFile(prefix, tempFileExt, temporaryFile);
            FileUtils.writeByteArrayToFile(temporaryFile, document.getContent());
            return temporaryFile;
        } catch (java.io.IOException e) {
            throw new ReportFormattingException("Could not create temporary file", e);
        }
    }

    @Override
    public String printReportByExtSimpleDoc(Report report, ExtSimpleDoc extSimpleDoc, String directory, String prefix) {
        documentReportJmxMBean = AppBeans.get(DocumentReportJmxMBean.class);
        ReportOutputDocument reportOutputDocument = documentReportJmxMBean.printReportByExtSimpleDoc(report, extSimpleDoc);
        return createTempFile(reportOutputDocument, directory, prefix).getAbsolutePath();
    }
}
