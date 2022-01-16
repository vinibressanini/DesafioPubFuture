package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ReceitasDAO;
import models.Conta;
import models.Receitas;
import dao.ContaDAO;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewReceitas extends JFrame {
	private JTable tableReceitas;
	private JTextField tfConta;
	private JTextField tfValor;
	private JTextField tfPrevisao;
	private JTextField tfTipo;
	private JTextField tfDtRecebimento;
	private JTextField tfIdReceita;
	private JButton btnNovo = new JButton("Novo");
	private JButton btnSalvar = new JButton("Salvar");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnLimpar = new JButton("Limpar");
	private ReceitasDAO daoReceita = new ReceitasDAO();
	private ContaDAO daoConta = new ContaDAO();	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewReceitas frame = new ViewReceitas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void insereReceita() {
		Receitas novaReceita = new Receitas();
		novaReceita.setConta(Integer.valueOf(tfConta.getText()));
		novaReceita.setDataRecebimento(tfDtRecebimento.getText());
		novaReceita.setDataRecebimentoEsperado(tfPrevisao.getText());
		novaReceita.setTipoReceita(tfTipo.getText());
		novaReceita.setValor(Double.valueOf(tfValor.getText()));
		
		int idReceita = Integer.valueOf(tfIdReceita.getText());
		String frase = "Receita Cadastrada com Sucesso!";
		
		if(idReceita > 0) {
			novaReceita.setId(idReceita);
			frase = "Receita Alterada com Sucesso!";
		}
		
		daoReceita.salvar(novaReceita);
		
		if(novaReceita.getId() > 0) {
			Conta novaConta  = new Conta();
			novaConta = daoConta.select(Conta.class, Integer.valueOf(tfConta.getText()));
			novaConta.setSaldo(novaConta.getSaldo() + Double.valueOf(tfValor.getText()));
			daoConta.salvar(novaConta);
			
			JOptionPane.showMessageDialog(null, frase,"Aviso!",JOptionPane.INFORMATION_MESSAGE);
			reiniciaTela();
		}
	}
	
	private void reiniciaTela() {
		btnNovo.setEnabled(true);
		btnExcluir.setEnabled(false);
		btnSalvar.setEnabled(false);
		btnLimpar.setEnabled(false);
		tfConta.setEditable(false);
		tfValor.setEditable(false);
		tfTipo.setEditable(false);
		tfPrevisao.setEditable(false);
		tfDtRecebimento.setEditable(false);
		limpaTela();
		tableReceitas.setModel(geraModeloDadosTabela());
	}
	
	private void limpaTela() {
		tfConta.setText("");
		tfDtRecebimento.setText("");
		tfValor.setText("");
		tfPrevisao.setText("");
		tfTipo.setText("");
	}
	
	private void excluirReceita() {
		int idExclusao = Integer.valueOf(tfIdReceita.getText());
		
		Conta novaConta = new Conta();
		Receitas novaReceita  = new Receitas();
		novaReceita = daoReceita.select(Receitas.class, idExclusao);
		novaConta = daoConta.select(Conta.class, novaReceita.getConta());
		novaConta.setSaldo(novaConta.getSaldo() - novaReceita.getValor());
		daoConta.salvar(novaConta);
		
		if(idExclusao > 0 ) {
			if(daoReceita.delete(Receitas.class, idExclusao).getId() > 0) {
				JOptionPane.showMessageDialog(null,"Receita Excluída com Sucesso!","Aviso!",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		reiniciaTela();
	}
	
	private void liberaCadastroReceitas() {
		btnNovo.setEnabled(false);
		btnExcluir.setEnabled(true);
		btnLimpar.setEnabled(true);
		btnSalvar.setEnabled(true);
		tfConta.setEditable(true);
		tfValor.setEditable(true);
		tfDtRecebimento.setEditable(true);
		tfPrevisao.setEditable(true);
		tfTipo.setEditable(true);
	}
	
	public ViewReceitas() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 642, 562);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 76, 606, 195);
		getContentPane().add(scrollPane);
		
		tableReceitas = new JTable();
		tableReceitas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(tableReceitas.getSelectedRow());
				// pegar os dados da linha atual
				int idLinhaAtualTabela = tableReceitas.getSelectedRow();
						
				tfIdReceita.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela, 0).toString());
				
				tfConta.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela, 0).toString());
				
				tfValor.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela,0).toString());
				
				tfTipo.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela, 0).toString());
				
				tfDtRecebimento.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela,0).toString());
				
				tfPrevisao.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela,0).toString());
			
			
				// carregar os dados da linha atual no formulário
			
				tfIdReceita.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela, 0).toString());
				
				tfConta.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela, 1).toString());
			
				tfValor.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela, 2).toString());
			
				tfTipo.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela,3 ).toString());
			
				tfDtRecebimento.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela,4 ).toString());
			
				tfPrevisao.setText(tableReceitas.getModel().getValueAt(idLinhaAtualTabela, 5).toString());

				liberaCadastroReceitas();
			}
		});
		scrollPane.setViewportView(tableReceitas);
		tableReceitas.setModel(geraModeloDadosTabela());
		
		JLabel lblGerenciamentoDeReceitas = new JLabel("Gerenciamento de Receitas");
		lblGerenciamentoDeReceitas.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 17));
		lblGerenciamentoDeReceitas.setBounds(204, 11, 212, 14);
		getContentPane().add(lblGerenciamentoDeReceitas);
		
		JLabel lConta = new JLabel("Conta");
		lConta.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		lConta.setBounds(10, 293, 34, 14);
		getContentPane().add(lConta);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		lblValor.setBounds(10, 349, 34, 14);
		getContentPane().add(lblValor);
		
		JLabel lblDataRecebimento = new JLabel("Data Recebimento");
		lblDataRecebimento.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		lblDataRecebimento.setBounds(293, 293, 98, 14);
		getContentPane().add(lblDataRecebimento);
		
		JLabel lblTipoReceita = new JLabel("Tipo Receita");
		lblTipoReceita.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		lblTipoReceita.setBounds(293, 349, 146, 14);
		getContentPane().add(lblTipoReceita);
		
		JLabel lblPrevisoRecebimento = new JLabel("Previs\u00E3o Recebimento ");
		lblPrevisoRecebimento.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 11));
		lblPrevisoRecebimento.setBounds(10, 405, 121, 14);
		getContentPane().add(lblPrevisoRecebimento);
		
		tfConta = new JTextField();
		tfConta.setEditable(false);
		tfConta.setBounds(10, 318, 252, 20);
		getContentPane().add(tfConta);
		tfConta.setColumns(10);
		
		tfValor = new JTextField();
		tfValor.setEditable(false);
		tfValor.setColumns(10);
		tfValor.setBounds(10, 374, 252, 20);
		getContentPane().add(tfValor);
		
		tfPrevisao = new JTextField();
		tfPrevisao.setEditable(false);
		tfPrevisao.setColumns(10);
		tfPrevisao.setBounds(10, 424, 252, 20);
		getContentPane().add(tfPrevisao);
		
		tfTipo = new JTextField();
		tfTipo.setEditable(false);
		tfTipo.setColumns(10);
		tfTipo.setBounds(293, 374, 323, 20);
		getContentPane().add(tfTipo);
		
		tfDtRecebimento = new JTextField();
		tfDtRecebimento.setEditable(false);
		tfDtRecebimento.setColumns(10);
		tfDtRecebimento.setBounds(293, 318, 323, 20);
		getContentPane().add(tfDtRecebimento);
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liberaCadastroReceitas();
			}
		});
		
		btnNovo.setBounds(10, 469, 89, 23);
		getContentPane().add(btnNovo);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insereReceita();
			}
		});
		
		btnSalvar.setEnabled(false);
		btnSalvar.setBounds(200, 469, 89, 23);
		getContentPane().add(btnSalvar);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirReceita();
			}
		});
		
		btnExcluir.setEnabled(false);
		btnExcluir.setBounds(374, 469, 89, 23);
		getContentPane().add(btnExcluir);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaTela();
			}
		});
		
		btnLimpar.setEnabled(false);
		btnLimpar.setBounds(527, 469, 89, 23);
		getContentPane().add(btnLimpar);
		
		tfIdReceita = new JTextField();
		tfIdReceita.setHorizontalAlignment(SwingConstants.CENTER);
		tfIdReceita.setText("0");
		tfIdReceita.setBounds(572, 10, 44, 20);
		getContentPane().add(tfIdReceita);
		tfIdReceita.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 606, 2);
		getContentPane().add(separator);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnOutros = new JMenu("Outros");
		menuBar.add(mnOutros);
		
		JMenuItem mntmDespesas = new JMenuItem("Despesas");
		mntmDespesas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewDespesas viewDespesas = new ViewDespesas();
				viewDespesas.setVisible(true);
				dispose();
			}
		});
		mnOutros.add(mntmDespesas);
		
		JMenuItem mntmContas = new JMenuItem("Contas");
		mntmContas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewContas viewContas = new ViewContas();
				viewContas.setVisible(true);
				dispose();
			}
		});
		mnOutros.add(mntmContas);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Atualizar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableReceitas.setModel(geraModeloDadosTabela());
			}
		});
		mnOutros.add(mntmNewMenuItem);
	}
	
	private List<Receitas> listaReceitas(){
		return daoReceita.selectAll(Receitas.class);
	}
	
	private DefaultTableModel geraModeloDadosTabela() {
		
		DefaultTableModel modeloTabela = 
			new DefaultTableModel(
					new Object[][] {}, 
					new String[] { "Id Receita","Id Conta", "Valor", "Tipo Receita","Data Pagamento","Previsão Pag" } 
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
		
		for(Receitas posicaoAtual : listaReceitas()) {
			Object linhaAtualDaTabela[] = new Object[] {
				posicaoAtual.getId(),	
				posicaoAtual.getConta(),
				posicaoAtual.getValor(),
				posicaoAtual.getTipoReceita(),
				posicaoAtual.getDataRecebimento(),
				posicaoAtual.getDataRecebimentoEsperado(),
				
			};
			modeloTabela.addRow(linhaAtualDaTabela);
		}
		return modeloTabela;
		
	}
	
}
