package com.company.tezistest.entity;

import com.haulmont.cuba.core.entity.annotation.Extends;
import com.haulmont.thesis.core.entity.Correspondent;
import com.haulmont.thesis.core.entity.SimpleDoc;

import javax.persistence.*;
import java.util.List;

/**
 * Created by moshi on 02.11.2017.
 */
@Entity(name = "tezistest$ExtSimpleDoc")
@Extends(SimpleDoc.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // default
@DiscriminatorValue("111") // ESD - ExtSimpleDoc
public class ExtSimpleDoc extends SimpleDoc {
    private static final long serialVersionUID = -5855307198256202048L;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "TEZISTEST_EXT_SIMPLE_DOC_EXT_CORRESPONDENT",
            joinColumns = @JoinColumn(name = "EXT_SIMPLE_DOC_ID"),
            inverseJoinColumns = @JoinColumn(name = "EXT_CORRESPONDENT_ID"))
    protected List<Correspondent> correspondentList;

    public void setCorrespondentList(List<Correspondent> correspondentList) {
        this.correspondentList = correspondentList;
    }

    public List<Correspondent> getCorrespondentList() {
        return correspondentList;
    }
}
