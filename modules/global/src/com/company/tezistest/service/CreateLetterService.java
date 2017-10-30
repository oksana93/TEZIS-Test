package com.company.tezistest.service;


import com.haulmont.thesis.core.entity.SimpleDoc;

import java.util.UUID;

public interface CreateLetterService {
    String NAME = "tezistest_CreateLetterServiceBean";
    UUID DOC_KIND_ID = UUID.fromString("c40ea551-d399-4a11-b6be-347ca5f27837");

    SimpleDoc createLetter(String number, String theme, String text);

}
