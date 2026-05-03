package com.estadisticasgrieta.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Lee el archivo hibernate.cfg.xml y construye la fábrica de sesiones
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Exception e) {
                System.err.println("Error al inicializar Hibernate SessionFactory: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}