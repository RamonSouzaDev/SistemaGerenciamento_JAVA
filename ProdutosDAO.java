
import java.sql.*;
import java.util.*; //List

public class ProdutosDAO {

    public static Statement stProdutos; //executa SQL
    public static ResultSet rsProdutos; //armazena result do select

    public static void cadastrarProduto(ProdutosVO tmpProduto) throws Exception {

        try {
            ConexaoDAO.abrirConexao();
            //montagem do insert
            String sqlCadProd = "";
            sqlCadProd += "Insert into produtos(";
            sqlCadProd += "codigo_PRODUTO, nome_PRODUTO,";
            sqlCadProd += "descricao_PRODUTO, quantidadeEstoque_PRODUTO,";
            sqlCadProd += "valorCompra_PRODUTO, valorVenda_PRODUTO,";
            sqlCadProd += "telefone_CLIENTE, email_CLIENTE,";
            sqlCadProd += "foto_CLIENTE, status_CLIENTE)";
            sqlCadProd += "values(";
            sqlCadProd += "'" + tmpProduto.getCodigo_PRODUTO() + "',";
            sqlCadProd += "'" + tmpProduto.getNome_PRODUTO() + "',";
            sqlCadProd += "'" + tmpProduto.getDescricao_PRODUTO() + "',";
            sqlCadProd += "'" + tmpProduto.getQuantidadeEstoque_PRODUTO() + "',";
            sqlCadProd += "'" + tmpProduto.getValorCompra_PRODUTO() + "',";
            sqlCadProd += "'" + tmpProduto.getValorVenda_PRODUTO() + "')";
            //sqlCadProd += "'" + tmpProduto.getFoto_PRODUTO() + "')";

            //preparando statement para execução do INSERT
            stProdutos = ConexaoDAO.connSistema.createStatement();

            //execução do insert
            stProdutos.executeUpdate(sqlCadProd);

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha no procedimento de cadastro de produto.\n"
                    + "Verifique a sintaxe da instrução Insert e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);
        }
    }//fechando cadastrarCliente

