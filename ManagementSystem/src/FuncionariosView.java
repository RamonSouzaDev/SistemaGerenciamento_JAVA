
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

public class FuncionariosView extends JInternalFrame implements ActionListener {

    //declaração de objetos
    public static JMenuBar mbrFuncionarios;
    public static JMenu mnuArquivo, mnuDados, mnuAjuda;
    public static JMenuItem mniNovo, mniEditar, mniSalvar,
                            mniExcluir, mniFechar,
                            mniConsulta, mniDesativar,
                            mniSobre;
    
    public static Container ctnFuncionarios;
    
    public static JToolBar tbrFuncionarios;
    public static ImageIcon icnNovo, icnEditar,
                            icnSalvar, icnDesativar,
                            icnBuscar, icnExcluir,
                            icnImprimir, icnFechar;
    public static BotoesBarra bbrNovo, bbrEditar,
                              bbrSalvar, bbrDesativar, 
                              bbrBuscar, bbrExcluir,
                              bbrImprimir, bbrFechar;

    public static String strCampos[] = {"CPF:", "Nome:", "Data de Nascimento:", "Endereço:", "Bairro:", "Cidade:", "Telefone:", "E-mail:", "Naturalidade:", "Número do PIS:", "Nome do pai:", "Nome da mãe:", "Função:", "Salário:", "Período:", "Banco:"};
    public static JLabel lblCampos[];
    public static JTextField txtCampos[];

    public static ImageIcon imgFoto;
    public static JLabel lblFoto;

    public static String strTopo[] = {"CPF", "Nome", "Função", "Período"};
    public static JScrollPane scrFuncionarios; //barra de rolagem da tabela
    public static JTable tblFuncionarios;//tabela
    public static DefaultTableModel mdlFuncionarios;//Classe que gerencia o conteudo da tabela

    public static JLabel lblBusca;
    public static JTextField txtBusca;
    public static JButton btnBusca;

    public static JButton btnNovo, btnSalvar, btnDesativar, btnFoto, btnEditar;
    public static ImageIcon icnPais, icnUsuario, icnRestaurar, icnBloquear;
    public static JButton btnCidade, btnNome, btnRestaurar;

    //Declaração de variaveis e objetos auxiliares
    public static FileChannel flcOrigem, flcDestino;//cópia
    public static FileInputStream flsEntrada;//leitura
    public static FileOutputStream flsSaida;//leitura
    public static String strCaminhoOrigem, strCaminhoDestino, strNomeArquivoOrigem, extensao;
    public static int statusFoto;
    public static int statusAtual = 0,acao;
    public static boolean status;

