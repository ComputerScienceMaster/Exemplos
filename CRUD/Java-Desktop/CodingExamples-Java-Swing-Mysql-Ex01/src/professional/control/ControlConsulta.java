/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package professional.control;

import professional.DAO.ConsultaDAO;
import java.util.ArrayList;
import professional.model.Consulta;

/**
 *
 * @author Familia
 */
public class ControlConsulta {
    
        private Consulta ConsultaParaSalvar;

    public void gravarConsulta() {
        //regras de validação...
        ConsultaDAO dao = new ConsultaDAO();
        dao.gravarConsulta(ConsultaParaSalvar);
        //JOptionPane.showMessageDialog(null, "Registro Salvo no Banco de Dados");
    }

    public Consulta getConsultaParaSalvar() {
        return ConsultaParaSalvar;
    }

    public void setConsultaParaSalvar(Consulta pacienteParaSalvar) {
        this.ConsultaParaSalvar = pacienteParaSalvar;
    }

    public ArrayList<Consulta> listarConsultas() {
        ConsultaDAO d1 = new ConsultaDAO();
        return d1.listarConsulta();
    }

    public String excluir(String p) {
        ConsultaDAO d2 = new ConsultaDAO();
        return d2.excluirConsulta(p);
    }

    public boolean validarCampos() {
        boolean validado = false;

//        FrmConsulta a1 = new FrmConsulta();
//        a1.txtConsulta.getText().equals("");
        return validado;

    }
    
}
