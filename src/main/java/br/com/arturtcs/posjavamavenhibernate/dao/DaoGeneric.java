package br.com.arturtcs.posjavamavenhibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.arturtcs.posjavamavenhibernate.HibernateUtil;

public class DaoGeneric<T> {

	private EntityManager em = HibernateUtil.getEntityManager();

	public void salvar(T entidade) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(entidade);
		transaction.commit();
	}

	public T pesquisar(Long id, Class<T> entidade) {
		em.clear(); //tirar de cache da minha lista de telefone
		//T entidadeRetornada = (T) em.find(entidade, id);
		T entidadeRetornada = (T) em.createQuery("from " + entidade.getSimpleName() + " where id = " + id).getSingleResult();
		return entidadeRetornada;
	}

	// Salva se não existir registro no banco ou atualiza caso haja
	public T updateMerge(T entidade) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		T entidadeSalva = em.merge(entidade);
		transaction.commit();
		return entidadeSalva;
	}

	public void deletarPorId(T entidade) throws Exception {
		Object id = HibernateUtil.getPrimaryKey(entidade);
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.createNativeQuery(
				"delete from " + entidade.getClass().getSimpleName().toLowerCase() + 
				" where id = " + id).executeUpdate(); // delete
		transaction.commit(); // grava a alteração (exclusão) no banco
	}
	
	public List<T> listar(Class<T> entidade){
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		List<T> lista = em.createQuery("from " + entidade.getName()).getResultList();
		
		transaction.commit();
		
		return lista;
		
	}

	public EntityManager getEm() {
		return em;
	}
	
	 

}
