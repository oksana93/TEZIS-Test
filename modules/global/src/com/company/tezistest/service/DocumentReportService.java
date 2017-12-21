package com.company.tezistest.service;

import com.company.tezistest.entity.ExtSimpleDoc;
import com.haulmont.reports.entity.Report;

public interface DocumentReportService {
    final static String NAME = "tezistest_DocumentReportService";

    String printReportByExtSimpleDoc(Report report, ExtSimpleDoc extSimpleDoc, String directory, String prefix);

}
