package br.com.arturtcs.posjavamavenhibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	public static EntityManagerFactory factory = null;
	
	static {
		init();
	}
	
	private static void init() {
		try {
			if(factory == null) {
				factory = Persistence.createEntityManagerFactory("posjavamavenhibernate");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Provê a parte da persistencia
	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
	
	//Retorna a primary key
	public static Object getPrimaryKey(Object entity) {
		return factory.getPersistenceUnitUtil().getIdentifier(entity);
	}
}
