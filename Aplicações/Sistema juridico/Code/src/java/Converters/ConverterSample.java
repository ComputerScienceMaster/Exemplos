/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Converters;


import Utilitarios.EmProvider;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author vinicius
 */
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
