package com.company.tezistest.service;

import com.haulmont.thesis.core.entity.DocKind;

import java.util.UUID;

/**
 * Created by Adelya on 31.10.2017.
 */

public interface SelectDocKindService {
    String NAME = "tezistest_SelectDocKindServiceBean";

    DocKind getDocKind(UUID uuid);
}
