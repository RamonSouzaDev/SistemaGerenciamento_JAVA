
import java.sql.*;
import java.util.*;

public class FornecedorDAO {

    public static Statement stFornecedor;
    public static ResultSet rsFornecedor;

    public static void cadastrarFornecedor(FornecedorVO tmpFornecedor) throws Exception {
        try {
            ConexaoDAO.abrirConexao();

            String sqlForn = "INSERT INTO fornecedores(cnpjCpf_FORNECEDOR,tipoPessoa_FORNECEDOR,nome_FORNECEDOR,fantasia_FORNECEDOR,inscEstadual_FORNECEDOR,cep_FORNECEDOR,endereco_FORNECEDOR,uf_FORNECEDOR,bairro_FORNECEDOR,cidade_FORNECEDOR,estado_FORNECEDOR,fone_FORNECEDOR,email_FORNECEDOR,status_FORNECEDOR) ";
            sqlForn += "VALUES(";
            sqlForn += "'" + tmpFornecedor.getCnpjCpf() + "',";
            sqlForn += "" + tmpFornecedor.getTipoPessoa() + ",";// int
            sqlForn += "'" + tmpFornecedor.getNome() + "',";
            sqlForn += "'" + tmpFornecedor.getFantasia() + "',";
            sqlForn += "'" + tmpFornecedor.getInscEstadual() + "',";
            sqlForn += "'" + tmpFornecedor.getCep() + "',";
            sqlForn += "'" + tmpFornecedor.getEndereco() + "',";
            sqlForn += "'" + tmpFornecedor.getUf() + "',";
            sqlForn += "'" + tmpFornecedor.getBairro() + "',";
            sqlForn += "'" + tmpFornecedor.getCidade() + "',";
            sqlForn += "'" + tmpFornecedor.getEstado() + "',";
            sqlForn += "'" + tmpFornecedor.getFone() + "',";
            sqlForn += "'" + tmpFornecedor.getEmail() + "',";
            sqlForn += "" + tmpFornecedor.getStatus() + ")";// int

            stFornecedor = ConexaoDAO.connSistema.createStatement();
            stFornecedor.executeUpdate(sqlForn);

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha no procedimento de cadastro de fornecedor.\n"
                    + "Verifique a sintaxe da instrução Insert e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }
    }

    public static List<FornecedorVO> listarFornecedor(int tmpTipo, String tmpBusca) throws Exception {

        try {
            ConexaoDAO.abrirConexao();

            List<FornecedorVO> lstForn = new ArrayList<FornecedorVO>();

            String sqlLista = "";

            if (tmpTipo == 0) {
                //listar tudo
                sqlLista = "SELECT * FROM fornecedores";

            } else if (tmpTipo == 1) {
                //listar por Cnpj /Cpf
                sqlLista = "SELECT * FROM fornecedores WHERE cnpjCpf_FORNECEDOR LIKE '%" + tmpBusca + "%'";

            } else if (tmpTipo == 2) {
                //listar por nome
                sqlLista = "SELECT * FROM fornecedores WHERE nome_FORNECEDOR LIKE '%" + tmpBusca + "%'";

            } else if (tmpTipo == 3) {
                //listar por nome Fantasia
                sqlLista = "SELECT * FROM fornecedores WHERE fantasia_FORNECEDOR LIKE '" + tmpBusca + "%'";
            } else if (tmpTipo == 4) {
                //listar por Inscriçao Estadual
                sqlLista = "SELECT * FROM fornecedores WHERE inscEstadual_FORNECEDOR LIKE '" + tmpBusca + "%'";
            } else if (tmpTipo == 5) {
                //listar por Cidade
                sqlLista = "SELECT * FROM fornecedores WHERE cidade_FORNECEDOR LIKE '" + tmpBusca + "%'";
            } else if (tmpTipo == 6) {
                //listar por Estado
                sqlLista = "SELECT * FROM fornecedores WHERE estado_FORNECEDOR LIKE '" + tmpBusca + "%'";
            }

            //preparando statement
            stFornecedor = ConexaoDAO.connSistema.createStatement();
            rsFornecedor = stFornecedor.executeQuery(sqlLista);

            while (rsFornecedor.next()) {

                FornecedorVO tmpForn = new FornecedorVO();

                tmpForn.setCnpjCpf(rsFornecedor.getString("cnpjCpf_FORNECEDOR"));
                //tmpForn.setTipoPessoa(rsFornecedor.getInt("tipoPessoa_FORNECEDOR"));
                tmpForn.setNome(rsFornecedor.getString("nome_FORNECEDOR"));
                tmpForn.setFantasia(rsFornecedor.getString("fantasia_FORNECEDOR"));
                tmpForn.setInscEstadual(rsFornecedor.getString("inscEstadual_FORNECEDOR"));
                //tmpForn.setCep(rsFornecedor.getString("cep_FORNECEDOR"));
                //tmpForn.setEndereco(rsFornecedor.getString("endereco_FORNECEDOR"));
                //tmpForn.setUf(rsFornecedor.getString("uf_FORNECEDOR"));
                //tmpForn.setBairro(rsFornecedor.getString("bairro_FORNECEDOR"));
                tmpForn.setCidade(rsFornecedor.getString("cidade_FORNECEDOR"));
                tmpForn.setEstado(rsFornecedor.getString("estado_FORNECEDOR"));
                //tmpForn.setFone(rsFornecedor.getString("fone_FORNECEDOR"));
                //tmpForn.setEmail(rsFornecedor.getString("email_FORNECEDOR"));
                //tmpForn.setStatus(rsFornecedor.getInt("status_FORNECEDOR"));

                lstForn.add(tmpForn);
            }

            ConexaoDAO.fecharConexao();
            return lstForn;

        } catch (Exception erro) {
            String msg = "Falha na listagem dos dados.\n"
                    + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }

    }

    public static FornecedorVO consultarFornecedor(String tmpCnpjCpf) throws Exception {
        try {
            ConexaoDAO.abrirConexao();

            String sqlConsulta = "SELECT * FROM fornecedores WHERE cnpjCpf_FORNECEDOR LIKE '" + tmpCnpjCpf + "'";

            //preparando statement
            stFornecedor = ConexaoDAO.connSistema.createStatement();
            rsFornecedor = stFornecedor.executeQuery(sqlConsulta);

            while (rsFornecedor.next()) {

                FornecedorVO tmpForn = new FornecedorVO();

                tmpForn.setCnpjCpf(rsFornecedor.getString("cnpjCpf_FORNECEDOR"));
                tmpForn.setTipoPessoa(rsFornecedor.getInt("tipoPessoa_FORNECEDOR"));
                tmpForn.setNome(rsFornecedor.getString("nome_FORNECEDOR"));
                tmpForn.setFantasia(rsFornecedor.getString("fantasia_FORNECEDOR"));
                tmpForn.setInscEstadual(rsFornecedor.getString("inscEstadual_FORNECEDOR"));
                tmpForn.setCep(rsFornecedor.getString("cep_FORNECEDOR"));
                tmpForn.setEndereco(rsFornecedor.getString("endereco_FORNECEDOR"));
                tmpForn.setUf(rsFornecedor.getString("uf_FORNECEDOR"));
                tmpForn.setBairro(rsFornecedor.getString("bairro_FORNECEDOR"));
                tmpForn.setCidade(rsFornecedor.getString("cidade_FORNECEDOR"));
                tmpForn.setEstado(rsFornecedor.getString("estado_FORNECEDOR"));
                tmpForn.setFone(rsFornecedor.getString("fone_FORNECEDOR"));
                tmpForn.setEmail(rsFornecedor.getString("email_FORNECEDOR"));
                tmpForn.setStatus(rsFornecedor.getInt("status_FORNECEDOR"));

                ConexaoDAO.fecharConexao();
                return tmpForn;
            }

            ConexaoDAO.fecharConexao();
            return null;

        } catch (Exception erro) {
            String msg = "Falha na consulta do Fornecedor.\n"
                    + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }

    }

    public static void alterarFornecedor(FornecedorVO tmpFornecedor, String tmpCnpjCpf) throws Exception {
        try {
            ConexaoDAO.abrirConexao();

            String sqlAlterar = "UPDATE fornecedores SET ";
            sqlAlterar += "tipoPessoa_FORNECEDOR =" + tmpFornecedor.getTipoPessoa() + ",";
            sqlAlterar += "nome_FORNECEDOR ='" + tmpFornecedor.getNome() + "',";
            sqlAlterar += "fantasia_FORNECEDOR ='" + tmpFornecedor.getFantasia() + "',";
            sqlAlterar += "inscEstadual_FORNECEDOR ='" + tmpFornecedor.getInscEstadual() + "',";
            sqlAlterar += "cep_FORNECEDOR ='" + tmpFornecedor.getCep() + "',";
            sqlAlterar += "endereco_FORNECEDOR ='" + tmpFornecedor.getEndereco() + "',";
            sqlAlterar += "uf_FORNECEDOR ='" + tmpFornecedor.getUf() + "',";
            sqlAlterar += "bairro_FORNECEDOR ='" + tmpFornecedor.getBairro() + "',";
            sqlAlterar += "cidade_FORNECEDOR ='" + tmpFornecedor.getCidade() + "',";
            sqlAlterar += "estado_FORNECEDOR ='" + tmpFornecedor.getEstado() + "',";
            sqlAlterar += "fone_FORNECEDOR ='" + tmpFornecedor.getFone() + "',";
            sqlAlterar += "email_FORNECEDOR ='" + tmpFornecedor.getEmail() + "',";
            sqlAlterar += "status_FORNECEDOR =" + tmpFornecedor.getStatus() + " WHERE ";
            sqlAlterar += "cnpjCpf_FORNECEDOR LIKE '" + tmpCnpjCpf+"'";
            
            stFornecedor= ConexaoDAO.connSistema.createStatement();
            stFornecedor.executeUpdate(sqlAlterar);

            ConexaoDAO.fecharConexao();
        } catch (Exception erro) {
            String msg = "Falha na alteração dos dados.\n"
                    + "Verifique a sintaxe da instrução Update e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);

        }

    }

    public static void excluirFornecedor(String tmpCnpjCpf) throws Exception {
        try {
            ConexaoDAO.abrirConexao();

            String sqlDelete = "DELETE FROM fornecedores WHERE cnpjCpf_FORNECEDOR LIKE '" + tmpCnpjCpf + "'";
            stFornecedor = ConexaoDAO.connSistema.createStatement();
            stFornecedor.executeUpdate(sqlDelete);

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
