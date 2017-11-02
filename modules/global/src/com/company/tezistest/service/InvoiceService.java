/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.tezistest.service;

import com.company.tezistest.entity.Invoice;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Вячеслав on 27.10.2017.
 */
public interface InvoiceService {
    String NAME = "tezistest_InvoiceService";

    Invoice createInvoice(String number, UUID budgetItemId, UUID contractorId, Date paymentDate, String amount, String paymentConditions, List paymentTermsList) throws ParseException;
}
