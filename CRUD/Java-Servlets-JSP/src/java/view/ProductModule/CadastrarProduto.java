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

@WebServlet(name = "CadastrarProduto", urlPatterns = {"/cadastrarproduto"})
public class CadastrarProduto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/WEB-INF/ProductModule/cadastrarProduto.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("POST - CADASTRAR PRODUTO");
        request.setCharacterEncoding("UTF-8");
        Produto u = new Produto();
        u.setNome(request.getParameter("nome"));
        u.setValor(Double.parseDouble(request.getParameter("valor")));
        u.setDescricao(request.getParameter("descricao"));
        String page = "home.jsp";

        ProdutoDAO dao = new ProdutoDAO();

        if (dao.cadastraProduto(u)) {
            page = "listarprodutos";
            response.sendRedirect(page);
        } else {
            //enviar um atributo msg de erro
            request.setAttribute("erro", "Produto não inserido.");
        }
        ;
    }

}
