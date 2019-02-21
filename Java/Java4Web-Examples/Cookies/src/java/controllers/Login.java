package controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.io.IOException;

public class Login extends HttpServlet {

    public void doGet (HttpServletRequest req,
                       HttpServletResponse res) throws IOException {
        PrintWriter writer = res.getWriter();
        writer.println("<!DOCTYPE HTML>");
        writer.println("<html>");
        writer.println("    <head>");
        writer.println("        <meta http-equiv=\"content-type\"");
        writer.println("              content=\"text/html; charset=utf-8\"/>");
        writer.println("        <title>Login</title>");
        writer.println("    </head>");
        writer.println("    <body>");
        writer.println("        <h1>Login</h1>");
        writer.println("        <form action=\"/Cookies/login\" method=\"POST\">");
        writer.println("        <fieldset>");
        writer.println("            <legend>Login</legend>");
        writer.println("            <input type=\"text\" name=\"usuario\" value=\"\">");
        writer.println("            <input type=\"password\" name=\"senha\" value=\"\">");
        writer.println("            <input type=\"submit\" value=\"logar\">");
        writer.println("        </fieldset>");
        writer.println("        </form>");
        writer.println("    </body>");
        writer.println("</html>");
    }

    public void doPost (HttpServletRequest req,
                        HttpServletResponse res) throws IOException {
        if (req.getParameter("usuario").equals("vini") &&
            req.getParameter("senha").equals("123")) {
            res.addCookie(new Cookie("status", "logado"));
            PrintWriter writer = res.getWriter();
            writer.println("<!DOCTYPE HTML>");
            writer.println("<html>");
            writer.println("    <head>");
            writer.println("        <meta http-equiv=\"content-type\"");
            writer.println("              content=\"text/html; charset=utf-8\"/>");
            writer.println("        <title>Login</title>");
            writer.println("    </head>");
            writer.println("    <body>");
            writer.println("         <img src=\"resources/entrou.jpg\"/>");
            writer.println("    </body>");
            writer.println("</html>");
        } else {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            PrintWriter writer = res.getWriter();
            writer.println("<!DOCTYPE HTML>");
            writer.println("<html>");
            writer.println("    <head>");
            writer.println("        <meta http-equiv=\"content-type\"");
            writer.println("              content=\"text/html; charset=utf-8\"/>");
            writer.println("        <title>Login</title>");
            writer.println("    </head>");
            writer.println("    <body>");
            writer.println("         <img src=\"resources/gandalf.jpg\"/>");
            writer.println("    </body>");
            writer.println("</html>");
        }
    }
}
