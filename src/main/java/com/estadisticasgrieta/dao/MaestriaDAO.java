package com.estadisticasgrieta.dao;

import com.estadisticasgrieta.model.MaestriaCampeon;
import com.estadisticasgrieta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class MaestriaDAO {

    public void crear(MaestriaCampeon maestria) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(maestria);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<MaestriaCampeon> obtenerPorJugador(Long idJugador) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM MaestriaCampeon m WHERE m.jugador.idJugador = :id";
            Query<MaestriaCampeon> query = session.createQuery(hql, MaestriaCampeon.class);
            query.setParameter("id", idJugador);
            return query.list();
        }
    }

    public void actualizar(MaestriaCampeon maestria) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(maestria);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void eliminar(Long idMaestria) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            MaestriaCampeon maestria = session.get(MaestriaCampeon.class, idMaestria);
            if (maestria != null) {
                session.remove(maestria);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}