package Converters;


import View.EmProvider;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConverterSample implements Converter{
    
    //MarcadorJpaController cat = new MarcadorJpaController(EmProvider.getInstance().getEntityManagerFactory());
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return null;//cat.findMarcador(Long.parseLong(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        //Marcador t = (Marcador)o;
        return null;//String.valueOf(t.getIdMarcador());
    }
}
