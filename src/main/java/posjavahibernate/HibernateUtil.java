package posjavahibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class HibernateUtil {
	
	public static EntityManagerFactory factory = null;    // so pode ler uma vez, por isso static
	
	// se metodo chamado pela primeira vez ler arquivo persistence, pela segunda apenas retorna o factory criado
	static {
		init();		
		
	}
	
	private static void init() {
		try {
			if (factory == null) {
				factory = Persistence.createEntityManagerFactory("pos-java-hibernate2");
			}
		}		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static EntityManagerFactory getFactory() {
		return factory;
	}
	
	
	/*retorna o gerenciador de entidade para fazer alteracoes no banco*/
	public static EntityManager getEntityManager() {			
		return factory.createEntityManager();
	}
	
	
	/* Retorna a primary key de um objeto*/
	public static Object getPrimaryKey(Object entity) {		
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}
	
}
