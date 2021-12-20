package view;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Usuario;
import dao.UsuarioDAO;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("entrei");
        Usuario u = new Usuario();
        u.setLogin(request.getParameter("usuario"));
        u.setSenha(request.getParameter("senha"));

        String page = "index.jsp";

        UsuarioDAO dao = new UsuarioDAO();
        
        if (dao.validar(u)) {
            
            u = dao.procuraUsuarioPeloID(u.getLogin());
            request.setAttribute("usuario", u);
            HttpSession ses = request.getSession();
            ses.setAttribute("usuario",u);
            response.sendRedirect("listarprodutos");
        } else {
            //enviar um atributo msg de erro
            request.setAttribute("erro", "Usuário ou senha inválida!");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
