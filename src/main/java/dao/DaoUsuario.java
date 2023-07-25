package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.EmailUser;
import model.UsuarioPessoa;
import posjavahibernate.HibernateUtil;

public class DaoUsuario<E> extends DaoGeneric<UsuarioPessoa> {
	
	private EntityManager entityManager = HibernateUtil.getEntityManager();
	UsuarioPessoa pessoa = new UsuarioPessoa();
	
	public UsuarioPessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(UsuarioPessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
	public void removerUserCascade (UsuarioPessoa pessoa) throws Exception {
		
		getEntityManager().getTransaction().begin();
		
		/* it s not morenecessary , due new parameters from anotations in the UsuarioPessoa class.
		String sqlDeleteFone = "delete from telefoneuser where usuariopessoa_id = "+pessoa.getId();
		getEntityManager().createNativeQuery(sqlDeleteFone).executeUpdate();   // executeUpdate faz tanto atualizacao quanto delete
		
		String sqlDeleteMail = "delete from emailuser where usuariopessoa_id = "+pessoa.getId();
		getEntityManager().createNativeQuery(sqlDeleteMail).executeUpdate(); 
		*/ 
		
		getEntityManager().remove(pessoa);
		
		getEntityManager().getTransaction().commit();
		
		// super.deletarPorId(pessoa);   not more necessary
		
	}

	public List<UsuarioPessoa> pesquisarNome(String campoPesquisa) {
		//Query query = super.getEntityManager().createQuery("from UsuarioPessoa where nome like '%"+campoPesquisa+"%' "); //JPA Hibernate trabalha direto com as classes
		Query query = super.getEntityManager().createQuery("from UsuarioPessoa where UPPER(nome) like UPPER('%"+campoPesquisa+"%')");
		return query.getResultList();
	}
	
	public List<UsuarioPessoa> pesquisarSobrenome(String campoPesquisa) {
		//Query query = super.getEntityManager().createQuery("from UsuarioPessoa where nome like '%"+campoPesquisa+"%' "); //JPA Hibernate trabalha direto com as classes
		Query query = super.getEntityManager().createQuery("from UsuarioPessoa where UPPER(sobrenome) like UPPER('%"+campoPesquisa+"%')");
		return query.getResultList();
	}
	
	
	public int countPessoas() {
	    int numberPessoa = 0;
	    EntityTransaction transaction = entityManager.getTransaction();
	    transaction.begin();
	    
	    try {
	        Query query = entityManager.createQuery("SELECT COUNT(*) FROM UsuarioPessoa");
	        numberPessoa = ((Number) query.getSingleResult()).intValue();
	        transaction.commit();
	    } catch (Exception e) {
	        transaction.rollback();
	        throw e;
	    }
	    
	    return numberPessoa;
	}



}
