
package Controller;

import DAO.PersonDAO;
import java.util.List;
import models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonController {
    
    @Autowired
    private PersonDAO controlPerson;
    
    @RequestMapping(value = "/pessoa/form", method = RequestMethod.GET)
    public String addPersonForm(){
        return "addPerson";
    }
    
    @RequestMapping(value = "/pessoa/criar", method = RequestMethod.POST)
    public String addPersonCreate(Person p){
        System.out.println(p);
        controlPerson.create(p);
        return "redirect:list";
    }
    
    @RequestMapping(value = "/pessoa/list", method = RequestMethod.GET)
    public ModelAndView listPerson(){
        List<Person> persons = controlPerson.list();
        ModelAndView mav = new ModelAndView("managePerson");
        mav.addObject("pessoas", persons);
        return mav;
    }
    
    @RequestMapping(value = "/pessoa/excluir", method = RequestMethod.POST)
    public String excluir(int idPerson){
        controlPerson.excluir(idPerson);
        return "redirect:list";
    }
    
    
    @RequestMapping(value = "/pessoa/formeditar", method = RequestMethod.GET)
    public ModelAndView gotoEditar(int idPerson){
        Person p = controlPerson.findById(idPerson);
        ModelAndView mav = new ModelAndView("editPerson");
        mav.addObject("toedit", p);
        return mav;
    }
    
    
    @RequestMapping(value = "/pessoa/editar", method = RequestMethod.POST)
    public String editar(Person p){
        controlPerson.editar(p);
        return "redirect:list";
    }
}
