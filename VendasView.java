
import javax.swing.table.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.text.*;
import java.util.*;

public class VendasView extends JInternalFrame implements ActionListener {

    public static Container ctnVendas;
    public static JLabel lblId, lblTotal;
    public static JTextField txtId;
    public static JLabel[] lblCliente;
    public static JTextField[] txtCliente;
    public static String strCliente[] = {"CPF:", "Nome:", "Endereço:", "Bairro:"};
    public static JLabel[] lblProduto;
    public static JTextField[] txtProduto;
    public static String strProduto[] = {"Código:", "Nome:", "Valor Unitário:", "Quantidade:"};

    public static JButton btnAdicionar, btnFecharPedido;

    public static String strTopoItens[] = {"ID", "Nome", "Valor Unitário", "Quantidade", "Valor Parcial"};
    public static JScrollPane scrItens; //barra de rolagem da tabela
    public static JTable tblItens;//tabela
    public static DefaultTableModel mdlItens;//Classe que gerencia o conteudo da tabela

    public static String strTopoProd[] = {"Código", "Nome", "Valor Unitário", "Estoque"};
    public static JScrollPane scrProd; //barra de rolagem da tabela
    public static JTable tblProd;//tabela
    public static DefaultTableModel mdlProd;//Classe que gerencia o conteudo da tabela

    /**
     * Objetos e variaveis auxiliares*
     */
    public static java.util.List<ItensVO> itens = new ArrayList<ItensVO>();
    public static float totalVenda;
    public static int novoId;

    public VendasView() {

        super("Gerenciamento de Vendas");

        ctnVendas = new Container();
        ctnVendas.setLayout(null);
        this.add(ctnVendas);

        lblId = new JLabel("Id da Venda: ");
        lblId.setBounds(30, 75, 150, 20);
        ctnVendas.add(lblId);

        txtId = new JTextField("");
        txtId.setBounds(160, 75, 150, 20);
        ctnVendas.add(txtId);

        try {
            int idAtual = VendasDAO.gerarCodigo();
            txtId.setText("" + idAtual);
            novoId = idAtual;
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        }

        lblCliente = new JLabel[4];
        txtCliente = new JTextField[4];
        lblProduto = new JLabel[4];
        txtProduto = new JTextField[4];

        for (int i = 0; i < strCliente.length; i++) {
            lblCliente[i] = new JLabel(strCliente[i]);
            lblCliente[i].setBounds(30, 120 + (i * 30), 150, 20);
            ctnVendas.add(lblCliente[i]);

            txtCliente[i] = new JTextField();
            txtCliente[i].setBounds(160, 120 + (i * 30), 150, 20);
            ctnVendas.add(txtCliente[i]);
        }

        txtCliente[0].addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent evt) {

            }

