package professional.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import professional.model.Consulta;
import professional.model.Paciente;

public class ConsultaDAO {

    public boolean gravarConsulta(Consulta c) {

      
        try {
            Connection con = DbConnect.getConexao();
            String sql = "insert into consulta (dataConsulta, horarioConsulta, valorConsulta, paciente_idpaciente)";
            sql += "values (?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getData());
            ps.setString(2, c.getHorario());
            ps.setFloat(3, c.getValor());
            ps.setInt(4, c.getPaciente().getIdPaciente());

            ps.execute();
            ps.close();
            con.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Consulta> listarConsulta() {
        
        //MOdificar o Nome no Array List
        ArrayList<Consulta> retorno = new ArrayList<>();

        try {
            Connection con = DbConnect.getConexao();
            String sql = "select * from consulta";
            Statement stmt = (Statement) con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Consulta c1 = new Consulta();
                Paciente add = new Paciente();
                add.setIdPaciente(rs.getInt("paciente_idpaciente"));
                c1.setPaciente(add);
                
                c1.setIdConsulta(rs.getInt("idConsulta"));
                c1.setData(rs.getString("dataConsulta"));
                c1.setHorario(rs.getString("horarioConsulta"));
                c1.setValor(rs.getFloat("valorConsulta"));
                
                retorno.add(c1);
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public String excluirConsulta(String consulta) {

        // Exclui Todos os Pacientes
        String resp = "";

        try {
            Connection con = DbConnect.getConexao();
            Statement stmt = con.createStatement();

            String query = "DELETE FROM consultas where idconsulta = " + consulta;
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
