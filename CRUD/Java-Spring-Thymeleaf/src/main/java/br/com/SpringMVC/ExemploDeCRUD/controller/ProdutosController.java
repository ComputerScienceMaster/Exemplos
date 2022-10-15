package br.com.SpringMVC.ExemploDeCRUD.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.SpringMVC.ExemploDeCRUD.dto.ProdutoRequest;
import br.com.SpringMVC.ExemploDeCRUD.model.Produto;
import br.com.SpringMVC.ExemploDeCRUD.repository.ProdutoRepository;

@Controller
@RequestMapping("produtos")
public class ProdutosController {
	
	@Autowired
	ProdutoRepository pR;

	@GetMapping("gerenciar")
	public ModelAndView gerenciar() {
		List<Produto> products = pR.findAll();
		ModelAndView mv = new ModelAndView("/produtos/gerenciar");
		mv.addObject("produtos", products);
		return mv;
	}
	
	@GetMapping("novo")
	public String novo() {
		return "produtos/novo";
	}
	
	@PostMapping("novo")
	public String salvarProduto( ProdutoRequest request) {
		pR.save(request.toProduto());
		return "produtos/novo";
	}
	
	@GetMapping("excluir/{id}")
	public String excluir(@PathVariable("id") String param) {
		pR.deleteById(Integer.parseInt(param));
		return "redirect:/home";
	}
	
	@GetMapping("alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") String param) {
		ModelAndView mv = new ModelAndView("/produtos/alterar");
		Optional<Produto> produto = pR.findById(Integer.parseInt(param));
		System.out.println(produto);
		mv.addObject("produto", produto.get());
		return mv;
	}
	
	@PostMapping("alterar")
	public String alterar(ProdutoRequest rq) {
		System.out.println(rq);
		Produto produto = pR.findById(rq.getIdProduto()).get();
		produto.setNome(rq.getNome());
		produto.setDescricao(rq.getDescricao());
		produto.setPreco(rq.getPreco());
		produto.setRating(rq.getRating());
		produto.setFoto(rq.getUrl());		
		pR.save(produto);
		return "redirect:/home";
	}
	
	
	
}
