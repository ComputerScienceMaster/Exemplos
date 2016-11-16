package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Servlet1 extends HttpServlet {

    public void doGet (HttpServletRequest req,
                       HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("logado") != null) {
            res.getWriter().println(
                    "<h1>Esta página está protegida</h1>");
            res.getWriter().println("<div>Oi " +
                    req.getSession()
                       .getAttribute("usuario") + "</div>");
        } else {
            res.sendRedirect("/Sessions01/login");
        }
    }
}
