package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import models.CidadesModel;

public class CidadesController extends HttpServlet {

    public void doGet (HttpServletRequest req,
                       HttpServletResponse res)
                       throws IOException, ServletException {
        List <CidadesModel> cidades =
            CidadesModel.find(this.getServletContext()
                                  .getRealPath(this.getInitParameter("cidades")));
        req.setAttribute("cidades", cidades);
        req.getRequestDispatcher("/WEB-INF/views/cidades/list.jsp")
           .forward(req, res);
    }
}
