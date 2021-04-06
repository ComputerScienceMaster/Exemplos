package Converters;

import Controler.DocumentmodelJpaController;
import Model.Documentmodel;
import Utilitarios.EmProvider;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConverterDocumentModel implements Converter {

    DocumentmodelJpaController cat = new DocumentmodelJpaController(EmProvider.getInstance().getEntityManagerFactory());

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        try {
            return cat.findDocumentmodel(Integer.parseInt(string));
        } catch (Exception e) {
            return new Documentmodel();
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o instanceof String) {
            return "Selecione um...";
        }
        Documentmodel t = (Documentmodel) o;
        return String.valueOf(t.getIdDocumentModel());
    }
}
