package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.EmailUser;
import posjavahibernate.HibernateUtil;

public class DaoEmail<E> extends DaoGeneric<EmailUser> {
	
	private EntityManager entityManager = HibernateUtil.getEntityManager();
	private EmailUser email = new EmailUser();
	
	public EmailUser getEmail() {
		return email;
	}
	
	public void setEmail(EmailUser email) {
		this.email = email;
	}
	
	
	public int countEmails(EmailUser email) {
	    int numberEmails = 0;
	    EntityTransaction transaction = entityManager.getTransaction();
	    transaction.begin();
	    
	    try {
	        Query query = entityManager.createQuery(
	           "SELECT COUNT(*) FROM EmailUser WHERE usuarioPessoa = :usuarioPessoa"
	        );
	        query.setParameter("usuarioPessoa", email.getUsuarioPessoa());
	        numberEmails = ((Number) query.getSingleResult()).intValue();
	        transaction.commit();
	    } catch (Exception e) {
	        transaction.rollback();
	        throw e;
	    }
	    
	    return numberEmails;
	}
	

	
		
}
