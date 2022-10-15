package br.com.SpringMVC.ExemploDeCRUD.dto;

import br.com.SpringMVC.ExemploDeCRUD.model.Produto;

public class ProdutoRequest {
	private Integer idProduto;
	private String nome;
	private String descricao;
	private double preco;
	private double rating;
	private String url;
	
	

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Produto toProduto() {
		Produto p = new Produto();
		p.setNome(this.nome);
		p.setDescricao(this.descricao);
		p.setPreco(this.preco);
		p.setRating(this.rating);
		p.setFoto(this.url);
		return p;
	}

	@Override
	public String toString() {
		return "ProdutoRequest [idProduto=" + idProduto + ", nome=" + nome + ", descricao=" + descricao + ", preco="
				+ preco + ", rating=" + rating + ", url=" + url + "]";
	}

	
	
	

}
