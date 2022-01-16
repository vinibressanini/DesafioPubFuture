package dao;

import models.Despesas;

public class DespesasDAO extends DAOModel<Despesas> {
	
	public void salvar(Despesas despesas) {
		if (despesas.getId() > 0) {
			update(despesas);
		}else {
			insert(despesas);
		}
	}
	
}
