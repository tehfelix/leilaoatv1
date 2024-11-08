
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
    
      public ProdutosDAO() {
        // Código para inicializar a conexão com o banco
        this.conn = new conectaDAO().connectDB();
    }
    
    
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
    
  public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> lista = new ArrayList<>();
        String sql = "SELECT id, nome, valor, status FROM produtos";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));
                
                lista.add(produto);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }

        return lista;
    }
    
}

