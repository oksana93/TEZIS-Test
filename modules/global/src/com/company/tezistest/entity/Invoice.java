/*
 * Copyright (c) 2017 com.company.tezistest.entity
 */
package com.company.tezistest.entity;


/**
 * @author Вячеслав
 */

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.EnableRestore;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.TrackEditScreenHistory;
import com.haulmont.thesis.core.entity.Company;
import com.haulmont.thesis.core.entity.TsCard;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Listeners("tezistest_InvoiceListener")
@PrimaryKeyJoinColumn(name = "CARD_ID", referencedColumnName = "ID")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("1")
@Table(name = "TEZISTEST_INVOICE")
@Entity(name = "tezistest$Invoice")
@EnableRestore
@TrackEditScreenHistory
public class Invoice extends TsCard {
    private static final long serialVersionUID = 3222437891093663267L;

    @Column(name = "NUMBER_", length = 50)
    protected String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUDGET_ITEM_ID")
    protected BudgetItem budgetItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACTOR_ID")
    protected Company contractor;

    @Temporal(TemporalType.DATE)
    @Column(name = "PAYMENT_DATE", length = 50)
    protected Date paymentDate;

    @Column(name = "AMOUNT", length = 50)
    protected String amount;

    @Column(name = "PAYMENT_CONDITIONS", length = 4000)
    protected String paymentConditions;

    @Composition
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    protected Set<TermsCollection> paymentTermsList;

    public void setPaymentTermsList(Set<TermsCollection> paymentTermsList) {
        this.paymentTermsList = paymentTermsList;
    }

    public Set<TermsCollection> getPaymentTermsList() {
        return paymentTermsList;
    }



    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setBudgetItem(BudgetItem budgetItem) {
        this.budgetItem = budgetItem;
    }

    public BudgetItem getBudgetItem() {
        return budgetItem;
    }

    public void setContractor(Company contractor) {
        this.contractor = contractor;
    }

    public Company getContractor() {
        return contractor;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setPaymentConditions(String paymentConditions) {
        this.paymentConditions = paymentConditions;
    }

    public String getPaymentConditions() {
        return paymentConditions;
    }


}