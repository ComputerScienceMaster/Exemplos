package Utilitarios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String getActualDateComplete() {

        DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));

        // DATA ATUAL DO SISTEMA : new Date(System.currentTimeMillis())
        String dataExtenso = formatador.format(new Date(System.currentTimeMillis()));
        int index = dataExtenso.indexOf(",") + 2;
        int lenght = dataExtenso.length();
        return dataExtenso.substring(index, lenght).toLowerCase();
    }

    public static String formatDate(Date data) {
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL, new Locale("pt", "BR"));
        String dataExtenso = formatador.format(data);
        int index = dataExtenso.indexOf(",") + 2;
        int lenght = dataExtenso.length();
        return dataExtenso.substring(index, lenght).toLowerCase();
    }

    public static String getActualDateShort() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(Calendar.getInstance().getTime());
    }
}