            public void focusLost(FocusEvent evt) {
                try {
                    carregarCamposCli(ClientesDAO.consultarCliente(txtCliente[0].getText()));
                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, erro.getMessage());
                }
            }
        }
        );

        for (int i = 0; i < strProduto.length; i++) {
            lblProduto[i] = new JLabel(strProduto[i]);
            lblProduto[i].setBounds(350, 75 + (i * 30), 150, 20);
            ctnVendas.add(lblProduto[i]);

            txtProduto[i] = new JTextField();
            txtProduto[i].setBounds(480, 75 + (i * 30), 150, 20);
            ctnVendas.add(txtProduto[i]);
        }

        txtProduto[0].addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent evt) {

            }

            public void focusLost(FocusEvent evt) {
                try {
                    carregarCamposProd(ProdutosDAO.consultarProduto(Integer.parseInt(txtProduto[0].getText())));
                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, erro.getMessage());
                }
            }
        }
        );

        btnAdicionar = new JButton("Adicionar", new ImageIcon("img/icons/new.png"));
        btnAdicionar.addActionListener(this);
        btnAdicionar.setBounds(350, 200, 280, 35);
        ctnVendas.add(btnAdicionar);

        btnFecharPedido = new JButton("Fechar Pedido", new ImageIcon("img/icons/block.png"));
        btnFecharPedido.addActionListener(this);
        btnFecharPedido.setBounds(680, 350, 250, 35);
        ctnVendas.add(btnFecharPedido);

        tblItens = new JTable();
        scrItens = new JScrollPane(tblItens);
        mdlItens = (DefaultTableModel) tblItens.getModel();

        //Inserindo elementos no topo da tabela
        for (int i = 0; i < strTopoItens.length; i++) {
            mdlItens.addColumn(strTopoItens[i]);
        }

        scrItens.setBounds(30, 250, 600, 180);
        ctnVendas.add(scrItens);

        tblProd = new JTable();
        scrProd = new JScrollPane(tblProd);
        mdlProd = (DefaultTableModel) tblProd.getModel();

        //Inserindo elementos no topo da tabela
        for (int i = 0; i < strTopoProd.length; i++) {
            mdlProd.addColumn(strTopoProd[i]);
        }

        scrProd.setBounds(680, 75, 550, 260);
        ctnVendas.add(scrProd);

        carregarProdutos(0, "");//carregando tabela produtos

        tblProd.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                try {
                    int tmpCodigo = Integer.parseInt(tblProd.getValueAt(tblProd.getSelectedRow(), 0).toString());
                    ProdutosVO tmpProduto = ProdutosDAO.consultarProduto(tmpCodigo);
                    carregarCamposProd(tmpProduto);

                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, erro.getMessage());
                }
            }
        });

        lblTotal = new JLabel("R$" + "0.00");
        lblTotal.setFont(new Font("Arial Black", Font.BOLD, 40));
        lblTotal.setBounds(1000, 330, 250, 80);
        ctnVendas.add(lblTotal);
        
        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent evt) {
                MainView.btnMenu[4].setEnabled(true);
            }
        });

        this.setClosable(true);
        this.setIconifiable(true);
        this.setSize(MainView.dskJanelas.getWidth(), MainView.dskJanelas.getHeight());
        this.show();
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnAdicionar) {
            //adicionar item na tabela 
            adicionarItem(Integer.parseInt(txtProduto[0].getText()));
        } else if (evt.getSource() == btnFecharPedido) {
            try {
                //CADASTRAR VENDA - VOLTAR AQUI
                Date dataAtual = new Date();
                SimpleDateFormat dataFinal = new SimpleDateFormat("dd/M/yyyy");

                VendasVO objVenda = new VendasVO();
                objVenda.setCodigo(novoId);
                objVenda.setData("" + dataFinal.format(dataAtual));
                objVenda.setTotal(totalVenda);
                objVenda.setCpfCliente(txtCliente[0].getText());

                VendasDAO.cadastrarVenda(itens, objVenda);
                
                JOptionPane.showMessageDialog(null, "Venda Cadastrada!");
                carregarProdutos(0, "");

            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, erro.getMessage());
            }
        }
    }

    public static void carregarProdutos(int tmpTipo, String tmpBusca) {
        try {

            java.util.List<ProdutosVO> lstProdutos = new ArrayList<ProdutosVO>();

            //limpando lista
            while (mdlProd.getRowCount() > 0) {
                mdlProd.removeRow(0);
            }

            //DAO >> VIEW
            lstProdutos = ProdutosDAO.listarProdutos(tmpTipo, tmpBusca);

            for (ProdutosVO tmpProduto : lstProdutos) {//para cada obj cliente dentro da lista

                String dados[] = new String[4];
                dados[0] = tmpProduto.getCodigo_PRODUTO() + "";
                dados[1] = tmpProduto.getNome_PRODUTO();
                dados[2] = "R$ " + tmpProduto.getValorVenda_PRODUTO();
                dados[3] = tmpProduto.getQuantidadeEstoque_PRODUTO() + "";

                mdlProd.addRow(dados);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro);
        }

    }

    public static void carregarCamposProd(ProdutosVO tmpProduto) {
        txtProduto[0].setText("" + tmpProduto.getCodigo_PRODUTO());
        txtProduto[1].setText(tmpProduto.getNome_PRODUTO());
        txtProduto[2].setText("R$ " + tmpProduto.getValorVenda_PRODUTO());
    }

    public static void carregarCamposCli(ClientesVO tmpCliente) {
        txtCliente[1].setText(tmpCliente.getNome());
        txtCliente[2].setText(tmpCliente.getEndereco());
        txtCliente[3].setText(tmpCliente.getBairro());
    }

    public static int adicionarItem(int tmpCodigo) {
        boolean achou = false;

        for (ItensVO tmpItem : itens) {
            if (tmpCodigo == tmpItem.getCodigoProd()) {
                achou = true;
                int novaQtde;
                novaQtde = tmpItem.getQtdeItem() + (Integer.parseInt(txtProduto[3].getText()));
                tmpItem.setQtdeItem(novaQtde);
            }
        }

        if (achou == true) {
            totalVenda = 0;

            while (mdlItens.getRowCount() > 0) {
                mdlItens.removeRow(0);
            }
            for (ItensVO tmpItem : itens) {
                String dados[] = new String[5];
                dados[0] = "" + tmpItem.getCodigoProd();

                try {
                    dados[1] = ProdutosDAO.consultarProduto(tmpItem.getCodigoProd()).getNome_PRODUTO();
                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, erro.getMessage());
                }

                dados[2] = "" + tmpItem.getValorItem();
                dados[3] = "" + tmpItem.getQtdeItem();
                dados[4] = "" + (tmpItem.getQtdeItem() * tmpItem.getValorItem());

                mdlItens.addRow(dados);
                totalVenda += tmpItem.getQtdeItem() * tmpItem.getValorItem();
            }
            lblTotal.setText("R$ " + totalVenda);
            return 2; //somando qtde
        } else {
            ItensVO novoItem = new ItensVO();
            String nomeProd = txtProduto[1].getText();

            novoItem.setCodigoVenda(novoId);
            novoItem.setCodigoProd(Integer.parseInt(txtProduto[0].getText()));
            novoItem.setValorItem(Float.parseFloat(txtProduto[2].getText().substring(3)));
            novoItem.setQtdeItem(Integer.parseInt(txtProduto[3].getText()));

            /*Adicionando na Array*/
            itens.add(novoItem);

            /*Adicionando na JTable*/
            String dados[] = new String[5];
            dados[0] = "" + novoItem.getCodigoProd();
            dados[1] = nomeProd;
            dados[2] = "" + novoItem.getValorItem();
            dados[3] = "" + novoItem.getQtdeItem();
            dados[4] = "" + (novoItem.getQtdeItem() * novoItem.getValorItem());

            totalVenda += novoItem.getQtdeItem() * novoItem.getValorItem();

            mdlItens.addRow(dados);
        }
        lblTotal.setText("R$ " + totalVenda);
        return 1; //add novo
    }

}
