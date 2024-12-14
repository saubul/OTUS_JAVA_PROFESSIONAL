package ru.otus.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractRepository<T> implements IRepository<T> {

    private final Class<T> classType;

    private final EntityManagerFactory entityManagerFactory;


    protected AbstractRepository(EntityManagerFactory entityManagerFactory, Class<T> classType) {
        this.entityManagerFactory = entityManagerFactory;
        this.classType = classType;
    }

    @Override
    public T create(T entity) {
        return executeOperation((e) -> {
            e.persist(entity);
            return entity;
        });
    }

    @Override
    public T findById(Long id) {
        return executeOperation((e) -> e.find(classType, id));
    }

    @Override
    public List<T> findAll() {
        return executeOperationList(e -> {
            CriteriaQuery<T> criteriaQuery = e.getCriteriaBuilder().createQuery(classType);
            Root<T> root = criteriaQuery.from(classType);
            return e.createQuery(criteriaQuery.select(root)).getResultList();
        });
    }

    @Override
    public void delete(T entity) {
        executeOperation(e -> {
            e.remove(entity);
            return null;
        });
    }

    protected T executeOperation(Function<EntityManager, T> f) {
        EntityTransaction entityTransaction = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();

            T entity = f.apply(entityManager);

            entityTransaction.commit();

            return entity;
        } catch (Throwable ex) {
            ex.printStackTrace();
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            return null;
        }
    }

    protected List<T> executeOperationList(Function<EntityManager, List<T>> f) {
        EntityTransaction entityTransaction = null;
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityTransaction = entityManager.getTransaction();

            entityTransaction.begin();

            List<T> entityList = f.apply(entityManager);

            entityTransaction.commit();

            return entityList;
        } catch (Throwable ex) {
            ex.printStackTrace();
            if (entityTransaction != null && entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            return Collections.emptyList();
        }
    }

    protected EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
