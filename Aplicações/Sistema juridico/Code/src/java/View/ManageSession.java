package View;

import javax.faces.context.FacesContext ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpSession ;


public class ManageSession {

   
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
            return session.getAttribute("userName").toString();
        }

        public static String getUserId() {
            HttpSession session = getSession();
            if (session != null) {
                return (String) session.getAttribute("userid");
            } else {
                return null;
            }
        }
   }
