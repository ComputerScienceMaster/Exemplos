package professional.model;

public class Consulta {

    private int idConsulta;
    private String data;
    private String horario;
    private float valor;
    private Paciente paciente;

    public Consulta(int idConsulta, String data, String horario, float valor, Paciente paciente) {
        this.idConsulta = idConsulta;
        this.data = data;
        this.horario = horario;
        this.valor = valor;
        this.paciente = paciente;
    }

    public Consulta() {
    }
    
    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

   
    @Override
    public String toString() {
        return String.valueOf(idConsulta);
    }
    
}
