package org.example.config;

import org.example.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Connector {

    private static final String NAME = "ATM";
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    public static void creating(){
       factory =
                Persistence.createEntityManagerFactory(NAME);

        entityManager = factory.createEntityManager();
    }

    public static void transactionBegin(){
        entityManager.getTransaction().begin();
    }

    public static void commitTransaction(){
        entityManager.getTransaction().commit();
    }
    public static void closeConnection(){
        entityManager.close();
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }
}
