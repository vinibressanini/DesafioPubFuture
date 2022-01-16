package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.ContaDAO;
import models.Conta;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewTransferencia extends JFrame {

	private JPanel contentPane;
	private JTextField tfRemetente;
	private JTextField tfValor;
	private JTextField tfDestino;
	private ContaDAO daoConta = new ContaDAO();

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewTransferencia frame = new ViewTransferencia();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void limpaTela() {
		tfDestino.setText("");
		tfRemetente.setText("");
		tfValor.setText("");
	}
	
	
	private void transfereSaldo() {
		Conta contaEnvio = new Conta();
		Conta contaDestino = new Conta();
		
		contaEnvio = daoConta.select(Conta.class, Long.valueOf(tfRemetente.getText()));
		contaDestino = daoConta.select(Conta.class, Long.valueOf(tfDestino.getText()));
		
		if(contaEnvio.getSaldo() > Double.valueOf(tfValor.getText())) {
			contaEnvio.setSaldo(contaEnvio.getSaldo() - Double.valueOf(tfValor.getText()));
			contaDestino.setSaldo(contaDestino.getSaldo() + Double.valueOf(tfValor.getText()));
			daoConta.salvar(contaEnvio);
			daoConta.salvar(contaDestino);
			JOptionPane.showMessageDialog(null, "Transferência Feita Com Sucesso!","Aviso!",JOptionPane.INFORMATION_MESSAGE);
			limpaTela();
		}else {
			JOptionPane.showMessageDialog(null, "Saldo Insuficiente","Aviso!",JOptionPane.ERROR_MESSAGE);
			limpaTela();
		}
	}
	
	public ViewTransferencia() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Transferir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transfereSaldo();
			}
		});
		btnNewButton.setBounds(24, 215, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaTela();
			}
		});
		btnLimpar.setBounds(305, 215, 89, 23);
		contentPane.add(btnLimpar);
		
		tfRemetente = new JTextField();
		tfRemetente.setBounds(24, 90, 165, 20);
		contentPane.add(tfRemetente);
		tfRemetente.setColumns(10);
		
		tfValor = new JTextField();
		tfValor.setColumns(10);
		tfValor.setBounds(24, 168, 370, 20);
		contentPane.add(tfValor);
		
		JLabel lblNewLabel = new JLabel("Tranfer\u00EAncias");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel.setBounds(165, 11, 89, 14);
		contentPane.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 401, 2);
		contentPane.add(separator);
		
		JLabel lblIdConta = new JLabel("ID Conta Remetente");
		lblIdConta.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblIdConta.setBounds(24, 73, 135, 14);
		contentPane.add(lblIdConta);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblValor.setBounds(24, 152, 89, 14);
		contentPane.add(lblValor);
		
		tfDestino = new JTextField();
		tfDestino.setColumns(10);
		tfDestino.setBounds(229, 90, 165, 20);
		contentPane.add(tfDestino);
		
		JLabel lblIdContaDestino = new JLabel("ID Conta Destino");
		lblIdContaDestino.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		lblIdContaDestino.setBounds(229, 73, 135, 14);
		contentPane.add(lblIdContaDestino);
	}
}
