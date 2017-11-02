package com.company.tezistest.appintegration;


import com.company.tezistest.service.SelectDocKindService;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.thesis.core.entity.DocKind;
import com.haulmont.thesis.portal.portalintegration.endpoint.AbstractPortalEndpoint;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.Map;

/**
 * Created by Adelya on 31.10.2017.
 */
public class GetDocKindEndpoint extends AbstractPortalEndpoint {

    @Override
    protected Element processRequest(Element requestElement, Document responseDocument, Map<String, Object> preProcessResult) throws Exception {
        SelectDocKindService selectDocKindService = AppBeans.get(SelectDocKindService.class);
        DocKind docKind = selectDocKindService.getDocKind();

        Element response = responseDocument.addElement("GetDocKindResponse", SCHEMA_URI);
        Element docKindElm = response.addElement("docKind");
        docKindElm.addElement("docId").setText(docKind.getId().toString());
        docKindElm.addElement("name").setText(docKind.getName());
        docKindElm.addElement("docTypeName").setText(docKind.getDocType().getName());

        return response;
    }
}
