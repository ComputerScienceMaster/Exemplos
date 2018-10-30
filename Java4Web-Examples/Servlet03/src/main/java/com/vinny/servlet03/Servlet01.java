package com.vinny.servlet03;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(urlPatterns = "/01")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class Servlet01 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private int id = 0;

    @Override
    public void doGet(HttpServletRequest req,
            HttpServletResponse res) throws IOException {
        PrintWriter writer = res.getWriter();
        writer.println("<!DOCTYPE HTML>");
        writer.println("<html>");
        writer.println("    <head>");
        writer.println("        <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />");
        writer.println("        <title>Servlet01</title>");
        writer.println("    </head>");
        writer.println("    <body>");
        writer.println("        <h1>Exemplos upload com servlet</h1>");
        writer.println("        <form action=\"01\" method=\"POST\"");
        writer.println("                          accept-charset=\"utf-8\"");
        writer.println("                          enctype=\"multipart/form-data\">");
        writer.println("            <input type=\"file\" name=\"arquivo\" value=\"\" />");
        writer.println("            <input type=\"submit\" name=\"enviar\" value=\"submit\" />");
        writer.println("        </form>");
        writer.println("    </body>");
        writer.println("</html>");
    }

    @Override
    public void doPost(HttpServletRequest req,
            HttpServletResponse res) throws IOException, ServletException {
        PrintWriter writer = res.getWriter();
        Part part = req.getPart("arquivo");
        String images_path = req.getServletContext().getRealPath("/uploads");
        InputStream in = part.getInputStream();
        if (part.getContentType().equals("image/png")) {
            FileOutputStream out = new FileOutputStream(
                    new File(images_path + "/" + this.id++ + ".png"));
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.close();
            writer.println("<!DOCTYPE HTML>");
            writer.println("<html>");
            writer.println("    <head>");
            writer.println("        <meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />");
            writer.println("        <title>Servlet01</title>");
            writer.println("    </head>");
            writer.println("    <body>");
            writer.println("        <h1>Sucesso!</h1>");
             writer.println("    </body>");
            writer.println("</html>");
        }
    }

}
