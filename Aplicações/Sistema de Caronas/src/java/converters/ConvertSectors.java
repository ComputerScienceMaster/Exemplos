package converters;

import controller.SectorJpaController;
import view.EmProvider;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Sector;

@FacesConverter("ConvertSectors")
public class ConvertSectors implements Converter{
    
    SectorJpaController set = new SectorJpaController(EmProvider.getInstance().getEntityManagerFactory());
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return set.findSector(Integer.valueOf(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Sector t = (Sector)o;
        return String.valueOf(t.getIdSector());
    }
}