package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.EmailUser;
import model.TelefoneUser;
import posjavahibernate.HibernateUtil;

public class DaoTelefone<E> extends DaoGeneric<TelefoneUser> {

	private EntityManager entityManager = HibernateUtil.getEntityManager();
	private TelefoneUser phone = new TelefoneUser();

	public TelefoneUser getPhone() {
		return phone;
	}

	public void setPhone(TelefoneUser phone) {
		this.phone = phone;
	}
	
	
	
	
	
	

	public int countPhones(TelefoneUser phone) {
		int numberPhones = 0;
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		try {
			Query query = entityManager
					.createQuery("SELECT COUNT(*) FROM TelefoneUser WHERE usuarioPessoa = :usuarioPessoa");
			query.setParameter("usuarioPessoa", phone.getUsuarioPessoa());
			numberPhones = ((Number) query.getSingleResult()).intValue();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}

		return numberPhones;
	}

}
