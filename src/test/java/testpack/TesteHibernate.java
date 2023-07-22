package testpack;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TesteHibernate {
	
	/*
	@Test
	public void testeHibernateUtil () {
		//HibernateUtil.getEntityManager();
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setNome("Antonio");
		pessoa.setSobrenome("xxxxx");
		pessoa.setLogin("xxxxx");
		pessoa.setSenha("xxxxx");
		pessoa.setIdade(45);
		
		daoGeneric.salvar(pessoa);			
	}	

	@Test
	public void testeBuscar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(3L);
		
		pessoa = daoGeneric.pesquisar(pessoa);
		
		System.out.println(pessoa);
	}
	
	@Test
	public void testeBuscar2() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();				
		UsuarioPessoa pessoa = daoGeneric.pesquisar(3L, UsuarioPessoa.class);		
		System.out.println(pessoa);
	}
	
	@Test
	public void testeUpdate() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();				
		UsuarioPessoa pessoa = daoGeneric.pesquisar(3L, UsuarioPessoa.class);		
		pessoa.setIdade(150);
		pessoa.setNome("Nome Atualizado JSF");
		pessoa = daoGeneric.updateMerge(pessoa);
		System.out.println(pessoa);
	}
	
	@Test
	public void testeDelete() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();				
		UsuarioPessoa pessoa = daoGeneric.pesquisar(3L, UsuarioPessoa.class);		
		try {
			daoGeneric.deletarPorId(pessoa);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		System.out.println("Usuario deletado.");
	}
	
	@Test
	public void testeConsultarList() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();				
		
		List<UsuarioPessoa> list = daoGeneric.listr(UsuarioPessoa.class);
		UsuarioPessoa pessoa = daoGeneric.pesquisar(3L, UsuarioPessoa.class);
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa); 
			System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
		}
	}
	
	
	//Pode definir a query de maneira mais específica, fora do DaoGeneric
	@Test
	public void testeQueryList() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery("from UsuarioPessoa where nome = 'aaaaa'").getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);			
		}		
	}
	
	
	//Retorna outra lista especifica, de acordo com regra de negocio , aula 25.16
	@Test
	public void testeQueryListMaxList() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().
				createQuery("from UsuarioPessoa order By id").setMaxResults(10).getResultList();		
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);			
		}		
	}
	
	//passando um ou mais parametros na query, querys condicionais (or, and) 
	@Test
	public void testeQueryListParameter () {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().
				createQuery("from UsuarioPessoa where nome = :nome or sobrenome = :sobrenome").
				setParameter("nome", "fff").
				setParameter("sobrenome", "fff").
				getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}
		
	}	
	
	
	//operação matematica, soma todas as idades, aula 25.18
	 //se fosse retornar a media (avg) teria que fazer cast para Double 
	@Test
	public void somaTodasIdades () {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>(); 
		Long somaIdade =  (Long) daoGeneric.getEntityManager().
				createQuery("select sum(u.idade) from UsuarioPessoa u ").getSingleResult();
		
		System.out.println("Soma de todas as idade é: " +somaIdade);
		
	}
	
	@Test
	public void namedQuery1() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>(); 
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.todos").getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}		
	}
	
	
	@Test
	public void namedQuery2() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>(); 
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().
				createNamedQuery("UsuarioPessoa.buscaPorNome").
				setParameter("nome", "Santos").
				getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
		}		
	}
	
	@Test
	public void testGravaTelefone () {
		DaoGeneric daoGeneric = new DaoGeneric();
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(26L, UsuarioPessoa.class);
		TelefoneUser telefoneUser = new TelefoneUser();
		telefoneUser.setTipo("casa");
		telefoneUser.setNumero("58974");
		telefoneUser.setUsuarioPessoa(pessoa);
		daoGeneric.salvar(telefoneUser);
		
		System.out.println(telefoneUser);
		System.out.println("Adiocionado ao usuario: " +pessoa.getNome());
		
	}
	
	@Test
	public void testeConsultaTelefones() {
		DaoGeneric daoGeneric = new DaoGeneric();
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar(4L, UsuarioPessoa.class);
		
		for (TelefoneUser fone : pessoa.getTelefonesUser()) {
			System.out.println(fone.getNumero());
			System.out.println(fone.getTipo());
			System.out.println(fone.getUsuarioPessoa().getNome());
			System.out.println(" ----------------------------- ");			
		}				
	}	*/

}
