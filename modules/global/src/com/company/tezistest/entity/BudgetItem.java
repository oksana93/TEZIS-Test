/*
 * Copyright (c) 2017 com.company.tezistest.entity
 */
package com.company.tezistest.entity;


/**
 * @author Вячеслав
 */

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.EnableRestore;
import com.haulmont.cuba.core.entity.annotation.TrackEditScreenHistory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|name")
@Table(name = "TEZISTEST_BUDGET_ITEM")
@Entity(name = "tezistest$BudgetItem")
@EnableRestore
@TrackEditScreenHistory
public class BudgetItem extends StandardEntity {
    private static final long serialVersionUID = -3778308739574150731L;

    @Column(name = "NAME", length = 50)
    protected String name;

    @Column(name = "CODE", nullable = false, length = 3)
    protected String code;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}