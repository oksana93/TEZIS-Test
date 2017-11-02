package com.company.tezistest.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.thesis.core.entity.DocKind;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

/**
 * Created by Adelya on 31.10.2017.
 */
@Service(SelectDocKindService.NAME)
public class SelectDocKindServiceBean implements SelectDocKindService {
    @Inject
    protected Persistence persistence;

    @Override
    public DocKind getDocKind(UUID uuid) {
        Transaction tx = persistence.createTransaction();
        DocKind docKind;
        try {
            EntityManager em = persistence.getEntityManager();
            Query query = em.createQuery("Select  d from df$DocKind d where d.id=?1")
                    .setParameter(1, uuid);
            docKind = (DocKind) query.getSingleResult();
            tx.commit();
        } finally {
            tx.end();
        }

        return docKind;
    }
}
