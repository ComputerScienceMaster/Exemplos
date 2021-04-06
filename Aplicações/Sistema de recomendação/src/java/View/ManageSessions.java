package View;

import Controllers.UserAccountJpaController;
import Model.UserAccount;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ManageSessions {
    private static UserAccountJpaController controlUser = new UserAccountJpaController(EmProvider.getInstance().getEntityManagerFactory());
   
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    public static String getLoginUser() {
        HttpSession session = getSession();
        if (session != null) {
            return (String) session.getAttribute("userLogin");
        } else {
            return null;
        }
    }

    public static Integer getUserId() {
        HttpSession session = getSession();
        if (session != null) {
            return (Integer) session.getAttribute("userId");
        } else {
            return null;
        }
    }
    
    public static UserAccount loadUserInSession() {
        UserAccount toReturn = null;
        Integer usrId = ManageSessions.getUserId();
        if (usrId != null) {
            toReturn = controlUser.findUserAccount(usrId);
        }
        return toReturn;
    }
}
