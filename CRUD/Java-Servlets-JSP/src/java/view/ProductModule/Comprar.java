package view.ProductModule;

import dao.ProdutoDAO;
import dao.VendasDAO;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Produto;
import model.Venda;

@WebServlet(name = "Comprar", urlPatterns = {"/comprar"})
public class Comprar extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProdutoDAO pdao = new ProdutoDAO();
        Produto produto = pdao.procuraProdutoPeloID(Integer.parseInt(request.getParameter("idProduto")));
        request.setAttribute("produto", produto);
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/comprar.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("POST - CADASTRAR PRODUTO");

        Venda u = new Venda();
        u.setData(Calendar.getInstance().getTime().toString());
        u.setNomeComprador(request.getParameter("nomeComprador"));
        u.setCartaoComprador(request.getParameter("cartaoComprador"));
        u.setCodSegurancaComprador(request.getParameter("codSegurancaComprador"));
        u.setValor(Double.parseDouble(request.getParameter("valor")));
        u.setIdProduto(Integer.parseInt(request.getParameter("idProduto")));

        VendasDAO dao = new VendasDAO();

        if (dao.cadastraVenda(u)) {
            RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/compraFeitaComSucesso.jsp");
        dispatcher.forward(request, response);
        } else {
            //enviar um atributo msg de erro
            request.setAttribute("erro", "Produto não inserido.");
        }
        ;
    }

}
