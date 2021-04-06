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

@WebServlet(name = "AlterarProduto", urlPatterns = {"/alterarproduto"})
public class AlterarProduto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ProdutoDAO pDao = new ProdutoDAO();
        Integer idProduto = Integer.parseInt(request.getParameter("idProduto"));
        Produto p = pDao.procuraProdutoPeloID(idProduto);
        request.setAttribute("produto", p);

        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/WEB-INF/ProductModule/alterarProduto.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("POST - Alterar PRODUTO");
        request.setCharacterEncoding("UTF-8");
        Produto u = new Produto();
        int idProduto = Integer.parseInt(request.getParameter("idProduto"));
        u.setCodigo(idProduto);
        u.setNome(request.getParameter("nome"));
        u.setValor(Double.parseDouble(request.getParameter("valor")));
        u.setDescricao(request.getParameter("descricao"));

        ProdutoDAO dao = new ProdutoDAO();

        if (dao.alteraProduto(u)) {
            response.sendRedirect("listarprodutos");
        } else {
            //enviar um atributo msg de erro
            request.setAttribute("erro", "Erro ao alterar");
        }
        ;
    }

}
