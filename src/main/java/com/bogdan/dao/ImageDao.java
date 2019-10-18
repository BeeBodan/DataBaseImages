package com.bogdan.dao;

import com.bogdan.model.Image;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ImageDao {

    private SessionFactory sessionFactory;

    public ImageDao() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public long addImage(Image image) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(image);
            transaction.commit();

            return image.getId();
        }
    }

    public List getAllImages() {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("select id, name from Image")
                    .list();
        }
    }

    public Object getImage(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("select base from Image where id = :id")
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }


//    private static ImageDao instance;
//
//    private List<Image> images = new ArrayList<>();
//
//    public static synchronized ImageDao getInstance() {
//        if (instance == null) {
//            instance = new ImageDao();
//        }
//        return instance;
//    }
//
//    public ImageDao() {
//    }
}
