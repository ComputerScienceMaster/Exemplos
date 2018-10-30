package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Servlet2 extends HttpServlet {

    public void doGet (HttpServletRequest req,
                       HttpServletResponse res) throws IOException {
        Cookie [] cookies = req.getCookies();
        Cookie cookieAbobrinha = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("status")) {
                    cookieAbobrinha = cookie;
                    break;
                }
            }
        }
        if (cookieAbobrinha != null && cookieAbobrinha.getValue().equals("logado")) {
            res.getWriter().println(
                    "<h1>Página secreta 2</h1>");
        } else {
            res.sendRedirect("/login");
        }
    }
}
