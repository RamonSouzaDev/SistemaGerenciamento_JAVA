
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class FornecedorView extends JInternalFrame implements ActionListener {

    public static JLabel lblCep, lblEndereco, lblBairro, lblCidade, lblEstado, lblUf, lblTipoPessoa, lblBuscar, lblCategoria;
    public static JLabel lblNome, lblFantasia, lblCnpjCpf, lblInscEstadual, lblFone, lblFax, lblContato, lblEmail, lblLegenda;
    public static JTextField txtCep, txtEndereco, txtBairro, txtCidade, txtEstado, txtBuscar;
    public static JTextField txtNome, txtFantasia, txtCnpjCpf, txtInscEstadual, txtFone, txtEmail;
    public static JComboBox cmbUf, cmbTipoPessoa, cmbCategoria;
    public static JButton btnNovo, btnEditar, btnDeletar, btnSalvar, btnBuscar;
    public static JCheckBox chbAtivo;

    public static String strTopo[] = {"CNPJ/CPF", "Nome", "Nome Fantasia", "Inscriçao Estadual", "Cidade", "Estado"};
    public static JScrollPane scrFornecedor;
    public static JTable tblFornecedor;
    public static DefaultTableModel mdlFornecedor;

    public Container ctnFornecedor;
    // variaveis auxiliares
    public static int id, acao;

    public FornecedorView() {

        super("Gerenciamento de Fornecedor");

        ctnFornecedor = new Container();
        ctnFornecedor.setLayout(null);
        this.add(ctnFornecedor);

        lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 48, 150, 20);
        ctnFornecedor.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(130, 48, 250, 20);
        ctnFornecedor.add(txtNome);

        lblFantasia = new JLabel("Nome Fantasia:");
        lblFantasia.setBounds(410, 48, 150, 20);
        ctnFornecedor.add(lblFantasia);

        txtFantasia = new JTextField();
        txtFantasia.setBounds(541, 48, 250, 20);
        ctnFornecedor.add(txtFantasia);

        chbAtivo = new JCheckBox();
        chbAtivo.setBounds(821, 48, 20, 20);
        chbAtivo.setSelected(true);
        ctnFornecedor.add(chbAtivo);

        lblLegenda = new JLabel("Ativo");
        lblLegenda.setBounds(851, 48, 50, 20);
        ctnFornecedor.add(lblLegenda);

        lblTipoPessoa = new JLabel("Tipo Pessoa:");
        lblTipoPessoa.setBounds(30, 88, 150, 20);
        ctnFornecedor.add(lblTipoPessoa);

        String tipoPesOpt[] = {"Fisica", "Juridica"};
        cmbTipoPessoa = new JComboBox(tipoPesOpt);
        cmbTipoPessoa.setBounds(130, 88, 150, 20);
        ctnFornecedor.add(cmbTipoPessoa);

        lblCnpjCpf = new JLabel("CNPJ / CPF:");
        lblCnpjCpf.setBounds(309, 88, 150, 20);
        ctnFornecedor.add(lblCnpjCpf);

        txtCnpjCpf = new JTextField();
        txtCnpjCpf.setBounds(408, 88, 150, 20);
        ctnFornecedor.add(txtCnpjCpf);

        lblInscEstadual = new JLabel("Inscrição Estadual:");
        lblInscEstadual.setBounds(584, 88, 150, 20);
        ctnFornecedor.add(lblInscEstadual);

        txtInscEstadual = new JTextField();
        txtInscEstadual.setBounds(741, 88, 150, 20);
        ctnFornecedor.add(txtInscEstadual);

        lblCep = new JLabel("Cep:");
        lblCep.setBounds(30, 128, 150, 20);
        ctnFornecedor.add(lblCep);

        txtCep = new JTextField();
        txtCep.setBounds(130, 128, 150, 20);
        ctnFornecedor.add(txtCep);

        lblEndereco = new JLabel("Endereço:");
        lblEndereco.setBounds(309, 128, 150, 20);
        ctnFornecedor.add(lblEndereco);

        txtEndereco = new JTextField();
        txtEndereco.setBounds(408, 128, 300, 20);
        ctnFornecedor.add(txtEndereco);

        lblUf = new JLabel("UF:");
        lblUf.setBounds(761, 128, 60, 20);
        ctnFornecedor.add(lblUf);

        String ufOpt[] = {"AC","AL","AM","AP","BA","CE","DF","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
        cmbUf = new JComboBox(ufOpt);
        cmbUf.setBounds(811, 128, 80, 20);
        ctnFornecedor.add(cmbUf);

        lblBairro = new JLabel("Bairro:");
        lblBairro.setBounds(30, 168, 60, 20);
        ctnFornecedor.add(lblBairro);

        txtBairro = new JTextField();
        txtBairro.setBounds(130, 168, 150, 20);
        ctnFornecedor.add(txtBairro);

        lblCidade = new JLabel("Cidade:");
        lblCidade.setBounds(305, 168, 80, 20);
        ctnFornecedor.add(lblCidade);

        txtCidade = new JTextField();
        txtCidade.setBounds(408, 168, 150, 20);
        ctnFornecedor.add(txtCidade);

        lblEstado = new JLabel("Estado:");
        lblEstado.setBounds(660, 168, 100, 20);
        ctnFornecedor.add(lblEstado);

        txtEstado = new JTextField();
        txtEstado.setBounds(741, 168, 150, 20);
        ctnFornecedor.add(txtEstado);

        lblFone = new JLabel("Fone:");
        lblFone.setBounds(30, 208, 80, 20);
        ctnFornecedor.add(lblFone);

        txtFone = new JTextField();
        txtFone.setBounds(130, 208, 150, 20);
        ctnFornecedor.add(txtFone);

        lblEmail = new JLabel("Email:");
        lblEmail.setBounds(309, 208, 100, 20);
        ctnFornecedor.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setBounds(408, 208, 300, 20);
        ctnFornecedor.add(txtEmail);

        lblBuscar = new JLabel("Buscar:");
        lblBuscar.setBounds(30, 303, 150, 20);
        ctnFornecedor.add(lblBuscar);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(130, 303, 300, 20);
        ctnFornecedor.add(txtBuscar);

        lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(460, 303, 150, 20);
        ctnFornecedor.add(lblCategoria);

        cmbCategoria = new JComboBox(strTopo);
        cmbCategoria.setBounds(550, 303, 150, 20);
        ctnFornecedor.add(cmbCategoria);

        //********************** Buttons **********************
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this);
        btnBuscar.setBounds(740, 298, 150, 30);
        ctnFornecedor.add(btnBuscar);

        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(this);
        btnNovo.setBounds(940, 48, 150, 40);
        ctnFornecedor.add(btnNovo);

        btnEditar = new JButton("Editar");
        btnEditar.addActionListener(this);
        btnEditar.setBounds(940, 118, 150, 40);
        ctnFornecedor.add(btnEditar);

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(this);
        btnSalvar.setBounds(940, 188, 150, 40);
        ctnFornecedor.add(btnSalvar);

        btnDeletar = new JButton("Deletar");
        btnDeletar.addActionListener(this);
        btnDeletar.setBounds(940, 258, 150, 40);
        ctnFornecedor.add(btnDeletar);

        //********************** Table **********************
        tblFornecedor = new JTable();
        scrFornecedor = new JScrollPane(tblFornecedor);
        mdlFornecedor = (DefaultTableModel) tblFornecedor.getModel();

        for (int i = 0; i < strTopo.length; i++) {
            mdlFornecedor.addColumn(strTopo[i]);

        }
        scrFornecedor.setBounds(30, 343, 859, 250);
        ctnFornecedor.add(scrFornecedor);
        //quando clicar na tabela.
        tblFornecedor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                try {
                    String tmpCnpjCpf = tblFornecedor.getValueAt(tblFornecedor.getSelectedRow(), 0).toString();
                    FornecedorVO tmpForn = FornecedorDAO.consultarFornecedor(tmpCnpjCpf);
                    preencherCampos(tmpForn);

                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, erro.getMessage());
                }
            }
        });

        //preenchendo tabela
        carregarDados(0, "");
        desbloquearCampos(false);
        
        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent evt) {
                MainView.btnMenu[2].setEnabled(true);
            }
        });

        this.setIconifiable(true);
        this.setClosable(true);
        this.setSize(1200,700);
        this.show();
    }

    public static void carregarDados(int tmpTipo, String tmpBusca) {

        try {

            java.util.List<FornecedorVO> lstForn = new ArrayList<FornecedorVO>();

            //limpando lista
            while (mdlFornecedor.getRowCount() > 0) {
                mdlFornecedor.removeRow(0);
            }

            //DAO >> VIEW
            lstForn = FornecedorDAO.listarFornecedor(tmpTipo, tmpBusca);

            for (FornecedorVO tmpForn : lstForn) {//para cada obj cliente dentro da lista

                String dados[] = new String[6];
                dados[0] = tmpForn.getCnpjCpf();
                dados[1] = tmpForn.getNome();
                dados[2] = tmpForn.getFantasia();
                dados[3] = tmpForn.getInscEstadual();
                dados[4] = tmpForn.getCidade();
                dados[5] = tmpForn.getEstado();

                mdlFornecedor.addRow(dados);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro);
        }

    }//fechando carregarDados

    public static void preencherCampos(FornecedorVO tmpForn) {

        txtNome.setText(tmpForn.getNome());
        txtFantasia.setText(tmpForn.getFantasia());
        cmbTipoPessoa.setSelectedIndex(tmpForn.getTipoPessoa());
        txtCnpjCpf.setText(tmpForn.getCnpjCpf());
        txtInscEstadual.setText(tmpForn.getInscEstadual());
        txtCep.setText(tmpForn.getCep());
        txtEndereco.setText(tmpForn.getEndereco());
        cmbUf.setSelectedItem(tmpForn.getUf());
        txtBairro.setText(tmpForn.getBairro());
        txtCidade.setText(tmpForn.getCidade());
        txtEstado.setText(tmpForn.getEstado());
        txtFone.setText(tmpForn.getFone());
        txtEmail.setText(tmpForn.getEmail());

        if (tmpForn.getStatus() == 1) {
            chbAtivo.setSelected(true);
            txtNome.setForeground(Color.BLACK);
            txtFantasia.setForeground(Color.BLACK);
            txtCnpjCpf.setForeground(Color.BLACK);

        } else {
            chbAtivo.setSelected(false);
            txtNome.setForeground(Color.RED);
            txtFantasia.setForeground(Color.RED);
            txtCnpjCpf.setForeground(Color.RED);
        }
        desbloquearCampos(false);
        btnEditar.setEnabled(true);
        btnDeletar.setEnabled(true);
        btnNovo.setEnabled(false);
        

    }

    public static boolean validarCampos() {

        if (txtNome.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo Nome");
            txtNome.grabFocus();

            return false;

        } else if (txtFantasia.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo Nome Fantasia");
            txtFantasia.grabFocus();

            return false;

        } else if (txtCnpjCpf.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo CNPJ / CPF");
            txtCnpjCpf.grabFocus();

            return false;

        } else if (txtCep.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo CEP");
            txtCep.grabFocus();

            return false;

        } else if (txtEndereco.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo Endereço");
            txtEndereco.grabFocus();

            return false;

        } else if (txtBairro.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo Bairro");
            txtBairro.grabFocus();

            return false;

        } else if (txtCidade.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo Cidade");
            txtCidade.grabFocus();

            return false;

        } else if (txtEstado.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo Estado");
            txtEstado.grabFocus();

            return false;

        } else if (txtFone.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo Fone");
            txtFone.grabFocus();

            return false;

        } else if (txtEmail.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo Email");
            txtEmail.grabFocus();

            return false;

        }

        return true;

    }

    public static void limparCampos() {

        txtNome.setText(null);
        txtFantasia.setText(null);
        txtFantasia.setText(null);
        cmbTipoPessoa.setSelectedIndex(0);
        txtCnpjCpf.setText(null);
        txtInscEstadual.setText(null);
        txtCep.setText(null);
        txtEndereco.setText(null);
        cmbUf.setSelectedItem(null);
        txtBairro.setText(null);
        txtCidade.setText(null);
        txtEstado.setText(null);
        txtFone.setText(null);
        txtEmail.setText(null);
        chbAtivo.setSelected(true);

        txtNome.grabFocus();
    }
    
    public static void desbloquearCampos(boolean tmpstatus){
       
        txtNome.setEnabled(tmpstatus);
        txtFantasia.setEnabled(tmpstatus);
        cmbTipoPessoa.setEnabled(tmpstatus);
        txtCnpjCpf.setEnabled(tmpstatus);
        txtInscEstadual.setEnabled(tmpstatus);
        txtCep.setEnabled(tmpstatus);
        txtEndereco.setEnabled(tmpstatus);
        cmbUf.setEnabled(tmpstatus);
        txtBairro.setEnabled(tmpstatus);
        txtCidade.setEnabled(tmpstatus);
        txtEstado.setEnabled(tmpstatus);
        txtFone.setEnabled(tmpstatus);
        txtEmail.setEnabled(tmpstatus);
        chbAtivo.setEnabled(tmpstatus);
        
        btnNovo.setEnabled(!tmpstatus);
        btnEditar.setEnabled(tmpstatus);
        btnSalvar.setEnabled(tmpstatus);
        btnDeletar.setEnabled(tmpstatus);
        
        
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnNovo) {
            //cadastro novo
            acao = 0;
            desbloquearCampos(true);
            btnEditar.setEnabled(false);
            btnDeletar.setEnabled(false);

        } else if (evt.getSource() == btnEditar) {
            //editar 
            acao = 1;
            desbloquearCampos(true);
            
            txtCnpjCpf.setEnabled(false);
            cmbTipoPessoa.setEnabled(false);
            btnEditar.setEnabled(false);
            btnDeletar.setEnabled(false);
            

        } else if (evt.getSource() == btnSalvar) {
            //Salvar
            if (validarCampos()) {
                try {
                    FornecedorVO novoForn = new FornecedorVO();

                    novoForn.setCnpjCpf(txtCnpjCpf.getText());
                    novoForn.setTipoPessoa(cmbTipoPessoa.getSelectedIndex());
                    novoForn.setNome(txtNome.getText());
                    novoForn.setFantasia(txtFantasia.getText());
                    novoForn.setInscEstadual(txtInscEstadual.getText());
                    novoForn.setCep(txtCep.getText());
                    novoForn.setEndereco(txtEndereco.getText());
                    novoForn.setUf(cmbUf.getSelectedItem().toString());
                    novoForn.setBairro(txtBairro.getText());
                    novoForn.setCidade(txtCidade.getText());
                    novoForn.setEstado(txtEstado.getText());
                    novoForn.setFone(txtFone.getText());
                    novoForn.setEmail(txtEmail.getText());

                    if (chbAtivo.isSelected()) {
                        novoForn.setStatus(1);
                    } else {
                        novoForn.setStatus(0);
                    }

                    if (acao == 0) {

                        FornecedorDAO.cadastrarFornecedor(novoForn);
                        JOptionPane.showMessageDialog(null, "Fornecedor cadastrado.");

                    } else{
                        
                        FornecedorDAO.alterarFornecedor(novoForn, txtCnpjCpf.getText());
                        JOptionPane.showMessageDialog(null, "Fornecedor Alterado.");

                    }
                    desbloquearCampos(false);
                    limparCampos();
                    carregarDados(0, "");

                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, erro.getMessage());
                }

            }

        } else if (evt.getSource() == btnDeletar) {
            //Deletar
             try{
                String cnpjCpf = txtCnpjCpf.getText();
                
                int verif = JOptionPane.showConfirmDialog(
                            null, "Deseja realmente excluir " + 
                                   txtNome.getText(),
                                   "Exclusão de Dados",
                                    JOptionPane.YES_NO_OPTION);
                
                if(verif == JOptionPane.YES_OPTION){
                    FornecedorDAO.excluirFornecedor(cnpjCpf);
                    JOptionPane.showMessageDialog(null, "Fornecedor excluído");
                    desbloquearCampos(false);
                    limparCampos();
                    carregarDados(0,"");                    
                }
                    
                
            }catch(Exception erro){
                JOptionPane.showMessageDialog(null, erro.getMessage());
            }
            

        } else if (evt.getSource() == btnBuscar) {
            carregarDados((cmbCategoria.getSelectedIndex() + 1), txtBuscar.getText());
        }

    }
}
