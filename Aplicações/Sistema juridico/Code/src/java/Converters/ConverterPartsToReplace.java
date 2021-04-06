package Converters;

import Controler.TagJpaController;
import Model.Tag;
import Utilitarios.EmProvider;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("ConverterPartsToReplace")
public class ConverterPartsToReplace implements Converter {

    TagJpaController cat = new TagJpaController(EmProvider.getInstance().getEntityManagerFactory());

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return string;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return (String)o;
    }
}
