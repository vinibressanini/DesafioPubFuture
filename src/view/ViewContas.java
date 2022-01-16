package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ContaDAO;
import dao.DespesasDAO;
import models.Conta;
import models.Despesas;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class ViewContas extends JFrame {

	private JPanel contentPane;
	private JTextField tfTipo;
	private JTextField tfAgencia;
	private JButton btnNovo = new JButton("Novo");
	private JButton btnSalvar = new JButton("Salvar");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnLimpar = new JButton("Limpar");
	private DespesasDAO daoDespesa = new DespesasDAO();
	private ContaDAO daoConta = new ContaDAO();
	private JTable tableContas;
	private JTextField tfIdConta;
	private final JLabel lblSaldo_1 = new JLabel("Saldo:");
	private final JTextField tfSaldo = new JTextField();
	private final JMenuBar menuBar = new JMenuBar();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewContas frame = new ViewContas();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	private void excluirConta() {
		int idExclusao = Integer.valueOf(tfIdConta.getText());
		
		if(idExclusao > 0) {
			if(daoConta.delete(Conta.class, idExclusao).getId() > 0) {
				daoDespesa.delete(Despesas.class,idExclusao);
				JOptionPane.showMessageDialog(null, "Conta Excluída Com Sucesso!","Aviso!",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		reiniciaTela();
		
	}

	private void limpaTela() {
		tfAgencia.setText("");
		tfTipo.setText("");
		tfSaldo.setText("");
	}
	
	private void liberaCadastro() {
		tfTipo.setEditable(true);
		tfAgencia.setEditable(true);
		tfSaldo.setEditable(true);
		btnNovo.setEnabled(false);
		btnSalvar.setEnabled(true);
		btnLimpar.setEnabled(true);
	}
	
	private void reiniciaTela() {
		tfTipo.setEditable(false);
		tfAgencia.setEditable(false);
		tfSaldo.setEditable(false);
		btnNovo.setEnabled(true);
		btnExcluir.setEnabled(false);
		btnLimpar.setEnabled(false);
		btnSalvar.setEnabled(false);
		limpaTela();
		tableContas.setModel(geraModeloDadosTabela());
		
	}
	
	private void cadastraConta() {
		Conta novaConta = new Conta();
		novaConta.setSaldo(Double.valueOf(tfSaldo.getText()));
		novaConta.setIntituicaoFinanceira(tfAgencia.getText());
		novaConta.setTipoConta(tfTipo.getText());
		
		int idConta = Integer.valueOf(tfIdConta.getText());
		String frase = "Conta Cadastrada com Sucesso!";

		if (idConta > 0 ) {
			novaConta.setId(idConta);
			frase = "Conta Alterada com Sucesso!";
		}
		
		daoConta.salvar(novaConta);
		
		if(novaConta.getId() > 0) {
			JOptionPane.showMessageDialog(null,frase,"Aviso!",JOptionPane.INFORMATION_MESSAGE);
			reiniciaTela();
		}
		
	}
	public  ViewContas() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 490, 550);
		
		setJMenuBar(menuBar);
		
		JMenu mnOutros = new JMenu("Outros");
		menuBar.add(mnOutros);
		
		JMenuItem mntmReceitas = new JMenuItem("Receitas");
		mntmReceitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewReceitas viewReceitas = new ViewReceitas();
				viewReceitas.setVisible(true);
				dispose();
			}
		});
		mnOutros.add(mntmReceitas);
		
		JMenuItem mntmDespesas = new JMenuItem("Despesas");
		mntmDespesas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewDespesas viewDespesas = new ViewDespesas();
				viewDespesas.setVisible(true);
				dispose();
			}
		});
		mnOutros.add(mntmDespesas);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Atualizar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableContas.setModel(geraModeloDadosTabela());
			}
		});
		mnOutros.add(mntmNewMenuItem);
		
		JMenu mnNewMenu = new JMenu("Transferir");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNovo = new JMenuItem("Novo");
		mntmNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ViewTransferencia viewTransferencia = new ViewTransferencia();
				viewTransferencia.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNovo);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 85, 454, 238);
		contentPane.add(scrollPane);
		
		tableContas = new JTable();
		tableContas.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent arg0) {
				System.out.println(tableContas.getSelectedRow());
				
				// pegar os dados da linha atual
				int idLinhaAtualTabela = tableContas.getSelectedRow();
				
				int idConta = Integer.valueOf(tableContas.getModel().getValueAt(idLinhaAtualTabela, 0).toString());
				
				tfIdConta.setText(tableContas.getModel().getValueAt(idLinhaAtualTabela, 0).toString());
				
				tfTipo.setText(tableContas.getModel().getValueAt(idLinhaAtualTabela, 0).toString());
				
				System.out.println("Linha: "+idLinhaAtualTabela+" | Id Conta: "+idConta);
				
				// carregar os dados da linha atual no formulário
				
				
				tfTipo.setText(tableContas
												.getModel()
												.getValueAt(idLinhaAtualTabela, 3)
												.toString());
				
				tfAgencia.setText(tableContas
												.getModel()
												.getValueAt(idLinhaAtualTabela, 2)
												.toString());
				
				tfSaldo.setText(tableContas
												.getModel()
												.getValueAt(idLinhaAtualTabela, 1)
												.toString());
				
				// ativar o formulário
				liberaCadastro();
				btnExcluir.setEnabled(true);
				
			}
		
		});
		
		scrollPane.setViewportView(tableContas);
		tableContas.setModel(geraModeloDadosTabela());
		
		tfTipo = new JTextField();
		tfTipo.setEditable(false);
		tfTipo.setBounds(237, 365, 214, 20);
		contentPane.add(tfTipo);
		tfTipo.setColumns(10);
		
		tfAgencia = new JTextField();
		tfAgencia.setEditable(false);
		tfAgencia.setColumns(10);
		tfAgencia.setBounds(10, 365, 214, 20);
		contentPane.add(tfAgencia);
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				liberaCadastro();
			}
		});
		
		
		btnNovo.setBounds(10, 452, 89, 23);
		contentPane.add(btnNovo);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastraConta();
				
			}
		});
		btnSalvar.setEnabled(false);
		
		btnSalvar.setBounds(135, 452, 89, 23);
		contentPane.add(btnSalvar);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirConta();
			}
		});
		btnExcluir.setEnabled(false);
		
		btnExcluir.setBounds(247, 452, 89, 23);
		contentPane.add(btnExcluir);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaTela();
			}
		});
		btnLimpar.setEnabled(false);
		
		btnLimpar.setBounds(375, 452, 89, 23);
		contentPane.add(btnLimpar);
		
		JLabel lblAgencia = new JLabel("Institui\u00E7\u00E3o Financeira:");
		lblAgencia.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		lblAgencia.setBounds(227, 347, 153, 14);
		contentPane.add(lblAgencia);
		
		JLabel lblTipoDaConta = new JLabel("Tipo da Conta: ");
		lblTipoDaConta.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		lblTipoDaConta.setBounds(10, 347, 153, 14);
		contentPane.add(lblTipoDaConta);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 64, 454, 2);
		contentPane.add(separator);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 334, 454, 2);
		contentPane.add(separator_2);
		
		JLabel lblTodasContas = new JLabel("Contas Cadastradas");
		lblTodasContas.setHorizontalAlignment(SwingConstants.CENTER);
		lblTodasContas.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 17));
		lblTodasContas.setBounds(151, 39, 160, 14);
		contentPane.add(lblTodasContas);
		
		
			
		tfIdConta = new JTextField();
		tfIdConta.setText("0");
		tfIdConta.setEditable(false);
		tfIdConta.setBounds(430, 33, 34, 20);
		contentPane.add(tfIdConta);
		tfIdConta.setColumns(10);
		contentPane.add(tfIdConta);
		lblSaldo_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		lblSaldo_1.setBounds(10, 396, 63, 14);
		
		contentPane.add(lblSaldo_1);
		tfSaldo.setEditable(false);
		tfSaldo.setColumns(10);
		tfSaldo.setBounds(10, 421, 441, 20);
		
		contentPane.add(tfSaldo);
	}
	
	private List<Conta> listaContas(){
		return daoConta.selectAll(Conta.class);
	}
	
	private DefaultTableModel geraModeloDadosTabela() {
		
		DefaultTableModel modeloTabela = 
			new DefaultTableModel(
					new Object[][] {}, 
					new String[] { "Id", "Saldo", "Tipo da Conta","Inst. Financeira" } 
				) {
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("rawtypes")
				Class[] tiposColunas = new Class[] {
					long.class, String.class, String.class,String.class
				};
				public Class<?> getColumnClass(int indiceColuna) {
					return tiposColunas[indiceColuna];
				}
		};
		
		for(Conta posicaoAtual : listaContas()) {
			Object linhaAtualDaTabela[] = new Object[] {
				posicaoAtual.getId(),
				posicaoAtual.getSaldo(),
				posicaoAtual.getIntituicaoFinanceira(),
				posicaoAtual.getTipoConta(),
				
			};
			modeloTabela.addRow(linhaAtualDaTabela);
		}
		return modeloTabela;
		
	}
}
