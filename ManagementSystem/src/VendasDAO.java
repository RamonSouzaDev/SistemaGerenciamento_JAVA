import java.sql.*;
import java.util.List;

public class VendasDAO {
    
    public static Statement stVendas;
    public static ResultSet rsVendas;
    
    public static int gerarCodigo() throws Exception{
        int novoID;
        try{            
            ConexaoDAO.abrirConexao();
            
            String sqlCodigo = "Select codigo_VENDA from vendas order by codigo_VENDA desc limit 1";
            stVendas = ConexaoDAO.connSistema.createStatement();
            rsVendas = stVendas.executeQuery(sqlCodigo);
            
            if(!rsVendas.next()){
                novoID = 1;
            }else{
                int ultimoID = rsVendas.getInt("codigo_VENDA");
                novoID = ultimoID + 1;
            }
             
            ConexaoDAO.fecharConexao();
            
        }catch(Exception erro){
              String msg = "Falha ao gerar código da venda.\n"
                    + "Verifique a sintaxe da instrução Select e nomes de campos e tabelas.\n\n"
                    + "Erro Original: " + erro.getMessage();

            throw new Exception(msg); 
        }
        return novoID;
    }

    static void cadastrarVenda(List<ItensVO> itens, VendasVO objVenda) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
