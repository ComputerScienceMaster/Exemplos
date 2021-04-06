package converters;

import controller.CarJpaController;
import view.EmProvider;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Car;

@FacesConverter("ConvertCars")
public class ConvertCars implements Converter{
    
    CarJpaController car = new CarJpaController(EmProvider.getInstance().getEntityManagerFactory());
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return car.findCar((string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Car t = (Car)o;
        return t.getPlate();
    }
}