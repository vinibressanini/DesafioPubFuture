package dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class ConexaoJPA {

	private static EntityManagerFactory conexao;
	
	public static EntityManagerFactory conectaBanco() {
		
		if(conexao == null) {
			conexao = Persistence.createEntityManagerFactory("senaiPU");
		}		
		return conexao;
	}
}