
package view.Vendas;

import dao.VendasDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Venda;


@WebServlet(name = "RelatorioDeVendas", urlPatterns = {"/relatoriodevendas"})
public class RelatorioDeVendas extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VendasDAO dao = new VendasDAO();
        ArrayList<Venda> listaDeVendas = dao.procuraTodosVendas();
            request.setAttribute("listaDeVendas", listaDeVendas);
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/VendasModule/relatorioDeVendas.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         System.out.println("POST - CADASTRAR USUARIO");
    }

}
