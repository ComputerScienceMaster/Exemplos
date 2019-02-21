package view;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="MeuBean")
@SessionScoped
public class MeuBean {

	private String oi = "oi";

	public String getOi() {
		oi = "oi";
		return oi;
	}

	public void setOi(String oi) {
		this.oi = oi;
	}
	
	
}
