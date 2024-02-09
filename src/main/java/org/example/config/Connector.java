package org.example.config;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

    // This is custom class for making an EntityManager and make transaction with it.
public class Connector {

    private static final String NAME = "ATM";
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    public static void creating(){

       factory = Persistence.createEntityManagerFactory(NAME);

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

}
