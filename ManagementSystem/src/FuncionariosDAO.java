import java.sql.*;
import java.util.*; //List

public class FuncionariosDAO {

    public static Statement stFuncionarios; //executa SQL
    public static ResultSet rsFuncionarios; //armazena result do select

    public static void cadastrarFuncionario(FuncionariosVO tmpFuncionario) throws Exception {

        try {
            ConexaoDAO.abrirConexao();
            //montagem do insert
            String sqlCadFun = "";
            sqlCadFun += "Insert into funcionarios(";
            sqlCadFun += "cpf_FUNCIONARIO, nome_FUNCIONARIO,";
            sqlCadFun += "dataNascimento_FUNCIONARIO, endereco_FUNCIONARIO,";
            sqlCadFun += "bairro_FUNCIONARIO, cidade_FUNCIONARIO,";
            sqlCadFun += "telefone_FUNCIONARIO, email_FUNCIONARIO,";
            sqlCadFun += "naturalidade_FUNCIONARIO, pis_FUNCIONARIO,";
            sqlCadFun += "pai_FUNCIONARIO, mae_FUNCIONARIO,";
            sqlCadFun += "cargo_FUNCIONARIO, periodo_FUNCIONARIO,";
            sqlCadFun += "salario_FUNCIONARIO,";
            sqlCadFun += "banco_FUNCIONARIO,";
            sqlCadFun += "foto_FUNCIONARIO, status_FUNCIONARIO)";
            sqlCadFun += "values(";
            sqlCadFun += "'" + tmpFuncionario.getCpf() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getNome() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getDataNascimento() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getEndereco() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getBairro() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getCidade() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getTelefone() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getEmail() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getNaturalidade() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getNumeroPIS() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getPai() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getMae() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getCargo() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getPeriodo() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getSalario() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getBanco() + "', ";
            sqlCadFun += "'" + tmpFuncionario.getFoto() + "', 1)";
            

            //preparando statement para execução do INSERT
            stFuncionarios = ConexaoDAO.connSistema.createStatement();

            //execução do insert
            stFuncionarios.executeUpdate(sqlCadFun);

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha no procedimento de cadastro de funcionario.\n"
                    + "Verifique a sintaxe da instrução Insert e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }
    }//fechando cadastrarCliente

    public static FuncionariosVO consultarFuncionario(String tmpCpf) throws Exception {

        try {
            ConexaoDAO.abrirConexao();

            FuncionariosVO tmpFuncionario = new FuncionariosVO();

            String sqlConsulta = "Select * from funcionarios where cpf_FUNCIONARIO like '" + tmpCpf + "'";

            //preparando statement
            stFuncionarios = ConexaoDAO.connSistema.createStatement();
            rsFuncionarios = stFuncionarios.executeQuery(sqlConsulta);

            if (rsFuncionarios.next()) {//se houver registros

                tmpFuncionario.setCpf(rsFuncionarios.getString("cpf_FUNCIONARIO"));
                tmpFuncionario.setNome(rsFuncionarios.getString("nome_FUNCIONARIO"));
                tmpFuncionario.setDataNascimento(rsFuncionarios.getString("dataNascimento_FUNCIONARIO"));
                tmpFuncionario.setEndereco(rsFuncionarios.getString("endereco_FUNCIONARIO"));
                tmpFuncionario.setBairro(rsFuncionarios.getString("bairro_FUNCIONARIO"));
                tmpFuncionario.setCidade(rsFuncionarios.getString("cidade_FUNCIONARIO"));
                tmpFuncionario.setTelefone(rsFuncionarios.getString("telefone_FUNCIONARIO"));
                tmpFuncionario.setEmail(rsFuncionarios.getString("email_FUNCIONARIO"));
                tmpFuncionario.setNaturalidade(rsFuncionarios.getString("naturalidade_FUNCIONARIO"));
                tmpFuncionario.setNumeroPIS(rsFuncionarios.getString("pis_FUNCIONARIO"));
                tmpFuncionario.setPai(rsFuncionarios.getString("pai_FUNCIONARIO"));
                tmpFuncionario.setMae(rsFuncionarios.getString("mae_FUNCIONARIO"));
                tmpFuncionario.setCargo(rsFuncionarios.getString("cargo_FUNCIONARIO"));
                tmpFuncionario.setPeriodo(rsFuncionarios.getString("periodo_FUNCIONARIO"));
                tmpFuncionario.setSalario(rsFuncionarios.getString("salario_FUNCIONARIO"));
                tmpFuncionario.setBanco(rsFuncionarios.getString("banco_FUNCIONARIO"));
                tmpFuncionario.setFoto(rsFuncionarios.getString("foto_FUNCIONARIO"));
                tmpFuncionario.setStatus(rsFuncionarios.getInt("status_FUNCIONARIO"));

            

                ConexaoDAO.fecharConexao();
                return tmpFuncionario;
            }

            ConexaoDAO.fecharConexao();
            return null; // saida 1 - return            

        } catch (Exception erro) {
            String msg = "Falha na consulta do Funcionario.\n"
                    + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg); //saida 2
        }

    }//fechando consultar

