package com.company.tezistest.service;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultString;


@Source(type = SourceType.DATABASE)
public interface DocKindConfig extends Config {
    @Property("docKind.id")
    @DefaultString("c40ea551-d399-4a11-b6be-347ca5f27837")
    String getDocKindUUID();

    void setDocKindUUID(String value);
}
