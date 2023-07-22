package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import posjavahibernate.HibernateUtil;

public class DaoGeneric<E> {
	
	private EntityManager entityManager = HibernateUtil.getEntityManager(); 
	
	

	public void salvar(E entidade) {		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entidade);  
		transaction.commit();			
	}	
	
	/*Salva ou atualiza*/
	public E  updateMerge(E entidade) {		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		E entidadeSalva = entityManager.merge(entidade);
		transaction.commit();		
		
		return entidadeSalva;		
	}
	

	
	public E pesquisar(E entidade) {
		Object id = HibernateUtil.getPrimaryKey(entidade);
		E e = (E) entityManager.find(entidade.getClass(), id);
		return e;
	}
	
	
	public E pesquisar(Long id, Class<E> entidade) {	
		entityManager.clear();
		E e = (E) entityManager.createQuery("from " + entidade.getSimpleName() + " where id = " +id).getSingleResult();
		return e;
	}
	
	public void deletarPorId  (E entidade) throws Exception {
		Object id = HibernateUtil.getPrimaryKey(entidade);
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		 
		entityManager.createNativeQuery("delete from " +entidade.getClass().getSimpleName().toLowerCase() 
				+ " where id =" +id).executeUpdate(); // apessar de ser delete, faz update com 1 egistro a menos
		transaction.commit();		
	}
	
	
	public List<E> listr (Class<E> entidade) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		List<E> lista = entityManager.createQuery("from " + entidade.getName()).getResultList();
		transaction.commit();
		return lista;
		
	}
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	
	
	
	
	
	
	
	


}
