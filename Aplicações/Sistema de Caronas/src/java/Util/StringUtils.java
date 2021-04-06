package Util;

public class StringUtils {
    public static String treatHoursAndMinutesWithOneDigit(String toTreat){
        String toReturn = toTreat;
        if(toReturn != null && toReturn.length() == 1 ){
            toReturn = "0" + toReturn;
        }
        return toReturn;
    }
}
