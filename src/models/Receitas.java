package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Receitas {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private long id;
	private double valor;
	private String dataRecebimento;
	private String dataRecebimentoEsperado;
	private int conta;
	private String tipoReceita;
	
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getDataRecebimento() {
		return dataRecebimento;
	}
	public void setDataRecebimento(String dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}
	public String getDataRecebimentoEsperado() {
		return dataRecebimentoEsperado;
	}
	public void setDataRecebimentoEsperado(String dataRecebimentoEsperado) {
		this.dataRecebimentoEsperado = dataRecebimentoEsperado;
	}

	public int getConta() {
		return conta;
	}
	public void setConta(int conta) {
		this.conta = conta;
	}
	public String getTipoReceita() {
		return tipoReceita;
	}
	public void setTipoReceita(String tipoReceita) {
		this.tipoReceita = tipoReceita;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	
	

}
