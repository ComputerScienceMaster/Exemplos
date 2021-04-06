package Converters;

import Controler.ClientJpaController;
import Model.Client;
import Utilitarios.EmProvider;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConverterClients implements Converter {

    ClientJpaController cat = new ClientJpaController(EmProvider.getInstance().getEntityManagerFactory());

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            return cat.findClient(string);
        } catch (Exception e) {
            return new Client();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof String) {
            return "Selecione...";
        }
        Client t = (Client) o;
        return String.valueOf(t.getCpfRegister());
    }
}
