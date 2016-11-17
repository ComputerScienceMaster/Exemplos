package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import models.CidadesModel;


//Este controller é uma servlet que está mapeada no Web.xml como /index
public class CidadesController extends HttpServlet {

    public void doGet (HttpServletRequest req,
                       HttpServletResponse res)
                       throws IOException, ServletException {
        List <CidadesModel> cidades =
            CidadesModel.find(this.getServletContext()
                                  .getRealPath(this.getInitParameter("cidades")));
        req.setAttribute("cidades", cidades);
        //Esta lista está dentro do WEB-INF pois esta pasta é oculta e
        //não pode ser visualizada pelo cliente
        req.getRequestDispatcher("/WEB-INF/views/cidades/list.jsp")
           .forward(req, res);
    }
}
