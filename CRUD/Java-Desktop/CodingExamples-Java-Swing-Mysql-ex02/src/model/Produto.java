package model;

public class Produto {
            
    private int Id;
    private String Descricao;
    private int Qtd;
    private double Preco;       

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public int getQtd() {
        return Qtd;
    }

    public void setQtd(int Qtd) {
        this.Qtd = Qtd;
    }

    public double getPreco() {
        return Preco;
    }

    public void setPreco(double Preco) {
        this.Preco = Preco;
    }

    @Override
    public String toString() {
        return "Produto{" + "Id=" + Id + ", Descricao=" + Descricao + ", Qtd=" + Qtd + ", Preco=" + Preco + '}';
    }
    
    
}
