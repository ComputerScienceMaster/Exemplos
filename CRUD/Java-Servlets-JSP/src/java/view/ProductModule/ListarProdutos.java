
package view.ProductModule;

import dao.ProdutoDAO;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Produto;


@WebServlet(name = "ListarProdutos", urlPatterns = {"/listarprodutos"})
public class ListarProdutos extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProdutoDAO dao = new ProdutoDAO();
        ArrayList<Produto> listaDeProdutos = dao.procuraTodosProdutos();
            request.setAttribute("listaDeProdutos", listaDeProdutos);
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/ProductModule/listarProdutos.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         System.out.println("POST - CADASTRAR USUARIO");
    }

}
