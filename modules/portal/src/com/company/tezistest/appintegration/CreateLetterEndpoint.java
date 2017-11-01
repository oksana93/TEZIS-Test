/*
 * Copyright (c) ${YEAR} ${PACKAGE_NAME}
 */

package com.company.tezistest.appintegration;


import com.company.tezistest.service.CreateLetterService;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.thesis.core.entity.SimpleDoc;
import com.haulmont.thesis.portal.portalintegration.endpoint.AbstractPortalEndpoint;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Map;

public class CreateLetterEndpoint extends AbstractPortalEndpoint {
    @Override
    protected Element processRequest(Element requestElement, Document responseDocument, Map<String, Object> preProcessResult) throws Exception {
        String docNumber = (requestElement.element("number")).getText();
        String docTheme = (requestElement.element("theme")).getText();
        String docText = (requestElement.element("text")).getText();

        CreateLetterService createLetterService = AppBeans.get(CreateLetterService.NAME);
        SimpleDoc letter = createLetterService.createLetter(docNumber, docTheme, docText);

        Element response = responseDocument.addElement("CreateLetterResponse", SCHEMA_URI);
        Element letterElm = response.addElement("letter");
        letterElm.addElement("docId").setText(letter.getId().toString());
        letterElm.addElement("number").setText(StringUtils.isEmpty(letter.getNumber()) ? "" : letter.getNumber());
        letterElm.addElement("theme").setText(StringUtils.isEmpty(letter.getTheme()) ? "" : letter.getTheme());
        letterElm.addElement("docKind").setText(letter.getDocKind().getName());
        letterElm.addElement("date").setText(letter.getDate() == null ? " " : letter.getDate().toString());
        letterElm.addElement("content").setText(StringUtils.isEmpty(letter.getComment()) ? "" : letter.getComment());
        letterElm.addElement("organization").setText(letter.getOrganization() == null ? "" : letter.getOrganization().getFullName());

        return response;
    }
}
