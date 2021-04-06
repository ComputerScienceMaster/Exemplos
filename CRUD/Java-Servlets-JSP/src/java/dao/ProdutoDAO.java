package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Produto;

public class ProdutoDAO {

    public boolean cadastraProduto(Produto produto) {
        String insertTableSQL = "INSERT INTO produto" + "(nome, valor, descricao) VALUES" + "(?,?,?) ;";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = DbConnect.getConexao().prepareStatement(insertTableSQL);
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setDouble(2, produto.getValor());
            preparedStatement.setString(3, produto.getDescricao());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alteraProduto(Produto produto) {
        String insertTableSQL = "UPDATE produto SET nome = ?, valor = ?, descricao= ?"
                + "WHERE idProduto = ? ;";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = DbConnect.getConexao().prepareStatement(insertTableSQL);
            preparedStatement.setString(1, produto.getNome());
            preparedStatement.setDouble(2, produto.getValor());
            preparedStatement.setString(3, produto.getDescricao());
            preparedStatement.setInt(4, produto.getCodigo());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluiProduto(Integer toDelete) {
        String insertTableSQL = "DELETE FROM produto WHERE idProduto = ? ; ";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = DbConnect.getConexao().prepareStatement(insertTableSQL);
            preparedStatement.setInt(1, toDelete);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Produto procuraProdutoPeloID(Integer idProduto) {

        try {
            String sql = "select * from produto where idProduto = ? ;";
            PreparedStatement con = DbConnect.getConexao().prepareStatement(sql);

            con.setInt(1, idProduto);
            ResultSet rs = con.executeQuery();
            Produto prod = new Produto();

            if (rs.next()) {
                prod.setCodigo(rs.getInt("idProduto"));
                prod.setNome(rs.getString("nome"));
                prod.setValor(rs.getDouble("valor"));
                prod.setDescricao(rs.getString("descricao"));

            }
            rs.close();
            con.close();
            return prod;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Produto> procuraTodosProdutos() {

        try {
            String sql = "select * from produto;";
            PreparedStatement con = DbConnect.getConexao().prepareStatement(sql);

            ResultSet rs = con.executeQuery();

            ArrayList<Produto> listaProdutos = new ArrayList<>();
            while(rs.next()) {
                Produto prod = new Produto();

                prod.setCodigo(rs.getInt("idProduto"));
                prod.setNome(rs.getString("nome"));
                prod.setValor(rs.getDouble("valor"));
                prod.setDescricao(rs.getString("descricao"));

                listaProdutos.add(prod);
            }
            rs.close();
            con.close();
            return listaProdutos;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
