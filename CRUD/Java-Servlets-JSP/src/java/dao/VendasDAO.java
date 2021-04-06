package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Venda;

public class VendasDAO {

    public boolean cadastraVenda(Venda produto) {
        String insertTableSQL = "INSERT INTO vendas" + "(data, nomeComprador, cartaoComprador, codSegurancaComprador, valor, idProdutoFk) VALUES" + "(?,?,?,?,?,?);";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = DbConnect.getConexao().prepareStatement(insertTableSQL);
            preparedStatement.setString(1, produto.getData());
            preparedStatement.setString(2, produto.getNomeComprador());
            preparedStatement.setString(3, produto.getCartaoComprador());
            preparedStatement.setString(4, produto.getCodSegurancaComprador());
            preparedStatement.setDouble(5, produto.getValor());
            preparedStatement.setDouble(6, produto.getIdProduto());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alteraVenda(Venda produto) {
        String insertTableSQL = "UPDATE vendas SET data = ?, nomeComprador = ?, cartaoComprador = ?, codSegurancaComprador = ?, valor = ?, idProdutoFk = ?"
                + "WHERE idVenda = ?;";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = DbConnect.getConexao().prepareStatement(insertTableSQL);
            preparedStatement.setString(1, produto.getData());
            preparedStatement.setString(2, produto.getNomeComprador());
            preparedStatement.setString(3, produto.getCartaoComprador());
            preparedStatement.setString(4, produto.getCodSegurancaComprador());
            preparedStatement.setDouble(5, produto.getValor());
            preparedStatement.setDouble(6, produto.getIdProduto());
            preparedStatement.setInt(6, produto.getIdVenda());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluiVenda(Integer toDelete) {
        String insertTableSQL = "DELETE FROM produto WHERE idVenda = ? ; ";
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

    public Venda procuraVendaPeloID(Integer idVenda) {

        try {
            String sql = "select * from produto where idVenda = ? ;";
            PreparedStatement con = DbConnect.getConexao().prepareStatement(sql);

            con.setInt(1, idVenda);
            ResultSet rs = con.executeQuery();
            Venda prod = new Venda();

            if (rs.next()) {
                prod.setIdVenda(rs.getInt("idVenda"));
                prod.setData(rs.getString("data"));
                prod.setNomeComprador(rs.getString("nomeComprador"));
                prod.setCartaoComprador(rs.getString("cartaoComprador"));
                prod.setCodSegurancaComprador(rs.getString("codSegurancaComprador"));
                prod.setValor(rs.getDouble("valor"));
                prod.setIdProduto(rs.getInt("idProdutoFk"));
            }
            rs.close();
            con.close();
            return prod;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Venda> procuraTodosVendas() {

        try {
            String sql = "select * from vendas;";
            PreparedStatement con = DbConnect.getConexao().prepareStatement(sql);

            ResultSet rs = con.executeQuery();

            ArrayList<Venda> listaVendas = new ArrayList<>();
            while (rs.next()) {
                Venda prod = new Venda();

                prod.setIdVenda(rs.getInt("idVenda"));
                prod.setData(rs.getString("data"));
                prod.setNomeComprador(rs.getString("nomeComprador"));
                prod.setCartaoComprador(rs.getString("cartaoComprador"));
                prod.setCodSegurancaComprador(rs.getString("codSegurancaComprador"));
                prod.setValor(rs.getDouble("valor"));
                prod.setIdProduto(rs.getInt("idProdutoFk"));

                listaVendas.add(prod);
            }
            rs.close();
            con.close();
            return listaVendas;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
