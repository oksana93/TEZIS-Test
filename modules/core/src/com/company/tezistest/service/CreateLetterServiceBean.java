package com.company.tezistest.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.thesis.core.entity.DocKind;
import com.haulmont.thesis.core.entity.SimpleDoc;
import org.springframework.stereotype.Service;


import javax.inject.Inject;

@Service(CreateLetterService.NAME)
public class CreateLetterServiceBean implements CreateLetterService {
    @Inject
    protected Persistence persistence;
    @Inject
    Metadata metadata;

    @Override
    public SimpleDoc createLetter(String number, String theme, String text) {
        Transaction tx = persistence.createTransaction();
        SimpleDoc letter = null;
        try {
            EntityManager em = persistence.getEntityManager();
            letter = metadata.create(SimpleDoc.class);
            letter.setNumber(number);
            letter.setTheme(theme);
            letter.setComment(text);
            Query query = em.createQuery("Select  d from df$DocKind d where d.id=?1")
                    .setParameter(1, DOC_KIND_ID);
            DocKind docKind = (DocKind) query.getSingleResult();
            letter.setDocKind(docKind);
            em.persist(letter);
            tx.commit();
        } finally {
            tx.end();
        }
        return letter;
    }
}
