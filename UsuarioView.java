
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.util.*;

public class UsuarioView extends JInternalFrame implements ActionListener {

    public static Container ctnUsuario;
    
    public static JLabel lblNome, lblSenha, lblPermissao, lblBusca;
    public static JTextField txtNome, txtBusca;
    public static JPasswordField pwdSenha;
    public static JComboBox cmbPermissao;

    public static JButton btnNovo, btnAlterar, btnExcluir, btnSalvar, btnResetar;

    public static DefaultTableModel mdlUsuarios;
    public static JTable tblUsuarios;
    public static JScrollPane scrUsuario;

    //variaveis auxiliares
    public static String nomeUsuario;
    public static int acao;

    public UsuarioView() {
        super("Gerenciamento de Usuarios");

        ctnUsuario = new Container();
        ctnUsuario.setLayout(null);
        this.add(ctnUsuario);
       
        //********************** construçao tela **********************
        lblNome = new JLabel("Nome: ");
        lblNome.setBounds(30, 75, 150, 20);
        ctnUsuario.add(lblNome);

        lblSenha = new JLabel("Senha: ");
        lblSenha.setBounds(30, 105, 150, 20);
        ctnUsuario.add(lblSenha);

        lblPermissao = new JLabel("Permissão: ");
        lblPermissao.setBounds(30, 135, 150, 20);
        ctnUsuario.add(lblPermissao);

        txtNome = new JTextField();
        txtNome.setBounds(130, 75, 210, 20);
        ctnUsuario.add(txtNome);

        pwdSenha = new JPasswordField();
        pwdSenha.setBounds(130, 105, 210, 20);
        ctnUsuario.add(pwdSenha);

        String opcao[] = {"Usuario", "Administrador"};
        cmbPermissao = new JComboBox(opcao);
        cmbPermissao.setBounds(130, 135, 210, 20);
        ctnUsuario.add(cmbPermissao);

        lblBusca = new JLabel("Busca Rapida: ");
        lblBusca.setBounds(450, 75, 150, 20);
        ctnUsuario.add(lblBusca);

        txtBusca = new JTextField();
        txtBusca.setBounds(600, 75, 200, 20);
        ctnUsuario.add(txtBusca);

        txtBusca.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                carregarDados(1, txtBusca.getText());
            }
        });
        //********************** buttons **********************
        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(this);
        btnNovo.setBounds(30, 185, 150, 40);
        ctnUsuario.add(btnNovo);

        btnAlterar = new JButton("Alterar");
        btnAlterar.addActionListener(this);
        btnAlterar.setEnabled(false);
        btnAlterar.setBounds(190, 185, 150, 40);
        ctnUsuario.add(btnAlterar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(this);
        btnExcluir.setEnabled(false);
        btnExcluir.setBounds(30, 235, 150, 40);
        ctnUsuario.add(btnExcluir);

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(this);
        btnSalvar.setEnabled(false);
        btnSalvar.setBounds(190, 235, 150, 40);
        ctnUsuario.add(btnSalvar);

        btnResetar = new JButton("Resetar senha");
        btnResetar.addActionListener(this);
        btnResetar.setEnabled(false);
        btnResetar.setBounds(30, 285, 150, 40);
        ctnUsuario.add(btnResetar);

        //********************** Table **********************
        tblUsuarios = new JTable();
        scrUsuario = new JScrollPane(tblUsuarios);
        mdlUsuarios = (DefaultTableModel) tblUsuarios.getModel();
        mdlUsuarios.addColumn("Nome");
        mdlUsuarios.addColumn("Permissão");

        tblUsuarios.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                try {
                    String tmpNome = tblUsuarios.getValueAt(tblUsuarios.getSelectedRow(), 0).toString();
                    UsuariosVO tmpUsuario = UsuariosDAO.consultarUsuario(tmpNome);
                    preencherCampos(tmpUsuario);

                } catch (Exception erro) {
                    JOptionPane.showMessageDialog(null, erro.getMessage());
                }
            }
        });

        scrUsuario.setBounds(450, 105, 550, 290);
        ctnUsuario.add(scrUsuario);

        desbloquearCampos(false);
        carregarDados(0, null);

        this.addInternalFrameListener(new InternalFrameAdapter() {
            public void internalFrameClosing(InternalFrameEvent evt) {
                MainView.btnMenu[6].setEnabled(true);
            }
        });

        this.setIconifiable(true);
        this.setClosable(true);
        this.setSize(1100, 500);
        this.show();

    }

    public static boolean validarCampos() {
        if (txtNome.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo Nome");
            txtNome.grabFocus();

            return false;

        } else if (pwdSenha.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Preencha o campo Senha");
            pwdSenha.grabFocus();

            return false;

        }
        return true;

    }

    public static void carregarDados(int tmpTipo, String tmpBusca) {

        try {

            java.util.List<UsuariosVO> lstUsuarios = new ArrayList<UsuariosVO>();

            while (mdlUsuarios.getRowCount() > 0) {
                mdlUsuarios.removeRow(0);
            }

            lstUsuarios = UsuariosDAO.listarUsuarios(tmpTipo, tmpBusca);

            for (UsuariosVO tmpUsuarios : lstUsuarios) {

                String dados[] = new String[2];
                dados[0] = tmpUsuarios.getNome();

                if (tmpUsuarios.getPermissao() == 0) {
                    dados[1] = "Usuario";
                } else if (tmpUsuarios.getPermissao() == 1) {
                    dados[1] = "Administrador";
                }

                mdlUsuarios.addRow(dados);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro);
        }
    }

    public static void preencherCampos(UsuariosVO tmpUsuario) {

        nomeUsuario = tmpUsuario.getNome();

        txtNome.setText(tmpUsuario.getNome());
        pwdSenha.setText(tmpUsuario.getSenha());
        cmbPermissao.setSelectedIndex(tmpUsuario.getPermissao());

        btnAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnResetar.setEnabled(true);
    }

    public static void limparCampos() {

        txtNome.setText(null);
        pwdSenha.setText(null);
        cmbPermissao.setSelectedIndex(0);

    }

    public static boolean verificarNome(String tmpNome) {
        try {
            UsuariosVO tmpUsuario = UsuariosDAO.consultarUsuario(tmpNome);

            if (tmpUsuario != null) {
                JOptionPane.showMessageDialog(null, "Este nome de Usuario ja existe!");
                txtNome.grabFocus();
                return false;
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, erro.getMessage());
        }

        return true;

    }

    public static void desbloquearCampos(boolean tmpStatus) {

        txtNome.setEnabled(tmpStatus);
        pwdSenha.setEnabled(tmpStatus);
        cmbPermissao.setEnabled(tmpStatus);

        btnNovo.setEnabled(!tmpStatus);
        btnExcluir.setEnabled(tmpStatus);
        btnAlterar.setEnabled(tmpStatus);
        btnResetar.setEnabled(tmpStatus);
        btnSalvar.setEnabled(tmpStatus);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnNovo) {
            acao = 0;
            desbloquearCampos(true);

            btnExcluir.setEnabled(false);
            btnAlterar.setEnabled(false);
            btnResetar.setEnabled(false);

            limparCampos();

        } else if (evt.getSource() == btnAlterar) {

            String senha = JOptionPane.showInputDialog("Digite a senha do usuario: " + nomeUsuario);
            if (pwdSenha.getText().equals(senha)) {
                acao = 1;

                desbloquearCampos(true);

                btnExcluir.setEnabled(false);
                btnAlterar.setEnabled(false);
                btnResetar.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "Senha invalida !");
            }

        } else if (evt.getSource() == btnExcluir) {
            try {

                int resp = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir " + nomeUsuario, "Exclusão de Dados", JOptionPane.YES_NO_OPTION);

                if (resp == JOptionPane.YES_OPTION) {

                    UsuariosDAO.excluirUsuario(nomeUsuario);

                    JOptionPane.showMessageDialog(null, "Usuario Excluido");

                }
                limparCampos();
                carregarDados(0, "");
                desbloquearCampos(false);

            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, erro.getMessage());
            }

        } else if (evt.getSource() == btnSalvar) {
            if (validarCampos()) {
                if (verificarNome(txtNome.getText())) {

                    try {
                        UsuariosVO Usuario = new UsuariosVO();

                        Usuario.setNome(txtNome.getText());
                        Usuario.setSenha(pwdSenha.getText());
                        Usuario.setPermissao(cmbPermissao.getSelectedIndex());

                        if (acao == 0) {
                            //cadastrar
                            UsuariosDAO.cadastrarUsuario(Usuario);
                            JOptionPane.showMessageDialog(null, "Novo Usuario Cadastrado");

                        } else {
                            //alterar
                            UsuariosDAO.alterar(Usuario, nomeUsuario);
                            JOptionPane.showMessageDialog(null, "Usuario Alterado");

                        }
                        limparCampos();
                        carregarDados(0, "");
                        desbloquearCampos(false);

                    } catch (Exception erro) {
                        JOptionPane.showMessageDialog(null, erro.getMessage());
                    }
                }//verificar nome

            }//validar

        } else if (evt.getSource() == btnResetar) {
            try {

                int resp = JOptionPane.showConfirmDialog(null, "Deseja realmente Resetar a senha de  " + nomeUsuario, "Resetar senha", JOptionPane.YES_NO_OPTION);

                if (resp == JOptionPane.YES_OPTION) {

                    UsuariosDAO.alterarSenha(nomeUsuario);

                    JOptionPane.showMessageDialog(null, "Sua nova senha é: 000 ");

                    carregarDados(0, "");
                }
                limparCampos();

            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, erro.getMessage());
            }

        }

    }

}
