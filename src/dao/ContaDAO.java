package dao;

import models.Conta;

public class ContaDAO extends DAOModel<Conta> {
	
	public void salvar (Conta conta) {
		if(conta.getId() > 0) {
			update(conta);
		}else {
			insert(conta);
		}
	}
	

}
