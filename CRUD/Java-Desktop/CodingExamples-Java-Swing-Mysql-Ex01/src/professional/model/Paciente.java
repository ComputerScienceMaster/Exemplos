package professional.model;

public class Paciente {

    private int idPaciente;
    private String nomePaciente;
    private String dataNasc;
    private String sexo;

    public Paciente(String label) {

    }

    public Paciente(int codPaciente, String nomePaciente, String dataNasc, String sexo) {
        this.idPaciente = codPaciente;
        this.nomePaciente = nomePaciente;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
    }

    public Paciente() {
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return nomePaciente;
    }

}