    public FuncionariosView() {//construtor
        super("Gerenciamento de Funcionários");

        //Construção dos objetos
        icnNovo = new ImageIcon("img/icons/new.png");
        icnEditar = new ImageIcon("img/icons/edit.png");
        icnSalvar = new ImageIcon("img/icons/save.png");
        icnDesativar = new ImageIcon("img/icons/block.png");
        icnBuscar = new ImageIcon("img/icons/search.png");
        icnExcluir = new ImageIcon("img/icons/delete.png");
        icnImprimir = new ImageIcon("img/icons/report.png");
        icnFechar = new ImageIcon("img/icons/exit.png");
        
        mbrFuncionarios = new JMenuBar();
        this.setJMenuBar(mbrFuncionarios);
        
        mnuArquivo = new JMenu("Arquivo");
        mnuArquivo.setMnemonic('a');
        mbrFuncionarios.add(mnuArquivo);
        
        mniNovo = new JMenuItem("Novo Funcionario",icnNovo);
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
        
        mniExcluir = new JMenuItem("Excluir funcionario atual", icnExcluir);
        mniExcluir.setEnabled(false);
        mniExcluir.addActionListener(this);
        mnuArquivo.add(mniExcluir);
        
        mnuArquivo.add(new JSeparator());
        
        mniFechar = new JMenuItem("Fechar módulo FUNCIONARIOS", icnFechar);
        mniFechar.addActionListener(this);
        mnuArquivo.add(mniFechar);
        
        mnuDados = new JMenu("Dados");
        mnuDados.setMnemonic('d');
        mbrFuncionarios.add(mnuDados);
        
        mniConsulta = new JMenuItem("Consulta por CPF",icnBuscar);
        mniConsulta.addActionListener(this);
        mnuDados.add(mniConsulta);
        
        mnuDados.add(new JSeparator());
        
        mniDesativar = new JMenuItem("Alterar Status",icnBloquear);
        mniDesativar.setEnabled(false);
        mniDesativar.addActionListener(this);
        mnuDados.add(mniDesativar);
        
        mnuAjuda = new JMenu("Ajuda");
        mnuAjuda.setMnemonic('h');
        mbrFuncionarios.add(mnuAjuda);
        
        mniSobre = new JMenuItem("Sobre o sistema");
        mniSobre.addActionListener(this);
        mnuAjuda.add(mniSobre);
        
        ctnFuncionarios = new Container();
        ctnFuncionarios.setLayout(null);
        this.add(ctnFuncionarios);
        
        tbrFuncionarios = new JToolBar();
        tbrFuncionarios.setBounds(0,0,285,35);
        ctnFuncionarios.add(tbrFuncionarios);
        
        /********************************BARRA DE FERRAMENTAS******************************/
        
        bbrNovo = new BotoesBarra(0,icnNovo,"Novo Funcionario");
        tbrFuncionarios.add(bbrNovo);
        
        bbrEditar = new BotoesBarra(1,icnEditar,"Editar Dados");
        bbrEditar.setEnabled(false);
        tbrFuncionarios.add(bbrEditar);
        
        bbrSalvar = new BotoesBarra(2,icnSalvar,"Salvar Informações");
        tbrFuncionarios.add(bbrSalvar);
        
        bbrDesativar = new BotoesBarra(3,icnDesativar,"Alterar Status");
        bbrDesativar.setEnabled(false);
        tbrFuncionarios.add(bbrDesativar);
        
        bbrBuscar = new BotoesBarra(4,icnBuscar,"Buscar por CPF");
        tbrFuncionarios.add(bbrBuscar);
        
        bbrExcluir = new BotoesBarra(5,icnExcluir,"Remover funcionario atual");
        bbrExcluir.setEnabled(false);
        tbrFuncionarios.add(bbrExcluir);
        
        bbrImprimir = new BotoesBarra(6,icnImprimir,"Gerar arquivo texto");
        tbrFuncionarios.add(bbrImprimir);
        
        bbrFechar = new BotoesBarra(7,icnFechar,"Fechar módulo");
        tbrFuncionarios.add(bbrFechar);

        lblCampos = new JLabel[strCampos.length];
        txtCampos = new JTextField[strCampos.length];

        for (int i = 0; i < lblCampos.length; i++) {
            lblCampos[i] = new JLabel(strCampos[i]);
            lblCampos[i].setBounds(30, 75 + (i * 30), 150, 20);
            ctnFuncionarios.add(lblCampos[i]);

            txtCampos[i] = new JTextField();
            txtCampos[i].setBounds(160, 75 + (i * 30), 240, 20);
            ctnFuncionarios.add(txtCampos[i]);

        }//fechando for

        btnEditar = new JButton("Editar Dados");
        btnEditar.setEnabled(false);        
        btnEditar.addActionListener(this);
        btnEditar.setBounds(430, 365, 150, 30);
        ctnFuncionarios.add(btnEditar);

        imgFoto = new ImageIcon("img/user.png");
        lblFoto = new JLabel(imgFoto);
        lblFoto.setBounds(440, 75, 128, 128);
        ctnFuncionarios.add(lblFoto);

        btnFoto = new JButton("Selecionar imagem");
        btnFoto.addActionListener(this);
        btnFoto.setBounds(430, 215, 150, 20);
        ctnFuncionarios.add(btnFoto);

        tblFuncionarios = new JTable();
        scrFuncionarios = new JScrollPane(tblFuncionarios);
        mdlFuncionarios = (DefaultTableModel) tblFuncionarios.getModel();

        //Inserindo elementos no topo da tabela
        for (int i = 0; i < strTopo.length; i++) {
            mdlFuncionarios.addColumn(strTopo[i]);
        }

        scrFuncionarios.setBounds(600, 105, 550, 290);
        ctnFuncionarios.add(scrFuncionarios);

        tblFuncionarios.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                try {
                    String tmpCpf = tblFuncionarios.getValueAt(tblFuncionarios.getSelectedRow(), 0).toString();
                    FuncionariosVO tmpFuncionario = FuncionariosDAO.consultarFuncionario(tmpCpf);
                    carregarCampos(tmpFuncionario);

                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, erro.getMessage());
                }
            }
        });

        lblBusca = new JLabel("Busca Rápida:");
        lblBusca.setBounds(600, 75, 100, 20);
        ctnFuncionarios.add(lblBusca);

        txtBusca = new JTextField();
        txtBusca.setBounds(690, 75, 450, 20);
        ctnFuncionarios.add(txtBusca);

        txtBusca.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                carregarDados(3, txtBusca.getText());
            }
        }
        );

        btnNovo = new JButton("Novo Funcionario");
        btnNovo.addActionListener(this);
        btnNovo.setBounds(430, 265, 150, 30);
        ctnFuncionarios.add(btnNovo);

        btnSalvar = new JButton("Salvar dados");
        btnSalvar.addActionListener(this);
        btnSalvar.setBounds(430, 315, 150, 30);
        ctnFuncionarios.add(btnSalvar);

        icnBloquear = new ImageIcon("img/icons/block.png");

        btnDesativar = new JButton("Desativar", icnBloquear);
        btnDesativar.setEnabled(false);
        btnDesativar.addActionListener(this);
        btnDesativar.setBounds(430, 415, 150, 30);
        ctnFuncionarios.add(btnDesativar);

        icnPais = new ImageIcon("img/icons/country.png");
        icnUsuario = new ImageIcon("img/icons/user.png");
        icnRestaurar = new ImageIcon("img/icons/restore.png");

        btnCidade = new JButton("por Cidade", icnPais);
        btnCidade.addActionListener(this);
        btnCidade.setBounds(1170, 105, 150, 30);
        ctnFuncionarios.add(btnCidade);

        btnNome = new JButton("por Nome", icnUsuario);
        btnNome.addActionListener(this);
        btnNome.setBounds(1170, 155, 150, 30);
        ctnFuncionarios.add(btnNome);

        btnRestaurar = new JButton("Restaurar", icnRestaurar);
        btnRestaurar.addActionListener(this);
        btnRestaurar.setBounds(1170, 205, 150, 30);
        ctnFuncionarios.add(btnRestaurar);

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
                    strCaminhoDestino = "C:\\Users\\Junior\\Desktop\\Ramon Mendes\\4CIC - 2019.2 - Prog. Avanc\\ManagementSystem\\img\\system" + txtCampos[0].getText() + "." + extensao;

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
                        FuncionariosVO novoFuncionario = new FuncionariosVO();
                        //preenchendo objeto
                        novoFuncionario.setCpf(txtCampos[0].getText());
                        novoFuncionario.setNome(txtCampos[1].getText());
                        novoFuncionario.setDataNascimento(txtCampos[2].getText());
                        novoFuncionario.setEndereco(txtCampos[3].getText());
                        novoFuncionario.setBairro(txtCampos[4].getText());
                        novoFuncionario.setCidade(txtCampos[5].getText());
                        novoFuncionario.setTelefone(txtCampos[6].getText());
                        novoFuncionario.setEmail(txtCampos[7].getText());
                        novoFuncionario.setNaturalidade(txtCampos[8].getText());
                        novoFuncionario.setNumeroPIS(txtCampos[9].getText());
                        novoFuncionario.setPai(txtCampos[10].getText());
                        novoFuncionario.setMae(txtCampos[11].getText());
                        novoFuncionario.setCargo(txtCampos[12].getText());
                        novoFuncionario.setSalario(txtCampos[13].getText());
                        novoFuncionario.setPeriodo(txtCampos[14].getText());
                        novoFuncionario.setBanco(txtCampos[15].getText());
                        novoFuncionario.setStatus(1);
                        novoFuncionario.setFoto(txtCampos[0].getText() + "." + extensao);

                        FuncionariosDAO.cadastrarFuncionario(novoFuncionario);
                        JOptionPane.showMessageDialog(null, "Funcionario " + novoFuncionario.getNome() + " cadastrado.");
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
                        FuncionariosVO funcionarioAtual = new FuncionariosVO();
                        String tmpCpf = txtCampos[0].getText();
                        //preenchendo objeto
                        funcionarioAtual.setNome(txtCampos[1].getText());
                        funcionarioAtual.setDataNascimento(txtCampos[2].getText());
                        funcionarioAtual.setEndereco(txtCampos[3].getText());
                        funcionarioAtual.setBairro(txtCampos[4].getText());
                        funcionarioAtual.setCidade(txtCampos[5].getText());
                        funcionarioAtual.setTelefone(txtCampos[6].getText());
                        funcionarioAtual.setEmail(txtCampos[7].getText());
                        funcionarioAtual.setNaturalidade(txtCampos[8].getText());
                        funcionarioAtual.setNumeroPIS(txtCampos[9].getText());
                        funcionarioAtual.setPai(txtCampos[10].getText());
                        funcionarioAtual.setMae(txtCampos[11].getText());
                        funcionarioAtual.setCargo(txtCampos[12].getText());
                        funcionarioAtual.setSalario(txtCampos[13].getText());
                        funcionarioAtual.setPeriodo(txtCampos[14].getText());
                        funcionarioAtual.setBanco(txtCampos[15].getText());
                        funcionarioAtual.setStatus(1);
                        funcionarioAtual.setFoto(txtCampos[0].getText() + "." + extensao);
                        

                        
                        FuncionariosDAO.alterarFuncionario(funcionarioAtual, tmpCpf);
                        JOptionPane.showMessageDialog(null, "Dados alterados.");
                        desbloquearCampos(false);
                        carregarDados(0, "");
                        
                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(null, erro.getMessage());
                    }
                }

        } else if (evt.getSource() == btnFoto) {

            JFileChooser flcFoto = new JFileChooser("C:\\Users\\Junior\\Desktop\\Ramon Mendes\\4CIC - 2019.2 - Prog. Avanc\\ManagementSystem\\img\\system");
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Arquivos de imagem(*.png, *.jpg)", "png", "jpg");
            flcFoto.setFileFilter(filtro);//vinculando chooser ao filtro
            statusFoto = flcFoto.showOpenDialog(this);//abre o explorer

            //preview da imagem
            strCaminhoOrigem = flcFoto.getSelectedFile().getPath();
            strNomeArquivoOrigem = flcFoto.getSelectedFile().getName();
            lblFoto.setIcon(new ImageIcon(strCaminhoOrigem));

        } else if (evt.getSource() == btnDesativar || evt.getSource() == mniDesativar) {
            try {
                String cpf = txtCampos[0].getText();

                FuncionariosDAO.alterarStatus(cpf, statusAtual);
                carregarDados(0, "");
                carregarCampos(FuncionariosDAO.consultarFuncionario(cpf));

                JOptionPane.showMessageDialog(null, "Status Alterado!");

            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, erro.getMessage());
            }

        } else if (evt.getSource() == btnRestaurar) {
            carregarDados(0, "");
            txtBusca.setText("");

        } else if (evt.getSource() == btnNome) {
            String nome = JOptionPane.showInputDialog(
                    "Entre com o nome do funcionario.");

            carregarDados(2, nome);
            txtBusca.setText("");

        } else if (evt.getSource() == btnCidade) {
            String cidade = JOptionPane.showInputDialog(
                    "Entre com a cidade: ");

            carregarDados(1, cidade);
            txtBusca.setText("");

        }else if(evt.getSource() == mniConsulta){
            String cpf = JOptionPane.showInputDialog
                            ("Informe o CPF do funcionario:");
            
            try{
                FuncionariosVO dadosFuncionario = FuncionariosDAO.consultarFuncionario(cpf);
                
                if(dadosFuncionario == null){
                    JOptionPane.showMessageDialog(null,"Funcionario não encontrado");
                }else{
                    FuncionariosView.carregarCampos(dadosFuncionario);
                }
            
            }catch(Exception erro){
                JOptionPane.showMessageDialog(null,erro.getMessage());
            }
        }else if(evt.getSource() == mniExcluir){
            
            try{
                String cpf = txtCampos[0].getText();
                
                int verif = JOptionPane.showConfirmDialog(
                            null, "Deseja realmente excluir " + 
                                   txtCampos[1].getText(),
                                   "Exclusão de Dados",
                                    JOptionPane.YES_NO_OPTION);
                
                if(verif == JOptionPane.YES_OPTION){
                    FuncionariosDAO.excluirFuncionario(cpf);
                    JOptionPane.showMessageDialog(null, "Funcionario excluído");
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

            java.util.List<FuncionariosVO> lstFuncionarios = new ArrayList<FuncionariosVO>();

            //limpando lista
            while (mdlFuncionarios.getRowCount() > 0) {
                mdlFuncionarios.removeRow(0);
            }

            //DAO >> VIEW
            lstFuncionarios = FuncionariosDAO.listarFuncionarios(tmpTipo, tmpBusca);

            for (FuncionariosVO tmpFuncionario : lstFuncionarios) {//para cada obj cliente dentro da lista

                String dados[] = new String[4];
                dados[0] = tmpFuncionario.getCpf();
                dados[1] = tmpFuncionario.getNome();
                dados[2] = tmpFuncionario.getCidade();
                dados[3] = tmpFuncionario.getTelefone();
                
                
                mdlFuncionarios.addRow(dados);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro);
        }

    }//fechando carregarDados

    public static void carregarCampos(FuncionariosVO tmpFuncionario) {
        txtCampos[0].setText(tmpFuncionario.getCpf());
        txtCampos[1].setText(tmpFuncionario.getNome());
        txtCampos[2].setText(tmpFuncionario.getDataNascimento());
        txtCampos[3].setText(tmpFuncionario.getEndereco());
        txtCampos[4].setText(tmpFuncionario.getBairro());
        txtCampos[5].setText(tmpFuncionario.getCidade());
        txtCampos[6].setText(tmpFuncionario.getTelefone());
        txtCampos[7].setText(tmpFuncionario.getEmail());
        txtCampos[8].setText(tmpFuncionario.getNaturalidade());
        txtCampos[9].setText(tmpFuncionario.getNumeroPIS());
        txtCampos[10].setText(tmpFuncionario.getPai());
        txtCampos[11].setText(tmpFuncionario.getMae());
        txtCampos[12].setText(tmpFuncionario.getCargo());
        txtCampos[13].setText(tmpFuncionario.getSalario());
        txtCampos[14].setText(tmpFuncionario.getPeriodo());
        txtCampos[15].setText(tmpFuncionario.getBanco());
        
        
        statusAtual = tmpFuncionario.getStatus();

        lblFoto.setIcon(new ImageIcon("Users/Junior/Desktop/Ramon Mendes/4CIC - 2019.2 - Prog. Avanc/ManagementSystem/img/system/" + tmpFuncionario.getFoto()));
        
       // C:\\Users/Junior/Desktop/Ramon Mendes/4CIC - 2019.2 - Prog. Avanc/ManagementSystem/img/system

        if (tmpFuncionario.getStatus() == 0) {
            txtCampos[0].setForeground(Color.red); //cpf
            txtCampos[1].setForeground(Color.red); //nome

            btnDesativar.setText("Ativar");

        } else {
            txtCampos[0].setForeground(Color.black); //cpf
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
        lblFoto.setIcon(new ImageIcon("img/user.png"));
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

