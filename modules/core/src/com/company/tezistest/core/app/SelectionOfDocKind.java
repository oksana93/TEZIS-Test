package com.company.tezistest.core.app;

import com.company.tezistest.service.DocKindConfig;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Configuration;
import com.haulmont.cuba.security.app.Authenticated;

import javax.annotation.ManagedBean;

/**
 * Created by Adelya on 31.10.2017.
 */
@ManagedBean("TEZIS-Test_SelectionOfDocKind")
public class SelectionOfDocKind implements SelectionOfDocKindMBean {
    private DocKindConfig docKindConfig = null;

    @Authenticated
    @Override
    public void setLetterType() {
        createConfiguration();
        docKindConfig.setDocKindUUID(SelectionOfDocKindMBean.LETTER_TYPE_UUID);
    }

    @Authenticated
    @Override
    public void setContractType() {
        createConfiguration();
        docKindConfig.setDocKindUUID(SelectionOfDocKindMBean.CONTRACT_TYPE_UUID);
    }

    @Authenticated
    @Override
    public void setOrderType() {
        createConfiguration();
        docKindConfig.setDocKindUUID(SelectionOfDocKindMBean.ORDER_TYPE_UUID);
    }

    @Authenticated
    @Override
    public void setRegulationsType() {
        createConfiguration();
        docKindConfig.setDocKindUUID(SelectionOfDocKindMBean.REGULATIONS_TYPE_UUID);
    }

    @Authenticated
    @Override
    public void setManualType() {
        createConfiguration();
        docKindConfig.setDocKindUUID(SelectionOfDocKindMBean.MANUAL_TYPE_UUID);
    }

    @Authenticated
    protected void createConfiguration() {
        if (docKindConfig == null) {
            docKindConfig = AppBeans.get(Configuration.class)
                    .getConfig(DocKindConfig.class);
        }
    }
}
