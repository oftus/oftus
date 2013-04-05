/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.main;

import com.myapp.admin.State;
import com.myapp.admin.StateDAO;
import com.myapp.admin.User;
import com.myapp.util.HibernateUtil;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author palwal
 */
public class CategoryDAO {

    private ArrayList<Category> categories;
    static final Logger logger = Logger.getLogger(StateDAO.class);

    @SuppressWarnings("unchecked")
    public ArrayList<Category> listCategories() {
        logger.debug("listCategories()");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            categories = new ArrayList<Category>();
            categories = (ArrayList<Category>) session.createQuery("from Category where status='A' order by categoryName").list();
            transaction.commit();
        } catch (HibernateException e) {
            logger.debug("HibernateException");
            e.printStackTrace();
            transaction.rollback();
        } finally {
            logger.debug("finally block");
            session.close();
        }
        return categories;
    }

    @SuppressWarnings("unchecked")
    public Category selectCategory(int categoryId) {
        logger.debug("categoryId:" + categoryId);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        Category category = new Category();
        try {
            transaction = session.beginTransaction();
            category = (Category) session.get(Category.class, categoryId);
            transaction.commit();
        } catch (HibernateException e) {
            logger.debug("HibernateException");
            e.printStackTrace();
        } catch (Exception e) {
            logger.debug("Exception");
            e.printStackTrace();
        } finally {
            logger.debug("finally block");
            session.close();
        }
        return category;
    }

    @SuppressWarnings("unchecked")
    public void updateCategory(Category category) {
        logger.debug("categoryId:" + category.getCategoryId());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(category);
            transaction.commit();
        } catch (HibernateException e) {
            logger.debug("HibernateException");
            e.printStackTrace();
        } catch (Exception e) {
            logger.debug("Exception");
            e.printStackTrace();
        } finally {
            logger.debug("finally block");
            session.close();
        }
        return;
    }
}
