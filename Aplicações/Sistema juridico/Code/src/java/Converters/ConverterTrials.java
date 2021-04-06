package Converters;

import Controler.TrialJpaController;
import Model.Trial;
import Utilitarios.EmProvider;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class ConverterTrials implements Converter {

    TrialJpaController cat = new TrialJpaController(EmProvider.getInstance().getEntityManagerFactory());

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return cat.findTrial(Integer.parseInt(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Trial t = (Trial) o;
        return String.valueOf(t.getIdTrial());
    }
}
