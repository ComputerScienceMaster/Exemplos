package professional.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import professional.model.Paciente;

public class PacienteDAO {

    public String gravarPaciente(Paciente p) {

        String resp = "";

        try {
            Connection con = DbConnect.getConexao();
            String sql = "insert into Paciente(nomePaciente, dataNascPaciente,sexoPaciente)";
            sql += "values (?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getNomePaciente());
            ps.setString(2, p.getDataNasc());
            ps.setString(3, p.getSexo());

            ps.execute();
            ps.close();
            con.close();

            resp = "OK";
        } catch (Exception e) {
            resp = e.toString();
        }
        return resp;
    }

    public Paciente buscarPacientePorId(int idPaciente) {
        Paciente retorno = new Paciente();

        try {
            Connection con = DbConnect.getConexao();
            String sql = "select * from Paciente where idpaciente = " + idPaciente;
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                Paciente p1 = new Paciente();
                p1.setIdPaciente(rs.getInt("idPaciente"));
                p1.setNomePaciente(rs.getString("nomePaciente"));
                p1.setDataNasc(rs.getString("dataNascPaciente"));
                p1.setSexo(rs.getString("sexoPaciente"));
                return p1;
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<Paciente> listarPacientes() {
        ArrayList<Paciente> retorno = new ArrayList<>();

        try {
            Connection con = DbConnect.getConexao();
            String sql = "select * from Paciente";
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Paciente p1 = new Paciente();
                p1.setIdPaciente(rs.getInt("idPaciente"));
                p1.setNomePaciente(rs.getString("nomePaciente"));
                p1.setDataNasc(rs.getString("dataNascPaciente"));
                p1.setSexo(rs.getString("sexoPaciente"));
                retorno.add(p1);
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public String excluirPaciente(String paciente) {

        // Exclui Todos os Pacientes
        String resp = "";

        try {
            Connection con = DbConnect.getConexao();
            Statement stmt = con.createStatement();

            String query = "DELETE FROM paciente where idPaciente = " + paciente;
            System.err.println(query);

            //fazendo a query
            int valor = stmt.executeUpdate(query);

            if (valor != 0) {
                resp = "Exclusão feita com Sucesso!";
            } else {
                resp = "Não foi possivel excluir";
            }

        } catch (SQLException e) {
            resp = "dados errados";
            e.printStackTrace();
        }

        return resp;
    }

}
