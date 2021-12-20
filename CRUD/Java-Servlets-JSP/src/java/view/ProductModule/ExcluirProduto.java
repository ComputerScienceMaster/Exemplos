package view.ProductModule;

import dao.ProdutoDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Produto;

@WebServlet(name = "ExcluirProduto", urlPatterns = {"/excluirproduto"})
public class ExcluirProduto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/ProductModule/ListarProdutos.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("POST - Excluir PRODUTO");

        Produto u = new Produto();
        u.setCodigo(Integer.parseInt(request.getParameter("idProduto")));
        
        ProdutoDAO dao = new ProdutoDAO();

        dao.excluiProduto(u.getCodigo());
        String page = "/ProductModule/listarProdutos.jsp";
        response.sendRedirect("listarprodutos");
    }

}
