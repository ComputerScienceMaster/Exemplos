
package view;

import dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;


@WebServlet(name = "alterarusuario", urlPatterns = {"/alterarusuario"})
public class AlterarUsuario extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/WEB-INF/alterarUsuario.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         System.out.println("POST - CADASTRAR USUARIO");
        Usuario u = new Usuario();
        u.setLogin(request.getParameter("usuario"));
        u.setSenha(request.getParameter("senha"));
        u.setNome(request.getParameter("nome"));
        u.setCpf(request.getParameter("cpf"));
        u.setTelefone(request.getParameter("telefone"));
        u.setEndereco(request.getParameter("endereco"));
        String page = "index.jsp";

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.alteraUsuario(u)) {
            page = "home.jsp";
            request.setAttribute("usuario", u);
        } else {
            //enviar um atributo msg de erro
            request.setAttribute("erro", "Usuário ou senha inválida!");
        }

        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }

}
