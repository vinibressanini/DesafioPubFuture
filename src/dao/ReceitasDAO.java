package dao;

import models.Receitas;

public class ReceitasDAO extends DAOModel<Receitas> {
	
	public void salvar (Receitas receita) {
		if(receita.getId() > 0) {
			update(receita);
		}else {
			insert(receita);
		}
	}

}
