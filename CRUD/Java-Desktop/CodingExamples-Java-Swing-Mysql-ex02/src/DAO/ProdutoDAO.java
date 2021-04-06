package DAO;

import Conexao.ConexaoSql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Produto;

public class ProdutoDAO {

    private ConexaoSql conn;

    public ProdutoDAO() {
        try {
            this.conn = new ConexaoSql();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean inserir(Produto prod) {
        try {

            String sql = "INSERT INTO produtos (descricao, qtd, preco)VALUES (?, ?, ?)";
            PreparedStatement ps = this.conn.getConexao().prepareStatement(sql);

            ps.setString(1, prod.getDescricao());
            ps.setInt(2, prod.getQtd());
            ps.setDouble(3, prod.getPreco());

            ps.execute();

            this.conn.confirmar();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean alterar(Produto prod) {
        try {

            String sql = "UPDATE produtos set descricao=?, qtd=?, preco=? WHERE id = ?";
           PreparedStatement ps = this.conn.getConexao().prepareStatement(sql);

            ps.setString(1, prod.getDescricao());
            ps.setInt(2, prod.getQtd());
            ps.setDouble(3, prod.getPreco());
            ps.setInt(4, prod.getId());
            
            ps.executeUpdate();

            this.conn.confirmar();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Produto buscarPorId(Integer id) {
        try {
            String sql = "SELECT * from produtos WHERE id = " + id;
            PreparedStatement ps = this.conn.getConexao().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            Produto p = new Produto();

            while (rs.next()) {
                p.setDescricao(rs.getString("descricao"));
                p.setQtd(Integer.parseInt(rs.getString("qtd")));
                p.setPreco(Integer.parseInt(rs.getString("preco")));
            }
            return p;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<Produto> buscarTodos() {
        try {
            String sql = "SELECT * from produtos";
            PreparedStatement ps = this.conn.getConexao().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            
            ArrayList<Produto> arrayProduto = new ArrayList<>();
            

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setQtd(Integer.parseInt(rs.getString("qtd")));
                p.setPreco(rs.getInt("preco"));
                arrayProduto.add(p);
            }
            return arrayProduto;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean excluir(int idProdutoExcluir) {
        try {
            String sql = "DELETE from produtos WHERE id = '" + idProdutoExcluir + "'";
            System.out.println(sql);
            PreparedStatement ps = this.conn.getConexao().prepareStatement(sql);
            ps.executeUpdate();
            this.conn.confirmar();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
