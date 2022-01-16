	package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Conta {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double saldo;
	private String tipoConta;
	private String intituicaoFinanceira;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public String getTipoConta() {
		return tipoConta;
	}
	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}
	public String getIntituicaoFinanceira() {
		return intituicaoFinanceira;
	}
	public void setIntituicaoFinanceira(String intituicaoFinanceira) {
		this.intituicaoFinanceira = intituicaoFinanceira;
	}
	@Override
	public String toString() {
		return "Conta [id=" + id + ", saldo=" + saldo + ", tipoConta=" + tipoConta + ", intituicaoFinanceira="
				+ intituicaoFinanceira + "]";
	}
	
	

}
