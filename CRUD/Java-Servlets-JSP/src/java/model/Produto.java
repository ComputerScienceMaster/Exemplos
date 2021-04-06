package model;

public class Produto {
    
    private Integer codigo;
    private String nome;
    private Double valor;
    private String descricao;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Produto(Integer codigo, String nome, Double valor, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
    }

    public Produto() {
    }

    @Override
    public String toString() {
        return "Produto{" + "codigo=" + codigo + ", nome=" + nome + ", valor=" + valor + ", descricao=" + descricao + '}';
    }
}