    public static List<FuncionariosVO> listarFuncionarios(int tmpTipo, String tmpBusca) throws Exception {

        try {
            ConexaoDAO.abrirConexao();

            List<FuncionariosVO> lstFuncionarios = new ArrayList<FuncionariosVO>();

            String sqlLista = "";

            if (tmpTipo == 0) {
                sqlLista = "Select * from funcionarios";

            } else if (tmpTipo == 1) {
                sqlLista = "Select * from funcionarios where cidade_FUNCIONARIO like '%" + tmpBusca + "%'";

            } else if (tmpTipo == 2) {
                sqlLista = "Select * from funcionarios where nome_FUNCIONARIO like '%" + tmpBusca + "%'";

            } else if (tmpTipo == 3) {
                sqlLista = "Select * from funcionarios where nome_FUNCIONARIO like '" + tmpBusca + "%'";
            }

            //preparando statement
            stFuncionarios = ConexaoDAO.connSistema.createStatement();
            rsFuncionarios = stFuncionarios.executeQuery(sqlLista);

            while (rsFuncionarios.next()) {

                FuncionariosVO tmpFuncionario = new FuncionariosVO();//instanciando obj Funcionario
                

                tmpFuncionario.setCpf(rsFuncionarios.getString("cpf_FUNCIONARIO"));
                tmpFuncionario.setNome(rsFuncionarios.getString("nome_FUNCIONARIO"));
                tmpFuncionario.setDataNascimento(rsFuncionarios.getString("dataNascimento_FUNCIONARIO"));
                tmpFuncionario.setEndereco(rsFuncionarios.getString("endereco_FUNCIONARIO"));
                tmpFuncionario.setBairro(rsFuncionarios.getString("bairro_FUNCIONARIO"));
                tmpFuncionario.setCidade(rsFuncionarios.getString("cidade_FUNCIONARIO"));
                tmpFuncionario.setTelefone(rsFuncionarios.getString("telefone_FUNCIONARIO"));
                tmpFuncionario.setEmail(rsFuncionarios.getString("email_FUNCIONARIO"));
                tmpFuncionario.setPai(rsFuncionarios.getString("pai_FUNCIONARIO"));
                tmpFuncionario.setMae(rsFuncionarios.getString("mae_FUNCIONARIO"));
                tmpFuncionario.setCargo(rsFuncionarios.getString("cargo_FUNCIONARIO"));
                tmpFuncionario.setPeriodo(rsFuncionarios.getString("periodo_FUNCIONARIO"));
                tmpFuncionario.setNumeroPIS(rsFuncionarios.getString("pis_FUNCIONARIO"));
                tmpFuncionario.setBanco(rsFuncionarios.getString("banco_FUNCIONARIO"));
                tmpFuncionario.setNaturalidade(rsFuncionarios.getString("naturalidade_FUNCIONARIO"));
                tmpFuncionario.setFoto(rsFuncionarios.getString("foto_FUNCIONARIO"));
                //tmpFuncionario.setStatus(rsFuncionarios.getInt("status_FUNCIONARIO"));

                lstFuncionarios.add(tmpFuncionario);
            }

            ConexaoDAO.fecharConexao();
            return lstFuncionarios; // saida 1 - return            

        } catch (Exception erro) {
            String msg = "Falha na listagem dos dados.\n"
                    + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg); //saida 2
        }

    }//fechando listarFuncionarios - saida 3

    public static void alterarFuncionario(FuncionariosVO tmpFuncionario, String tmpCPF) throws Exception {

        try {
            ConexaoDAO.abrirConexao();

            String sqlAltera = "";
            sqlAltera += "Update funcionarios set ";
            sqlAltera += "nome_FUNCIONARIO = '" + tmpFuncionario.getNome() + "',";
            sqlAltera += "dataNascimento_FUNCIONARIO = '" + tmpFuncionario.getDataNascimento() + "',";
            sqlAltera += "endereco_FUNCIONARIO = '" + tmpFuncionario.getEndereco() + "',";
            sqlAltera += "bairro_FUNCIONARIO = '" + tmpFuncionario.getBairro() + "',";
            sqlAltera += "cidade_FUNCIONARIO = '" + tmpFuncionario.getCidade() + "',";
            sqlAltera += "telefone_FUNCIONARIO = '" + tmpFuncionario.getTelefone() + "',";
            sqlAltera += "pai_FUNCIONARIO = '" + tmpFuncionario.getPai() + "',";
            sqlAltera += "mae_FUNCIONARIO = '" + tmpFuncionario.getMae() + "',";
            sqlAltera += "pis_FUNCIONARIO = '" + tmpFuncionario.getNumeroPIS() + "',";
            sqlAltera += "salario_FUNCIONARIO = '" + tmpFuncionario.getSalario() + "',";
            sqlAltera += "cargo_FUNCIONARIO = '" + tmpFuncionario.getCargo() + "',";
            sqlAltera += "periodo_FUNCIONARIO = '" + tmpFuncionario.getPeriodo() + "',";
            sqlAltera += "banco_FUNCIONARIO = '" + tmpFuncionario.getBanco() + "',";
            sqlAltera += "naturalidade_FUNCIONARIO = '" + tmpFuncionario.getNaturalidade() + "',";
            sqlAltera += "email_FUNCIONARIO = '" + tmpFuncionario.getEmail() + "' where ";
            sqlAltera += "cpf_FUNCIONARIO like '" + tmpCPF + "'";

            stFuncionarios = ConexaoDAO.connSistema.createStatement();
            stFuncionarios.executeUpdate(sqlAltera);

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha na alteração dos dados.\n"
                    + "Verifique a sintaxe da instrução Update e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);

        }

    }

    public static void alterarStatus(String tmpCpf, int tmpStatus) throws Exception {
        try {

            ConexaoDAO.abrirConexao();

            int novoStatus = 0;

            if (tmpStatus == 0) {
                novoStatus = 1;
            } else if (tmpStatus == 1) {
                novoStatus = 0;
            }

            String sqlStatus = "Update funcionarios set status_CLIENTE = " + novoStatus + " where cpf_FUNCIONARIO like '" + tmpCpf + "'";
            stFuncionarios = ConexaoDAO.connSistema.createStatement();

            //executando SQL
            stFuncionarios.executeUpdate(sqlStatus);

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha na alteração do status. "
                    + "Verifique a sintaxe do comando Update. "
                    + erro.getMessage();

            throw new Exception(msg);
        }
    }

    public static void excluirFuncionario(String tmpCpf) throws Exception {
        try{
            ConexaoDAO.abrirConexao();
            
            String sqlExc = "Delete from funcionarios where cpf_FUNCIONARIO like '" + tmpCpf + "'";
            stFuncionarios = ConexaoDAO.connSistema.createStatement();
            stFuncionarios.executeUpdate(sqlExc);            
            
            ConexaoDAO.fecharConexao();
            
        }catch(Exception erro){
            String msg = "Falha na exclusão de dados. "
                    + "Verifique a sintaxe do DELETE e "
                    + "o nome de campos e tabelas.\n\n"
                    + "Erro Original:" + erro.getMessage();
            
            throw new Exception(msg);
                    
        }
    }

}
