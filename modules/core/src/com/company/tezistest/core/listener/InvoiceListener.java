/*
 * Copyright (c) 2017 com.company.tezistest.core.listener
 */
package com.company.tezistest.core.listener;


import com.company.tezistest.entity.Invoice;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Set;
/**
 * @author Вячеслав
 */
@ManagedBean("tezistest_InvoiceListener")
public class InvoiceListener implements BeforeUpdateEntityListener<Invoice>, BeforeInsertEntityListener<Invoice> {

    @Inject
    protected Persistence persistence;

    @Override
    public void onBeforeUpdate(Invoice entity) {

        Set<String> fields = persistence.getTools().getDirtyFields(entity);
        if (CollectionUtils.containsAny(fields, Arrays.asList("number"))){
            StringBuilder description=new StringBuilder();
            description.append(StringUtils.trimToEmpty(entity.<String>getValue("number")));
            entity.setDescription(description.toString());
        }
    }

    @Override
    public void onBeforeInsert(Invoice entity) {
        onBeforeUpdate(entity);
    }
}