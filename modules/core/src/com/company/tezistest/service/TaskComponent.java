package com.company.tezistest.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.thesis.core.entity.Task;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by moshi on 27.10.2017.
 */
@Component
public class TaskComponent {
    @Inject
    protected Persistence persistence;

    public void createTask(Task entity) {
        Transaction tx = persistence.createTransaction();
        try {
            EntityManager em = persistence.getEntityManager();
            em.persist(entity);
            tx.commit();
        } finally {
            tx.end();
        }
    }
}
