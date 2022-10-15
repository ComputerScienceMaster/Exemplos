package br.com.SpringMVC.ExemploDeCRUD.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.SpringMVC.ExemploDeCRUD.model.Produto;
import br.com.SpringMVC.ExemploDeCRUD.repository.ProdutoRepository;

@Controller
public class HomeController {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@GetMapping("/home")
	public String home(Model model) {
		
		List<Produto> products = produtoRepository.findAll();
		model.addAttribute("produtos", products);
		return "home";
	}
}
