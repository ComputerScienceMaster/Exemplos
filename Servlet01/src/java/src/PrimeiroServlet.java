package src;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PrimeiroServlet")
public class PrimeiroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        /* TODO output your page here. You may use following sample code. */
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Primeiro Formulário</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1> Primeiro Formulário </h1>");
        out.println("<form name=\"loginForm\" method=\"GET\" action=\"./PrimeiroServlet\">");
        out.println("<input type=\"text\" name=\"nome\" placeholder=\"digite o seu nome\"/>");
        out.println("<input type=\"text\" name=\"idade\" placeholder=\"digite a sua idade\"/>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

        String nome = (String) request.getParameter("nome");
        String idade = (String) request.getParameter("idade");

        System.out.println("nome: " + nome);
        System.out.println("idade: " + idade);

        String htmlResponse = "";
        htmlResponse += "<h2>Seu nome é: " + nome + "<br/>";
        htmlResponse += "Sua idade é: " + idade + "</h2>";

        out.println(htmlResponse);
    }

}
