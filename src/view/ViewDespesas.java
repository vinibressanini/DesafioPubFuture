package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ContaDAO;
import dao.DespesasDAO;
import models.Despesas;
import models.Conta;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class ViewDespesas extends JFrame {

	private JPanel contentPane;
	private JTextField tfValor;
	private JTextField tfDtPagamento;
	private JTextField tfDtPagEsperado;
	private JTextField tfTipoDespesa;
	private JTextField tfConta;
	private JButton btnNovo = new JButton("Novo");
	private JButton btnSalvar = new JButton("Salvar");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnLimpar = new JButton("Limpar");
	private JTextField tfIdDespesa;
	private DespesasDAO daoDespesa = new DespesasDAO();
	private ContaDAO daoConta = new ContaDAO();
	private JTable tableDespesas;
	private JMenuBar menuBar;
	private JMenu mnOutros;
	private JMenuItem mntmContas;
	private JMenuItem mntmReceitas;
	private JMenuItem mntmNewMenuItem;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewDespesas frame = new ViewDespesas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void liberaCadastroDespesas() {
		btnNovo.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnExcluir.setEnabled(true);
		btnLimpar.setEnabled(true);
		tfValor.setEditable(true);
		tfTipoDespesa.setEditable(true);
		tfDtPagamento.setEditable(true);
		tfDtPagEsperado.setEditable(true);
		tfConta.setEditable(true);
	}
	
	private void excluirDespesa() {
		int idExclusao = Integer.valueOf(tfIdDespesa.getText());
		
		Conta novaConta = new Conta();
		Despesas novaDespesa = new Despesas();
		novaDespesa = daoDespesa.select(Despesas.class, idExclusao);
		novaConta = daoConta.select(Conta.class, novaDespesa.getConta());
		novaConta.setSaldo(novaConta.getSaldo() + novaDespesa.getValor());
		daoConta.salvar(novaConta);
		
		if (idExclusao > 0) {
			if(daoDespesa.delete(Despesas.class, idExclusao).getId() > 0) {
				JOptionPane.showMessageDialog(null, "Despesa Excluída Com Sucesso!","Aviso!",JOptionPane.INFORMATION_MESSAGE);	
			}
		}
		reiniciaTela();
	}
	
	private void limpaTela() {
		tfValor.setText("");
		tfTipoDespesa.setText("");
		tfDtPagamento.setText("");
		tfDtPagEsperado.setText("");
		tfConta.setText("");
		tfIdDespesa.setText("");
	}
	
	private void reiniciaTela() {
		btnNovo.setEnabled(true);
		btnSalvar.setEnabled(false);
		btnLimpar.setEnabled(false);
		btnExcluir.setEnabled(false);
		tfConta.setEditable(false);
		tfDtPagamento.setEditable(false);
		tfDtPagEsperado.setEditable(false);
		tfTipoDespesa.setEditable(false);
		tfValor.setEditable(false);
		limpaTela();
		tableDespesas.setModel(geraModeloDadosTabela());
	}
	
	private void insereDespesa() {
		Despesas novaDespesa = new Despesas();
		novaDespesa.setConta(Integer.valueOf(tfConta.getText()));
		novaDespesa.setDataPagamento(tfDtPagamento.getText());
		novaDespesa.setDataPagamentoEsperado(tfDtPagEsperado.getText());
		novaDespesa.setTipoDespesa(tfTipoDespesa.getText());
		novaDespesa.setValor(Double.valueOf(tfValor.getText()));
		
		int idDespesa = Integer.valueOf(tfIdDespesa.getText());
		String frase = "Despesa Cadastrada com Sucesso!";
		
		if (idDespesa > 0 ) {
			novaDespesa.setId(idDespesa);
			frase = "Despesa Alterada Com Sucesso!";
		}
		
		daoDespesa.salvar(novaDespesa);
		
		if(novaDespesa.getId() > 0) {
			// Atualizando o Saldo
			Conta novaConta = new Conta();
			novaConta = daoConta.select(Conta.class, Integer.valueOf(tfConta.getText()));
			novaConta.setSaldo(novaConta.getSaldo() - Double.valueOf(tfValor.getText()));
			daoConta.salvar(novaConta);
			
			JOptionPane.showMessageDialog(null,frase,"Aviso!",JOptionPane.INFORMATION_MESSAGE);
			reiniciaTela();
		}
		
	}
	
	public ViewDespesas() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 642, 542);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnOutros = new JMenu("Outros");
		menuBar.add(mnOutros);
		
		mntmReceitas = new JMenuItem("Receitas");
		mntmReceitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewReceitas viewReceitas = new ViewReceitas();
				viewReceitas.setVisible(true);
				dispose();
			}
		});
		mnOutros.add(mntmReceitas);
		
		mntmContas = new JMenuItem("Contas");
		mntmContas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewContas viewContas = new ViewContas();
				viewContas.setVisible(true);
				dispose();
			}
		});
		mnOutros.add(mntmContas);
		
		mntmNewMenuItem = new JMenuItem("Atualizar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableDespesas.setModel(geraModeloDadosTabela());
			}
		});
		mnOutros.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 85, 600, 174);
		contentPane.add(scrollPane);
		
		tableDespesas = new JTable();
		tableDespesas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(tableDespesas.getSelectedRow());
				// pegar os dados da linha atual
				int idLinhaAtualTabela = tableDespesas.getSelectedRow();
						
				tfIdDespesa.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela, 0).toString());
				
				tfConta.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela, 0).toString());
				
				tfValor.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela,0).toString());
				
				tfTipoDespesa.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela, 0).toString());
				
				tfDtPagamento.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela,0).toString());
				
				tfDtPagEsperado.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela,0).toString());
			
			
				// carregar os dados da linha atual no formulário
			
				tfIdDespesa.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela, 0).toString());
				
				tfConta.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela, 1).toString());
			
				tfValor.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela, 2).toString());
			
				tfTipoDespesa.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela,3 ).toString());
			
				tfDtPagamento.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela,4 ).toString());
			
				tfDtPagEsperado.setText(tableDespesas.getModel().getValueAt(idLinhaAtualTabela, 5).toString());

				liberaCadastroDespesas();
			}
		});
		scrollPane.setViewportView(tableDespesas);
		tableDespesas.setModel(geraModeloDadosTabela());
		
		tfValor = new JTextField();
		tfValor.setBounds(10, 351, 256, 20);
		tfValor.setEditable(false);
		contentPane.add(tfValor);
		tfValor.setColumns(10);
		
		tfDtPagamento = new JTextField();
		tfDtPagamento.setBounds(290, 295, 320, 20);
		tfDtPagamento.setEditable(false);
		tfDtPagamento.setColumns(10);
		contentPane.add(tfDtPagamento);
		
		tfDtPagEsperado = new JTextField();
		tfDtPagEsperado.setBounds(7, 407, 259, 20);
		tfDtPagEsperado.setEditable(false);
		tfDtPagEsperado.setColumns(10);
		contentPane.add(tfDtPagEsperado);
		
		tfTipoDespesa = new JTextField();
		tfTipoDespesa.setBounds(290, 351, 320, 20);
		tfTipoDespesa.setEditable(false);
		tfTipoDespesa.setColumns(10);
		contentPane.add(tfTipoDespesa);
		
		tfConta = new JTextField();
		tfConta.setBounds(10, 295, 256, 20);
		tfConta.setEditable(false);
		tfConta.setColumns(10);
		contentPane.add(tfConta);
		btnNovo.setBounds(10, 452, 89, 23);
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liberaCadastroDespesas();
			}
		});
		contentPane.add(btnNovo);
		btnSalvar.setBounds(177, 452, 89, 23);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insereDespesa();
			}
		});
		
		
		btnSalvar.setEnabled(false);
		contentPane.add(btnSalvar);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirDespesa();
			}
		});
		btnExcluir.setBounds(354, 452, 89, 23);
		
		
		btnExcluir.setEnabled(false);
		contentPane.add(btnExcluir);
		btnLimpar.setBounds(521, 452, 89, 23);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaTela();
			}
		});
		
		
		btnLimpar.setEnabled(false);
		contentPane.add(btnLimpar);
		
		tfIdDespesa = new JTextField();
		tfIdDespesa.setBounds(582, 0, 34, 20);
		tfIdDespesa.setText("0");
		tfIdDespesa.setEditable(false);
		contentPane.add(tfIdDespesa);
		tfIdDespesa.setColumns(10);
		contentPane.add(tfIdDespesa);
		
		JLabel lValor = new JLabel("Valor");
		lValor.setBounds(10, 326, 34, 14);
		lValor.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		contentPane.add(lValor);
		
		JLabel lDtPag = new JLabel("Data Pagamento");
		lDtPag.setBounds(288, 270, 86, 14);
		lDtPag.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		contentPane.add(lDtPag);
		
		JLabel lConta = new JLabel("Conta");
		lConta.setBounds(10, 270, 34, 14);
		lConta.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		contentPane.add(lConta);
		
		JLabel lDtPagEsperado = new JLabel("Data Pagamento Esperado");
		lDtPagEsperado.setBounds(10, 382, 145, 14);
		lDtPagEsperado.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		contentPane.add(lDtPagEsperado);
		
		JLabel lTipoDespesa = new JLabel("Tipo Despesa");
		lTipoDespesa.setBounds(290, 326, 73, 14);
		lTipoDespesa.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		contentPane.add(lTipoDespesa);
		
		JLabel lblGerenciamentoDeDespesas = new JLabel("Gerenciamento de Despesas");
		lblGerenciamentoDeDespesas.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 17));
		lblGerenciamentoDeDespesas.setBounds(202, 22, 212, 14);
		contentPane.add(lblGerenciamentoDeDespesas);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 47, 597, 2);
		contentPane.add(separator);
		
		
	}
	
	private List<Despesas> listaDespesas(){
		return daoDespesa.selectAll(Despesas.class);
	}
	
	private DefaultTableModel geraModeloDadosTabela() {
		
		DefaultTableModel modeloTabela = 
			new DefaultTableModel(
					new Object[][] {}, 
					new String[] { "Id Despesa","Id Conta", "Valor", "Tipo Despesa","Data Pagamento","Previsão Pag" } 
				) {
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("rawtypes")
				Class[] tiposColunas = new Class[] {
					long.class,long.class, String.class, String.class,String.class,String.class
				};
				public Class<?> getColumnClass(int indiceColuna) {
					return tiposColunas[indiceColuna];
				}
		};
		
		for(Despesas posicaoAtual : listaDespesas()) {
			Object linhaAtualDaTabela[] = new Object[] {
				posicaoAtual.getId(),	
				posicaoAtual.getConta(),
				posicaoAtual.getValor(),
				posicaoAtual.getTipoDespesa(),
				posicaoAtual.getDataPagamento(),
				posicaoAtual.getDataPagamentoEsperado(),
				
			};
			modeloTabela.addRow(linhaAtualDaTabela);
		}
		return modeloTabela;
		
	}
}
