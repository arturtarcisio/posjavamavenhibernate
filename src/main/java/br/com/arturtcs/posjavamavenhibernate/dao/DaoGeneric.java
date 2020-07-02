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
		T entidadeRetornada = (T) em.find(entidade, id);
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

	public void deletarPorId(Long id, Class<T> entidade) {
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		T entidadeRetornada = (T) em.find(entidade, id);
		em.remove(entidadeRetornada);
		transaction.commit();
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
