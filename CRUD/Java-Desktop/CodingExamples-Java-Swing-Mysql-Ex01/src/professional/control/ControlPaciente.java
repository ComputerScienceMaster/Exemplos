package professional.control;

import professional.DAO.PacienteDAO;
import java.util.ArrayList;
import professional.model.Paciente;

public class ControlPaciente {

    private Paciente pacienteParaSalvar;

    public Paciente buscarPacientePorId(int idPaciente){
        PacienteDAO pDao = new PacienteDAO();
        return pDao.buscarPacientePorId(idPaciente);
    }
    
    public void gravarPaciente() {
        //regras de validação...
        PacienteDAO dao = new PacienteDAO();
        dao.gravarPaciente(pacienteParaSalvar);
        //JOptionPane.showMessageDialog(null, "Registro Salvo no Banco de Dados");
    }

    public String excluir(String p) {
        PacienteDAO d2 = new PacienteDAO();
        return d2.excluirPaciente(p);
    }

    public boolean validarCampos() {
        boolean validado = false;

//        FrmPaciente a1 = new FrmPaciente();
//        a1.txtPaciente.getText().equals("");
        return validado;

    }

    public ArrayList<Paciente> listarPacientes() {
        PacienteDAO d1 = new PacienteDAO();
        return d1.listarPacientes();

    }

    public Paciente getPacienteParaSalvar() {
        return pacienteParaSalvar;
    }

    public void setPacienteParaSalvar(Paciente pacienteParaSalvar) {
        this.pacienteParaSalvar = pacienteParaSalvar;
    }

}
