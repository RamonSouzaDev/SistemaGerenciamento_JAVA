
import java.io.*;
import java.nio.file.*;
import java.nio.channels.*;
import javax.swing.filechooser.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class ProdutosView extends JInternalFrame implements ActionListener {

    //declaração de objetos
    public static JMenuBar mbrProdutos;
    public static JMenu mnuArquivo, mnuDados, mnuAjuda;
    public static JMenuItem mniNovo, mniEditar, mniSalvar,
                            mniExcluir, mniFechar,
                            mniConsulta, mniDesativar,
                            mniSobre;
    
    public static Container ctnProdutos;
    
    public static JToolBar tbrProdutos;
    public static ImageIcon icnNovo, icnEditar,
                            icnSalvar, icnDesativar,
                            icnBuscar, icnExcluir,
                            icnImprimir, icnFechar;
    public static BotoesBarra bbrNovo, bbrEditar,
                              bbrSalvar, bbrDesativar, 
                              bbrBuscar, bbrExcluir,
                              bbrImprimir, bbrFechar;

    public static String strCampos[] = {"Código:", "Nome:", "Descrição:", "Quantidade:", "Valor Venda:", "Valor Compra:", "Codigo categoria:"};
    public static JLabel lblCampos[];
    public static JTextField txtCampos[];

    public static ImageIcon imgFoto;
    public static JLabel lblFoto;

    public static String strTopo[] = {"Codigo", "Nome", "Descricao", "Quantidade"};
    public static JScrollPane scrProdutos; //barra de rolagem da tabela
    public static JTable tblProdutos;//tabela
    public static DefaultTableModel mdlProdutos;//Classe que gerencia o conteudo da tabela

    public static JLabel lblBusca;
    public static JTextField txtBusca;
    public static JButton btnBusca;

    public static JButton btnNovo, btnSalvar, btnDesativar, btnFoto, btnEditar;
    public static ImageIcon icnPais, icnUsuario, icnRestaurar, icnBloquear;
    public static JButton btnCodigo, btnNome, btnRestaurar;

    //Declaração de variaveis e objetos auxiliares
    public static FileChannel flcOrigem, flcDestino;//cópia
    public static FileInputStream flsEntrada;//leitura
    public static FileOutputStream flsSaida;//leitura
    public static String strCaminhoOrigem, strCaminhoDestino, strNomeArquivoOrigem, extensao;
    public static int statusFoto;
    public static int statusAtual = 0,acao;
    public static boolean status;

    public ProdutosView() {//construtor
        super("Gerenciamento de Produtos");

        //Construção dos objetos
        icnNovo = new ImageIcon("img/icons/new.png");
        icnEditar = new ImageIcon("img/icons/edit.png");
        icnSalvar = new ImageIcon("img/icons/save.png");
        icnDesativar = new ImageIcon("img/icons/block.png");
        icnBuscar = new ImageIcon("img/icons/search.png");
        icnExcluir = new ImageIcon("img/icons/delete.png");
        icnImprimir = new ImageIcon("img/icons/report.png");
        icnFechar = new ImageIcon("img/icons/exit.png");
        
        mbrProdutos = new JMenuBar();
        this.setJMenuBar(mbrProdutos);
        
        mnuArquivo = new JMenu("Arquivo");
        mnuArquivo.setMnemonic('a');
        mbrProdutos.add(mnuArquivo);
        
        mniNovo = new JMenuItem("Novo Produto",icnNovo);
        mniNovo.addActionListener(this);
        mnuArquivo.add(mniNovo);
        
        mnuArquivo.add(new JSeparator());
        
        mniEditar = new JMenuItem("Editar Dados", icnEditar);
        mniEditar.setEnabled(false);
        mniEditar.addActionListener(this);
        mnuArquivo.add(mniEditar);
        
        mniSalvar = new JMenuItem("Salvar Informações", icnSalvar);
        mniSalvar.addActionListener(this);
        mnuArquivo.add(mniSalvar);
        
        mnuArquivo.add(new JSeparator());
        
        mniExcluir = new JMenuItem("Excluir produto atual", icnExcluir);
        mniExcluir.setEnabled(false);
        mniExcluir.addActionListener(this);
        mnuArquivo.add(mniExcluir);
        
        mnuArquivo.add(new JSeparator());
        
        mniFechar = new JMenuItem("Fechar módulo PRODUTOS", icnFechar);
        mniFechar.addActionListener(this);
        mnuArquivo.add(mniFechar);
        
        mnuDados = new JMenu("Dados");
        mnuDados.setMnemonic('d');
        mbrProdutos.add(mnuDados);
        
        mniConsulta = new JMenuItem("Consulta por Codigo",icnBuscar);
        mniConsulta.addActionListener(this);
        mnuDados.add(mniConsulta);
        
        mnuDados.add(new JSeparator());
        
        mniDesativar = new JMenuItem("Alterar Status",icnBloquear);
        mniDesativar.setEnabled(false);
        mniDesativar.addActionListener(this);
        mnuDados.add(mniDesativar);
        
        mnuAjuda = new JMenu("Ajuda");
        mnuAjuda.setMnemonic('h');
        mbrProdutos.add(mnuAjuda);
        
        mniSobre = new JMenuItem("Sobre o sistema");
        mniSobre.addActionListener(this);
        mnuAjuda.add(mniSobre);
        
        ctnProdutos = new Container();
        ctnProdutos.setLayout(null);
        this.add(ctnProdutos);
        
        tbrProdutos = new JToolBar();
        tbrProdutos.setBounds(0,0,285,35);
        ctnProdutos.add(tbrProdutos);
        
        /********************************BARRA DE FERRAMENTAS******************************/
        
        bbrNovo = new BotoesBarra(0,icnNovo,"Novo Produto");
        tbrProdutos.add(bbrNovo);
        
        bbrEditar = new BotoesBarra(1,icnEditar,"Editar Dados");
        bbrEditar.setEnabled(false);
        tbrProdutos.add(bbrEditar);
        
        bbrSalvar = new BotoesBarra(2,icnSalvar,"Salvar Informações");
        tbrProdutos.add(bbrSalvar);
        
        bbrDesativar = new BotoesBarra(3,icnDesativar,"Alterar Status");
        bbrDesativar.setEnabled(false);
        tbrProdutos.add(bbrDesativar);
        
        bbrBuscar = new BotoesBarra(4,icnBuscar,"Buscar por Codigo");
        tbrProdutos.add(bbrBuscar);
        
        bbrExcluir = new BotoesBarra(5,icnExcluir,"Remover produto atual");
        bbrExcluir.setEnabled(false);
        tbrProdutos.add(bbrExcluir);
        
        bbrImprimir = new BotoesBarra(6,icnImprimir,"Gerar arquivo texto");
        tbrProdutos.add(bbrImprimir);
        
        bbrFechar = new BotoesBarra(7,icnFechar,"Fechar módulo");
        tbrProdutos.add(bbrFechar);

        lblCampos = new JLabel[strCampos.length];
        txtCampos = new JTextField[strCampos.length];

        for (int i = 0; i < lblCampos.length; i++) {
            lblCampos[i] = new JLabel(strCampos[i]);
            lblCampos[i].setBounds(30, 75 + (i * 30), 150, 20);
            ctnProdutos.add(lblCampos[i]);

            txtCampos[i] = new JTextField();
            txtCampos[i].setBounds(160, 75 + (i * 30), 240, 20);
            ctnProdutos.add(txtCampos[i]);

        }//fechando for

        btnEditar = new JButton("Editar Dados");
        btnEditar.setEnabled(false);        
        btnEditar.addActionListener(this);
        btnEditar.setBounds(250, 315, 150, 30);
        ctnProdutos.add(btnEditar);

        imgFoto = new ImageIcon("img/prod.png");
        lblFoto = new JLabel(imgFoto);
        lblFoto.setBounds(440, 75, 128, 128);
        ctnProdutos.add(lblFoto);

        btnFoto = new JButton("Selecionar imagem");
        btnFoto.addActionListener(this);
        btnFoto.setBounds(430, 215, 150, 20);
        ctnProdutos.add(btnFoto);

        tblProdutos = new JTable();
        scrProdutos = new JScrollPane(tblProdutos);
        mdlProdutos = (DefaultTableModel) tblProdutos.getModel();

        //Inserindo elementos no topo da tabela
        for (int i = 0; i < strTopo.length; i++) {
            mdlProdutos.addColumn(strTopo[i]);
        }

        scrProdutos.setBounds(600, 105, 550, 290);
        ctnProdutos.add(scrProdutos);

        tblProdutos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                try {
                    String tmpCodigo = tblProdutos.getValueAt(tblProdutos.getSelectedRow(), 0).toString();
                    ProdutosVO tmpProduto = ProdutosDAO.consultarProduto(Integer.parseInt(tmpCodigo));
                    carregarCampos(tmpProduto);

                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, erro.getMessage());
                }
            }
        });

        lblBusca = new JLabel("Busca Rápida:");
        lblBusca.setBounds(600, 75, 100, 20);
        ctnProdutos.add(lblBusca);

        txtBusca = new JTextField();
        txtBusca.setBounds(690, 75, 450, 20);
        ctnProdutos.add(txtBusca);

        txtBusca.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                carregarDados(3, txtBusca.getText());
            }
        }
        );

        btnNovo = new JButton("Novo Produto");
        btnNovo.addActionListener(this);
        btnNovo.setBounds(430, 265, 150, 30);
        ctnProdutos.add(btnNovo);

        btnSalvar = new JButton("Salvar dados");
        btnSalvar.addActionListener(this);
        btnSalvar.setBounds(430, 315, 150, 30);
        ctnProdutos.add(btnSalvar);

        icnBloquear = new ImageIcon("img/icons/block.png");

        btnDesativar = new JButton("Desativar", icnBloquear);
        btnDesativar.setEnabled(false);
        btnDesativar.addActionListener(this);
        btnDesativar.setBounds(430, 365, 150, 30);
        ctnProdutos.add(btnDesativar);

        icnPais = new ImageIcon("img/icons/country.png");
        icnUsuario = new ImageIcon("img/icons/prod.png");
        icnRestaurar = new ImageIcon("img/icons/restore.png");

        btnCodigo = new JButton("por Codigo", icnPais);
        btnCodigo.addActionListener(this);
        btnCodigo.setBounds(1170, 105, 150, 30);
        ctnProdutos.add(btnCodigo);

        btnNome = new JButton("por Nome", icnUsuario);
        btnNome.addActionListener(this);
        btnNome.setBounds(1170, 155, 150, 30);
        ctnProdutos.add(btnNome);

        btnRestaurar = new JButton("Restaurar", icnRestaurar);
        btnRestaurar.addActionListener(this);
        btnRestaurar.setBounds(1170, 205, 150, 30);
        ctnProdutos.add(btnRestaurar);

        desbloquearCampos(false);
        carregarDados(0, null);

        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent evt) {
                MainView.btnMenu[0].setEnabled(true);
            }
        });
        this.setIconifiable(true);
        this.setClosable(true);
        this.setSize(MainView.dskJanelas.getWidth(), MainView.dskJanelas.getHeight());
        this.show();

    }//fechando construtor

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnNovo || evt.getSource() == mniNovo) {
            acao = 1; //cadastro
            mniExcluir.setEnabled(false);
            bbrExcluir.setEnabled(false);
            btnEditar.setEnabled(false);
            bbrEditar.setEnabled(false);
            mniEditar.setEnabled(false);
            desbloquearCampos(true);
            btnDesativar.setEnabled(false);
            bbrDesativar.setEnabled(false);
            mniDesativar.setEnabled(false);
            limparCampos();

        } else if (evt.getSource() == btnEditar || evt.getSource() == mniEditar) {
            acao = 2;
            desbloquearCampos(true);
            txtCampos[0].setEditable(false);
            mniExcluir.setEnabled(false);
            bbrExcluir.setEnabled(false);
            btnEditar.setEnabled(false);
            mniEditar.setEnabled(false);
            bbrEditar.setEnabled(false);

        } else if (evt.getSource() == btnSalvar || evt.getSource() == mniSalvar) {
            status = validarCampos();

            if (acao == 1) {
            
            if (status == true) {

                if (statusFoto == JFileChooser.APPROVE_OPTION) {
                    //salvar a foto
                    int ultimoPonto = strNomeArquivoOrigem.lastIndexOf(".");//pegando a posição do ultimo ponto
                    extensao = strNomeArquivoOrigem.substring(ultimoPonto + 1, strNomeArquivoOrigem.length());
                    strCaminhoDestino = "img\\system\\" + txtCampos[0].getText() + "." + extensao;

                    try {
                        flsEntrada = new FileInputStream(strCaminhoOrigem);
                        flsSaida = new FileOutputStream(strCaminhoDestino);

                        flcOrigem = flsEntrada.getChannel();
                        flcDestino = flsSaida.getChannel();

                        //cópia total do arquivo
                        flcOrigem.transferTo(0, flcOrigem.size(), flcDestino);

                        flcOrigem.close();
                        flcDestino.close();

                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(null, erro.getMessage());
                    }
                }

                
                    //cadastrar
                    try {
                        ProdutosVO novoProduto = new ProdutosVO();
                        //preenchendo objeto
                        novoProduto.setCodigo_PRODUTO(Integer.parseInt(txtCampos[0].getText()));
                        novoProduto.setNome_PRODUTO(txtCampos[1].getText());
                        novoProduto.setDescricao_PRODUTO(txtCampos[2].getText());
                        novoProduto.setQuantidadeEstoque_PRODUTO(Integer.parseInt(txtCampos[3].getText()));
                        novoProduto.setValorCompra_PRODUTO(Integer.parseInt(txtCampos[4].getText()));
                        novoProduto.setValorVenda_PRODUTO(Integer.parseInt(txtCampos[5].getText()));
                        //novoProduto.setStatus(1);
                       // novoProduto.setFoto_PRODUTO(txtCampos[0].getText() + "." + extensao);

                        ProdutosDAO. cadastrarProduto(novoProduto);
                        JOptionPane.showMessageDialog(null, "Produto " + novoProduto.getNome_PRODUTO() + " cadastrado.");
                        desbloquearCampos(false);
                        limparCampos();
                        carregarDados(0, "");

                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(null, erro.getMessage());
                    }       
            }
            
             } else if (acao == 2) {
                    //ALTERAÇÃO
                    try {
                        ProdutosVO produtoAtual = new ProdutosVO();
                        String tmpCodigo = txtCampos[0].getText();
                        //preenchendo objeto
                        produtoAtual.setNome_PRODUTO(txtCampos[1].getText());
                        produtoAtual.setDescricao_PRODUTO(txtCampos[2].getText());
                        produtoAtual.setQuantidadeEstoque_PRODUTO(Integer.parseInt(txtCampos[3].getText()));
                        produtoAtual.setValorCompra_PRODUTO(Integer.parseInt(txtCampos[4].getText()));
                        produtoAtual.setValorVenda_PRODUTO(Integer.parseInt(txtCampos[5].getText()));
                        
                        
                        ProdutosDAO.alterarProduto(produtoAtual, tmpCodigo);
                        JOptionPane.showMessageDialog(null, "Dados alterados.");
                        desbloquearCampos(false);
                        carregarDados(0, "");
                        
                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(null, erro.getMessage());
                    }
                }

        } else if (evt.getSource() == btnFoto) {

            JFileChooser flcFoto = new JFileChooser("C:\\Users\\280104398\\Documents");
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos de imagem(*.png, *.jpg)", "png", "jpg");
            flcFoto.setFileFilter(filtro);//vinculando chooser ao filtro
            statusFoto = flcFoto.showOpenDialog(this);//abre o explorer
 
            //preview da imagem
            strCaminhoOrigem = flcFoto.getSelectedFile().getPath();
            strNomeArquivoOrigem = flcFoto.getSelectedFile().getName();
            lblFoto.setIcon(new ImageIcon(strCaminhoOrigem));

        } else if (evt.getSource() == btnDesativar || evt.getSource() == mniDesativar) {
            try {
                String codigo;
                codigo = txtCampos[0].getText();
                
                Integer.parseInt(codigo); 
                

                ProdutosDAO.alterarStatus(codigo, statusAtual);
                carregarDados(0, "");
                carregarCampos(ProdutosDAO.consultarProduto(codigo));

                JOptionPane.showMessageDialog(null, "Status Alterado!");

            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, erro.getMessage());
            }

        } else if (evt.getSource() == btnRestaurar) {
            carregarDados(0, "");
            txtBusca.setText("");

        } else if (evt.getSource() == btnNome) {
            String nome = JOptionPane.showInputDialog(
                    "Entre com o nome do produto.");

            carregarDados(2, nome);
            txtBusca.setText("");

        } else if (evt.getSource() == btnCodigo) {
            String codigo = JOptionPane.showInputDialog(
                    "Entre com o codigo: ");

            carregarDados(1, codigo);
            txtBusca.setText("");

        }else if(evt.getSource() == mniConsulta){
            String codigo = JOptionPane.showInputDialog
                            ("Informe o CPF do produto:");
            
            try{
                ProdutosVO dadosProduto = ProdutosDAO.consultarProduto(Integer.parseInt(codigo));
                
                if(dadosProduto == null){
                    JOptionPane.showMessageDialog(null,"Produto não encontrado");
                }else{
                    ProdutosView.carregarCampos(dadosProduto);
                }
            
            }catch(Exception erro){
                JOptionPane.showMessageDialog(null,erro.getMessage());
            }
        }else if(evt.getSource() == mniExcluir){
            
            try{
                String codigo = txtCampos[0].getText();
                
                int verif = JOptionPane.showConfirmDialog(
                            null, "Deseja realmente excluir " + 
                                   txtCampos[1].getText(),
                                   "Exclusão de Dados",
                                    JOptionPane.YES_NO_OPTION);
                
                if(verif == JOptionPane.YES_OPTION){
                    ProdutosDAO.excluirProduto(codigo);
                    JOptionPane.showMessageDialog(null, "Produto excluído");
                    desbloquearCampos(false);
                    limparCampos();
                    carregarDados(0,"");                    
                }
                    bbrExcluir.setEnabled(false);
                    mniExcluir.setEnabled(false);
                
            }catch(Exception erro){
                JOptionPane.showMessageDialog(null, erro.getMessage());
            }
            
        }
    }//fechando actionPerformed

    public static void carregarDados(int tmpTipo, String tmpBusca) {

        try {

            java.util.List<ProdutosVO> lstProdutos = new ArrayList<ProdutosVO>();

            //limpando lista
            while (mdlProdutos.getRowCount() > 0) {
                mdlProdutos.removeRow(0);
            }

            //DAO >> VIEW
            lstProdutos = ProdutosDAO.listarProdutos(tmpTipo, tmpBusca);

            for (ProdutosVO tmpProduto : lstProdutos) {//para cada obj produto dentro da lista

                String dados[] = new String[4];
                dados[0] = Integer.toString(tmpProduto.getCodigo_PRODUTO());
                dados[1] = tmpProduto.getNome_PRODUTO();
                dados[2] = Float.toString(tmpProduto.getValorVenda_PRODUTO());
                dados[3] = Integer.toString(tmpProduto.getQuantidadeEstoque_PRODUTO());

                mdlProdutos.addRow(dados);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro);
        }

    }//fechando carregarDados

    public static void carregarCampos(ProdutosVO tmpProduto) {
        txtCampos[0].setText(Integer.toString(tmpProduto.getCodigo_PRODUTO()));
        txtCampos[1].setText(tmpProduto.getNome_PRODUTO());
        txtCampos[2].setText(tmpProduto.getDescricao_PRODUTO());
        txtCampos[3].setText(Integer.toString(tmpProduto.getQuantidadeEstoque_PRODUTO()));
        txtCampos[4].setText(Float.toString(tmpProduto.getValorCompra_PRODUTO()));
        txtCampos[5].setText(Float.toString(tmpProduto.getValorVenda_PRODUTO()));
        //statusAtual = tmpProduto.getStatus();

        lblFoto.setIcon(new ImageIcon("img/system/" + tmpProduto.getFoto_PRODUTO()));

        if (tmpProduto.getStatus() == 0) {
            txtCampos[0].setForeground(Color.red); //codigo
            txtCampos[1].setForeground(Color.red); //nome

            btnDesativar.setText("Ativar");

        } else {
            txtCampos[0].setForeground(Color.black); //codigo
            txtCampos[1].setForeground(Color.black); //nome

            btnDesativar.setText("Desativar");
        }

        desbloquearCampos(false);
        btnDesativar.setEnabled(true);
        mniDesativar.setEnabled(true);
        bbrDesativar.setEnabled(true);
        mniExcluir.setEnabled(true);
        bbrExcluir.setEnabled(true);
        btnEditar.setEnabled(true);
        mniEditar.setEnabled(true);
        bbrEditar.setEnabled(true);

    }//fechando carregarCampos

    public static void desbloquearCampos(boolean tmpStatus) {
        for (int i = 0; i < txtCampos.length; i++) {
            txtCampos[i].setEditable(tmpStatus);
        }
        btnFoto.setEnabled(tmpStatus);
        btnDesativar.setEnabled(tmpStatus);
        mniDesativar.setEnabled(tmpStatus);
        bbrDesativar.setEnabled(tmpStatus);
        btnSalvar.setEnabled(tmpStatus);
        mniSalvar.setEnabled(tmpStatus);
        bbrSalvar.setEnabled(tmpStatus);
        btnNovo.setEnabled(!tmpStatus);
        mniNovo.setEnabled(!tmpStatus);
        bbrNovo.setEnabled(!tmpStatus);
    }

    public static void limparCampos() {
        for (int i = 0; i < txtCampos.length; i++) {
            txtCampos[i].setText(null);
        }
        lblFoto.setIcon(new ImageIcon("img/prod.png"));
    }

    public static boolean validarCampos() {

        for (int i = 0; i < txtCampos.length; i++) {
            if (txtCampos[i].getText().trim().equals("")) {
                JOptionPane.showMessageDialog(null,
                        "Preencha o campo " + strCampos[i]);
                txtCampos[i].grabFocus();//move o cursor pro campo espec.
                return false;
            }//fechando if
        }//fechando for                
        return true;
    }//fechando validar

}//fechando classe
