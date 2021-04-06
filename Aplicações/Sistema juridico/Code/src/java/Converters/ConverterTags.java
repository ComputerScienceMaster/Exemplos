package Converters;

import Controler.TagJpaController;
import Model.Tag;
import Utilitarios.EmProvider;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConverterTags implements Converter {

    TagJpaController cat = new TagJpaController(EmProvider.getInstance().getEntityManagerFactory());

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return cat.findTag(Integer.parseInt(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Tag t = (Tag) o;
        return String.valueOf(t.getIdTag());
    }
}
