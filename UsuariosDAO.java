
import java.sql.*;
import java.util.*;

public class UsuariosDAO {

    //Statement - executa instruções SQL
    public static Statement stUsuarios;
    //ResultSet - armazena resultado de um select
    public static ResultSet rsUsuarios;

    public static int validarUsuario(String tmpNome, String tmpSenha) throws Exception {
        int permissao = 0;

        try {
            ConexaoDAO.abrirConexao();

            //Execução do Processo de Login
            String sqlLogin;
            sqlLogin = "Select permissao_USUARIO from usuarios where nome_USUARIO like '" + tmpNome + "' and senha_USUARIO like '" + tmpSenha + "'";

            //preparando o statement
            stUsuarios = ConexaoDAO.connSistema.createStatement();
            //executando o select
            rsUsuarios = stUsuarios.executeQuery(sqlLogin);

            if (rsUsuarios.next()) {//se houver registros
                //pegando permissao do select e jogando na variavel permissao
                permissao = rsUsuarios.getInt("permissao_USUARIO");
            }

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha na validação em BD.\n"
                    + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();
            throw new Exception(msg);
        }

        return permissao;
    }//fechando validarUsuario

    public static void cadastrarUsuario(UsuariosVO tmpUsuario) throws Exception {
        try {
            ConexaoDAO.abrirConexao();
            String sqlUsuario = "INSERT INTO usuarios(nome_USUARIO,senha_USUARIO,permissao_USUARIO) VALUES(";
            sqlUsuario += "'" + tmpUsuario.getNome() + "',";
            sqlUsuario += "'" + tmpUsuario.getSenha() + "',";
            sqlUsuario += tmpUsuario.getPermissao() + ")";

            stUsuarios = ConexaoDAO.connSistema.createStatement();

            stUsuarios.executeUpdate(sqlUsuario);

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha no procedimento de cadastro de Usuario.\n"
                    + "Verifique a sintaxe da instrução Insert e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }
    }

    public static List<UsuariosVO> listarUsuarios(int tmpTipo, String tmpBusca) throws Exception {
        try {
            ConexaoDAO.abrirConexao();

            List<UsuariosVO> lstUsuarios = new ArrayList<UsuariosVO>();

            String sqlLista = "";
            if (tmpTipo == 0) {
                sqlLista = "SELECT * FROM usuarios";
            } else if (tmpTipo == 1) {
                sqlLista = "SELECT * FROM usuarios WHERE nome_USUARIO LIKE '%" + tmpBusca + "%'";
            } else if (tmpTipo == 2) {
                sqlLista = "SELECT * FROM usuarios WHERE nome_USUARIO LIKE '" + tmpBusca + "'";
            }
            stUsuarios = ConexaoDAO.connSistema.createStatement();
            rsUsuarios = stUsuarios.executeQuery(sqlLista);
            while (rsUsuarios.next()) {
                UsuariosVO tmpUsuario = new UsuariosVO();

                tmpUsuario.setNome(rsUsuarios.getString("nome_USUARIO"));
                tmpUsuario.setSenha(rsUsuarios.getString("senha_USUARIO"));
                tmpUsuario.setPermissao(rsUsuarios.getInt("permissao_USUARIO"));

                lstUsuarios.add(tmpUsuario);

            }
            ConexaoDAO.fecharConexao();
            return lstUsuarios;

        } catch (Exception erro) {
            String msg = "Falha na listagem dos dados.\n"
                    + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }
    }

    public static UsuariosVO consultarUsuario(String tmpNome) throws Exception {

        try {
            ConexaoDAO.abrirConexao();

            UsuariosVO tmpUsuario = new UsuariosVO();

            String sqlConsulta = "SELECT * FROM usuarios WHERE nome_USUARIO LIKE '" + tmpNome + "'";

            //preparando statement
            stUsuarios = ConexaoDAO.connSistema.createStatement();
            rsUsuarios = stUsuarios.executeQuery(sqlConsulta);

            if (rsUsuarios.next()) {//se houver registros

                tmpUsuario.setNome(rsUsuarios.getString("nome_USUARIO"));
                tmpUsuario.setSenha(rsUsuarios.getString("senha_USUARIO"));
                tmpUsuario.setPermissao(rsUsuarios.getInt("permissao_USUARIO"));

                ConexaoDAO.fecharConexao();
                return tmpUsuario;
            }

            ConexaoDAO.fecharConexao();
            return null;

        } catch (Exception erro) {
            String msg = "Falha na consulta de Usuario.\n"
                    + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }

    }//fechando consultar

    public static void alterarSenha(String tmpNome) throws Exception {
        try {
            ConexaoDAO.abrirConexao();

            String sqlUsuario = "UPDATE usuarios SET ";
            sqlUsuario += "senha_USUARIO ='000'"; 
            sqlUsuario += " WHERE nome_USUARIO LIKE '" + tmpNome + "'";

            stUsuarios = ConexaoDAO.connSistema.createStatement();
            stUsuarios.executeUpdate(sqlUsuario);

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha na alteração dos dados.\n"
                    + "Verifique a sintaxe da instrução Update e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }

    }

    public static void alterar(UsuariosVO tmpUsuario, String tmpNome) throws Exception {
        try {
            ConexaoDAO.abrirConexao();

            String sqlUsuario = "UPDATE usuarios SET ";
            sqlUsuario += "nome_USUARIO ='" + tmpUsuario.getNome() + "',";
            sqlUsuario += "senha_USUARIO ='" + tmpUsuario.getSenha() + "',";
            sqlUsuario += "permissao_USUARIO =" + tmpUsuario.getPermissao();
            sqlUsuario += " WHERE nome_USUARIO LIKE '" + tmpNome + "'";

            stUsuarios = ConexaoDAO.connSistema.createStatement();
            stUsuarios.executeUpdate(sqlUsuario);

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha na alteração dos dados.\n"
                    + "Verifique a sintaxe da instrução Update e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }
    }

    public static void excluirUsuario(String tmpNome) throws Exception {
        try {
            ConexaoDAO.abrirConexao();

            String sqlUsuario = "DELETE FROM usuarios WHERE nome_USUARIO LIKE '" + tmpNome + "'";

            stUsuarios = ConexaoDAO.connSistema.createStatement();
            stUsuarios.executeUpdate(sqlUsuario);

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha na exclusão de dados. "
                    + "Verifique a sintaxe do DELETE e "
                    + "o nome de campos e tabelas.\n\n"
                    + "Erro Original:" + erro.getMessage();

            throw new Exception(msg);

        }
    }

}
