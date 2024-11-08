
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.JOptionPane;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
      conn = new conectaDAO().connectDB();

        String sql = "INSERT INTO produtos (id, nome, valor, status) VALUES (?, ?, ?, ?)";
        
        try {
            prep = conn.prepareStatement(sql);

            // Configura os parâmetros da query
            prep.setInt(1, 0);
            prep.setString(2, produto.getNome());
            prep.setInt(3, produto.getValor());
            prep.setString(4, produto.getStatus());

            // Executa o comando de inserção
            prep.executeUpdate();
            String[] opcoes = {"Fechar"};
            JOptionPane.showOptionDialog(null, "Produto Cadastrado com sucesso!", "Cadastrado!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);
            

        } catch (SQLException e) {
            String[] opcoes = {"Fechar"};
            JOptionPane.showOptionDialog(null, "Erro ao Cadastrar Produto!  " +e.getMessage() , "Erro", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, opcoes, opcoes[0]);
            
        } finally {
            // Fecha a conexão e o statement para evitar leaks
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
               String[] opcoes = {"Fechar"};
            JOptionPane.showOptionDialog(null, "Erro ao Fechar Conexão!  " +e.getMessage() , "Erro", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, opcoes, opcoes[0]);
            
            }
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
        return listagem;
    }
    
}

