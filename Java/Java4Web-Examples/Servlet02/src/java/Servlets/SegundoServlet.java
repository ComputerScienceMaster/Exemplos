package Servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class SegundoServlet extends HttpServlet {

    private ArrayList <String> cidades;

    private void printHTML (PrintWriter writer,
                            String busca) throws IOException {
        writer.println("<!DOCTYPE HTML>");
        writer.println("<html>");
        writer.println("    <head>");
        writer.println("        <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />");
        writer.println("        <title>Primeiro Servlet rodando</title>");
        writer.println("        <script type=\"text/javascript\" src=\"/jquery-2.1.1.min.js\"></script>");
        writer.println("    </head>");
        writer.println("    <body>");
        writer.println("        <h1>Primeiro Servlet rodando</h1>");
        writer.println("        <form action=\"Servlet02\" method=\"get\" accept-charset=\"utf-8\">");
        writer.println("            <label for=\"busca\">Busca: </label>");
        writer.println("            <input type=\"text\" autocomplete=\"off\" name=\"busca\" value=\"" + busca + "\" id=\"busca\" />");
        writer.println("        </form>");
        writer.println("        <ul>");

        for (String fruta : this.cidades) {
            if (fruta.toUpperCase().startsWith(busca.toUpperCase()))
                writer.println("<li>" + fruta + "</li>");
        }

        writer.println("        </ul>");

        writer.println("<script type=\"text/javascript\" charset=\"utf-8\">");
        writer.println("    $(\"#busca\").keyup(function () {");
        writer.println("            $(\"ul\").html(\"\");");
        writer.println("            $.ajax(\"./\", {");
        writer.println("                type: \"get\",");
        writer.println("                data: {");
        writer.println("                    busca: $(this).val()");
        writer.println("                },");
        writer.println("                accepts: {\"json\": \"application/json\"},");
        writer.println("                dataType: \"json\"");
        writer.println("            }).done(function (data) {");
        writer.println("                for (var i = 0; i < (data.length - 1); i++)");
        writer.println("                    $(\"<li></li>\").html(data[i]).appendTo(\"ul\");");
        writer.println("            });");
        writer.println("    });");
        writer.println("</script>");
        writer.println("    </body>");
        writer.println("</html>");
    }

    public void init(ServletConfig config) throws ServletException {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                        config.getServletContext().getResourceAsStream(
                            config.getInitParameter("cidades")), "iso-8859-1"));
            this.cidades = new ArrayList <>();
            String fruta = reader.readLine();
            while (fruta != null) {
                this.cidades.add(fruta);
                fruta = reader.readLine();
            }
            Collections.sort(this.cidades);
        } catch (IOException exception) {
            throw new ServletException(exception.getMessage());
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        String busca = request.getParameter("busca") == null ?
                            "" : request.getParameter("busca");
            this.printHTML(writer, busca);
    }
}
