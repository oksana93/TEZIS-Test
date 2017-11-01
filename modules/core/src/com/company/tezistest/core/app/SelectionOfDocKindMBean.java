package com.company.tezistest.core.app;


import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;


@ManagedResource(description = "Choose Doc Kind")
public interface SelectionOfDocKindMBean {
    String LETTER_TYPE_UUID = "c40ea551-d399-4a11-b6be-347ca5f27837";
    String CONTRACT_TYPE_UUID = "9cd678e3-7978-4f53-a503-a36bce3a76d6";
    String ORDER_TYPE_UUID = "34370700-fe69-11e2-9d98-abe6f3d1eedc";
    String REGULATIONS_TYPE_UUID = "44f912b6-fe8e-11e2-a6b7-5bb93c75e36b";
    String MANUAL_TYPE_UUID = "7f1b0314-fe8e-11e2-85d5-f7a577d85b9f";

    @ManagedAttribute(description = "Type letter")
    void setLetterType();

    @ManagedAttribute(description = "Type contract")
    void setContractType();

    @ManagedAttribute(description = "Type order")
    void setOrderType();

    @ManagedAttribute(description = "Type regulation")
    void setRegulationsType();

    @ManagedAttribute(description = "Type manual")
    void setManualType();


}
