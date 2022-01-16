package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Despesas {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY )
	private long id;
	private int conta;
	private double valor;
	private String dataPagamento;
	private String dataPagamentoEsperado;
	private String tipoDespesa;
	
	
	public int getConta() {
		return conta;
	}
	public void setConta(int conta) {
		this.conta = conta;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public String getDataPagamentoEsperado() {
		return dataPagamentoEsperado;
	}
	public void setDataPagamentoEsperado(String dataPagamentoEsperado) {
		this.dataPagamentoEsperado = dataPagamentoEsperado;
	}
	public String getTipoDespesa() {
		return tipoDespesa;
	}
	public void setTipoDespesa(String tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Despesas [id=" + id + ", conta=" + conta + ", valor=" + valor + ", dataPagamento=" + dataPagamento
				+ ", dataPagamentoEsperado=" + dataPagamentoEsperado + ", tipoDespesa=" + tipoDespesa + "]";
	}
	
	

}
