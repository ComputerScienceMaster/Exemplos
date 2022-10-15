package br.com.SpringMVC.ExemploDeCRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SobreController {
	
	@GetMapping("/sobre")
	public String home(Model model) {
		
		return "sobre";
	}
}
