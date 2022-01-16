package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DAOModel<E> implements DAO<E> {

	public EntityManager getConexaoAtual() {
		EntityManagerFactory emf = ConexaoJPA.conectaBanco();
		return emf.createEntityManager();
	}
	
	public E insert(E objInserir) {
		EntityManager transacao = getConexaoAtual();
		transacao.getTransaction().begin();
		transacao.persist(objInserir);  // preparando o nosso objeto/inserção
		transacao.getTransaction().commit(); // executando no banco de dados
		transacao.close();
		return objInserir;
	}
	
	public E delete(Class<E> classe,long id) {
		EntityManager transacao = getConexaoAtual();
		E objeto = transacao.find(classe,id);
		transacao.getTransaction().begin();
		transacao.remove(objeto);
		transacao.getTransaction().commit();
		transacao.close();
		return objeto;
	}
	
	public E update(E objeto) {
		EntityManager transacao = getConexaoAtual();
		transacao.getTransaction().begin();
		objeto = transacao.merge(objeto);
		transacao.getTransaction().commit();
		transacao.close();
		return objeto;
	}
	
	@SuppressWarnings("unchecked")
	public List<E> selectAll(Class<E> classe) {
		
		// Busca de varios/todos os registros
		EntityManager transacao = getConexaoAtual();
		return (List<E>) transacao.createQuery("select t from " + classe.getSimpleName() + " t").getResultList();
		
	}
	
	public E select(Class<E> classe, long id) {
		EntityManager transacao = getConexaoAtual();
		E objeto = transacao.find(classe, id);
		return objeto;
	}
	
}