    public static ProdutosVO consultarProduto(Integer tmpCodigo) throws Exception {

        try {
            ConexaoDAO.abrirConexao();

            ProdutosVO tmpProduto = new ProdutosVO();

            String sqlConsulta = "Select * from produtos where codigo_PRODUTO like '" + tmpCodigo + "'";

            //preparando statement
            stProdutos = ConexaoDAO.connSistema.createStatement();
            rsProdutos = stProdutos.executeQuery(sqlConsulta);

            if (rsProdutos.next()) {//se houver registros

                tmpProduto.setCodigo_PRODUTO(rsProdutos.getInt("codigo_PRODUTO"));
                tmpProduto.setNome_PRODUTO(rsProdutos.getString("nome_PRODUTO"));
                tmpProduto.setDescricao_PRODUTO(rsProdutos.getString("descricao_PRODUTO"));
                tmpProduto.setQuantidadeEstoque_PRODUTO(rsProdutos.getInt("quantidadeEstoque_PRODUTO"));
                tmpProduto.setValorCompra_PRODUTO(rsProdutos.getInt("valorCompra_PRODUTO"));
                tmpProduto.setValorVenda_PRODUTO(rsProdutos.getInt("valorVenda_PRODUTO"));
                //tmpProduto.setStatus(rsProdutos.getInt("status_PRODUTO"));

                ConexaoDAO.fecharConexao();
                return tmpProduto;
            }

            ConexaoDAO.fecharConexao();
            return null; // saida 1 - return            

        } catch (Exception erro) {
            String msg = "Falha na consulta do Produto.\n"
                    + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg); //saida 2
        }

    }//fechando consultar

    public static List<ProdutosVO> listarProdutos(int tmpTipo, String tmpBusca) throws Exception {

        try {
            ConexaoDAO.abrirConexao();

            List<ProdutosVO> lstProdutos = new ArrayList<ProdutosVO>();

            String sqlLista = "";

            if (tmpTipo == 0) {
                sqlLista = "Select * from produtos";

            } else if (tmpTipo == 1) {
                sqlLista = "Select * from produtos where valorVenda_PRODUTO like '%" + tmpBusca + "%'";

            } else if (tmpTipo == 2) {
                sqlLista = "Select * from produtos where nome_PRODUTO like '%" + tmpBusca + "%'";

            } else if (tmpTipo == 3) {
                sqlLista = "Select * from produtos where nome_PRODUTO like '" + tmpBusca + "%'";
            }

            //preparando statement
            stProdutos = ConexaoDAO.connSistema.createStatement();
            rsProdutos = stProdutos.executeQuery(sqlLista);

            while (rsProdutos.next()) {

                ProdutosVO tmpProduto = new ProdutosVO();//instanciando obj Cliente

                tmpProduto.setCodigo_PRODUTO(rsProdutos.getInt("codigo_PRODUTO"));
                tmpProduto.setNome_PRODUTO(rsProdutos.getString("nome_PRODUTO"));
                tmpProduto.setDescricao_PRODUTO(rsProdutos.getString("descricao_PRODUTO"));
                tmpProduto.setQuantidadeEstoque_PRODUTO(rsProdutos.getInt("quantidadeEstoque_PRODUTO"));
                tmpProduto.setValorCompra_PRODUTO(rsProdutos.getInt("valorCompra_PRODUTO"));
                tmpProduto.setValorVenda_PRODUTO(rsProdutos.getInt("valorVenda_PRODUTO"));
                tmpProduto.setFoto_PRODUTO(rsProdutos.getInt("foto_PRODUTO"));
                //tmpProduto.setStatus(rsProdutos.getInt("status_CLIENTE"));

                lstProdutos.add(tmpProduto);
            }

            ConexaoDAO.fecharConexao();
            return lstProdutos; // saida 1 - return            

        } catch (Exception erro) {
            String msg = "Falha na listagem dos dados.\n"
                    + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg); //saida 2
        }

    }//fechando listarClientes - saida 3

    public static void alterarProduto(ProdutosVO tmpProduto, String tmpCodigo) throws Exception {

        try {
            ConexaoDAO.abrirConexao();

            String sqlAltera = "";
            sqlAltera += "Update produtos set ";
            sqlAltera += "nome_PRODUTO = '" + tmpProduto.getNome_PRODUTO() + "',";
            sqlAltera += "descricao_PRODUTO = '" + tmpProduto.getDescricao_PRODUTO() + "',";
            sqlAltera += "quantidadeEstoque_PRODUTO = '" + tmpProduto.getQuantidadeEstoque_PRODUTO() + "',";
            sqlAltera += "valorCompra_PRODUTO = '" + tmpProduto.getValorCompra_PRODUTO() + "',";
            sqlAltera += "valorVenda_PRODUTO = '" + tmpProduto.getValorVenda_PRODUTO() + "'," + "' where ";
            sqlAltera += "codigo_PRODUTO like '" + tmpCodigo + "'";

            stProdutos = ConexaoDAO.connSistema.createStatement();
            stProdutos.executeUpdate(sqlAltera);

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha na alteração dos dados.\n"
                    + "Verifique a sintaxe da instrução Update e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg);

        }

    }

    public static void alterarStatus(String tmpCodigo, int tmpStatus) throws Exception {
        try {

            ConexaoDAO.abrirConexao();

            int novoStatus = 0;

            if (tmpStatus == 0) {
                novoStatus = 1;
            } else if (tmpStatus == 1) {
                novoStatus = 0;
            }

            String sqlStatus = "Update produtos set status_CLIENTE = " + novoStatus + " where codigo_PRODUTO like '" + tmpCodigo + "'";
            stProdutos = ConexaoDAO.connSistema.createStatement();

            //executando SQL
            stProdutos.executeUpdate(sqlStatus);

            ConexaoDAO.fecharConexao();

        } catch (Exception erro) {
            String msg = "Falha na alteração do status. "
                    + "Verifique a sintaxe do comando Update. "
                    + erro.getMessage();

            throw new Exception(msg);
        }
    }

    public static void excluirProduto(String tmpCodigo) throws Exception {
        try{
            ConexaoDAO.abrirConexao();
            
            String sqlExc = "Delete from produtos where codigo_PRODUTO like '" + tmpCodigo + "'";
            stProdutos = ConexaoDAO.connSistema.createStatement();
            stProdutos.executeUpdate(sqlExc);            
            
            ConexaoDAO.fecharConexao();
            
        }catch(Exception erro){
            String msg = "Falha na exclusão de dados. "
                    + "Verifique a sintaxe do DELETE e "
                    + "o nome de campos e tabelas.\n\n"
                    + "Erro Original:" + erro.getMessage();
            
            throw new Exception(msg);
                    
        }
    }

    static ProdutosVO consultarProduto(String codigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
