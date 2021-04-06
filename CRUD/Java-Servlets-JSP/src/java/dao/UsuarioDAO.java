
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;

public class UsuarioDAO {
    
    public boolean cadastraUsuario(Usuario usuario) {
        String insertTableSQL = "INSERT INTO usuario" + "(login, senha, nome, cpf, telefone, endereco ) VALUES" + "(?,?,?,?,?,?) ;";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = DbConnect.getConexao().prepareStatement(insertTableSQL);
            preparedStatement.setString(1, usuario.getLogin());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setString(3, usuario.getNome());
            preparedStatement.setString(4, usuario.getCpf());
            preparedStatement.setString(5, usuario.getTelefone());
            preparedStatement.setString(6, usuario.getEndereco());
            

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alteraUsuario(Usuario usuario) {
        String insertTableSQL = "UPDATE usuario SET senha = ?, nome = ?, cpf = ?, telefone = ?, endereco = ? "
                + "WHERE login = ? ;";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = DbConnect.getConexao().prepareStatement(insertTableSQL);
            preparedStatement.setString(7, usuario.getLogin());
            preparedStatement.setString(1, usuario.getSenha());
            preparedStatement.setString(2, usuario.getNome());
            preparedStatement.setString(3, usuario.getCpf());
            preparedStatement.setString(4, usuario.getTelefone());
            preparedStatement.setString(6, usuario.getEndereco());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluiUsuario(String toDelete) {
        String insertTableSQL = "DELETE FROM usuario WHERE login = ? ; ";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = DbConnect.getConexao().prepareStatement(insertTableSQL);
            preparedStatement.setString(1, toDelete);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario procuraUsuarioPeloID(String login) {

        try {
            String sql = "select * from usuario where login = ? ;";
            PreparedStatement con = DbConnect.getConexao().prepareStatement(sql);

            con.setString(1, login);
            ResultSet rs = con.executeQuery();
            Usuario user = new Usuario();

            if (rs.next()) {

                user.setCpf(rs.getString("cpf"));
                user.setEndereco(rs.getString("endereco"));
                user.setLogin(rs.getString("login"));
                user.setNome(rs.getString("nome"));
                user.setSenha(rs.getString("senha"));
                user.setTelefone(rs.getString("telefone"));

            }
            rs.close();
            con.close();
            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validar(Usuario u){
        try {
            //con: referência para a conexão com o banco
            Connection con = DbConnect.getConexao();
            String sql = "select * from usuario where login = ? and senha = ?";
            
            //executar o sql
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getSenha());
            
            //representa o resultado do SQL
            ResultSet rs = ps.executeQuery();
            if(rs.first()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
