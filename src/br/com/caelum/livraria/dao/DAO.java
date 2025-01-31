package br.com.caelum.livraria.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class DAO<T> {

	private final Class<T> classe;
	private EntityManager em;

	public DAO(EntityManager manager, Class<T> classe) {
		this.em = manager;
		this.classe = classe;
	}

	public void adiciona(T t) {
		// persiste o objeto
		em.persist(t);
	}

	public void remove(T t) {
		em.remove(em.merge(t));
	}

	public void atualiza(T t) {
		em.merge(t);
	}

	public List<T> listaTodos() {
		
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		return lista;
	}

	public T buscaPorId(Integer id) {
		
		T instancia = em.find(classe, id);
		
		return instancia;
	}

	public int contaTodos() {
		
		long result = (Long) em.createQuery("select count(n) from livro n").getSingleResult();
		
		return (int) result;
	}

	// classe DAO
	public List<T> listaTodosPaginada(int firstResult, int maxResults, String coluna, String valor) {
	   
	    CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
	    Root<T> root = query.from(classe);

	    if(valor != null)
	        query = query.where(em.getCriteriaBuilder().like(root.<String>get(coluna), valor + "%"));

	    List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

	    return lista;
	}

	// classe DAO
	public int quantidadeDeElementos() {
		
		long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n").getSingleResult();
		
		return (int) result;
	}

}
