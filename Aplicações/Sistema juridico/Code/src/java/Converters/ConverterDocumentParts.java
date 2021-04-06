package Converters;

import Controler.DocumentpartJpaController;
import Model.Documentpart;
import Utilitarios.EmProvider;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConverterDocumentParts implements Converter {

    DocumentpartJpaController cat = new DocumentpartJpaController(EmProvider.getInstance().getEntityManagerFactory());

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return cat.findDocumentpart(Integer.parseInt(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Documentpart t = (Documentpart) o;
        return String.valueOf(t.getIdDocumentPart());
    }
}
