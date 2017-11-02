package com.company.tezistest.core.app;

import com.company.tezistest.service.DocKindConfig;
import com.haulmont.cuba.security.app.Authenticated;

import javax.annotation.ManagedBean;
import javax.inject.Inject;


/**
 * Created by Adelya on 31.10.2017.
 */
@ManagedBean("TEZIS-Test_SelectionOfDocKind")
public class SelectionOfDocKind implements SelectionOfDocKindMBean {
    @Inject
    private DocKindConfig docKindConfig;

    @Authenticated
    @Override
    public void setLetterType() {
        docKindConfig.setDocKindUUID(SelectionOfDocKindMBean.LETTER_TYPE_UUID);
    }

    @Authenticated
    @Override
    public void setContractType() {
        docKindConfig.setDocKindUUID(SelectionOfDocKindMBean.CONTRACT_TYPE_UUID);
    }

    @Authenticated
    @Override
    public void setOrderType() {
        docKindConfig.setDocKindUUID(SelectionOfDocKindMBean.ORDER_TYPE_UUID);
    }

    @Authenticated
    @Override
    public void setRegulationsType() {
        docKindConfig.setDocKindUUID(SelectionOfDocKindMBean.REGULATIONS_TYPE_UUID);
    }

    @Authenticated
    @Override
    public void setManualType() {
        docKindConfig.setDocKindUUID(SelectionOfDocKindMBean.MANUAL_TYPE_UUID);
    }
}
