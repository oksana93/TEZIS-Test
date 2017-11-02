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

import javax.persistence.*;
import java.util.Date;

@NamePattern("%s|term")
@Table(name = "TEZISTEST_TERMS_COLLECTION")
@Entity(name = "tezistest$TermsCollection")
@EnableRestore
@TrackEditScreenHistory
public class TermsCollection extends StandardEntity {
    private static final long serialVersionUID = -3245853465380443405L;

    @Column(name = "TERM", length = 4000)
    protected String term;

    @Column(name = "DEADLINE", length = 50)
    protected Date deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_ID")
    protected Invoice invoice;

    @Column(name = "EXPIRED", length = 50)
    protected Boolean expired;

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getExpired() {
        return expired;
    }


    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }


    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getDeadline() {
        return deadline;
    }


}