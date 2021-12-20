package utils;

import java.util.InputMismatchException;
import javafx.scene.input.KeyEvent;

public class CharacterValidation {

    private String toValidate;

    public static KeyEvent acceptMoney(KeyEvent evt, String s) {
        char actual = evt.getCharacter().charAt(0);
        char aux;
        char comma = ",".charAt(0);
        boolean containsComma = false;
        boolean containsNumbers = false;
        int indexComma = - 3;

        for (int i = 0; i <= s.length(); i++) {

            try {
                aux = s.charAt(i);
            } catch (Exception e) {
                aux = actual;
            }

            if (aux == comma) {
                containsComma = true;
                indexComma = i;
                break;
            }
        }

        if (actual == "0".charAt(0)
                || actual == "1".charAt(0)
                || actual == "2".charAt(0)
                || actual == "3".charAt(0)
                || actual == "4".charAt(0)
                || actual == "5".charAt(0)
                || actual == "6".charAt(0)
                || actual == "7".charAt(0)
                || actual == "8".charAt(0)
                || actual == "9".charAt(0)) {
            containsNumbers = true;
        }

        if (containsNumbers) {
            if (containsComma) {
                if (indexComma + 3 == s.length()) {
                    evt.consume();
                }
            } else {
                return evt;
            }
        }
        if (containsComma) {
            if (indexComma + 3 == s.length()) {
                evt.consume();
            } else {
                return evt;
            }
        }
        evt.consume();
        return evt;
    }

    public static KeyEvent acceptNumbers(KeyEvent evt) {
        char actual = evt.getCharacter().charAt(0);
        boolean containsNumbers = false;

        if (actual == "0".charAt(0)
                || actual == "1".charAt(0)
                || actual == "2".charAt(0)
                || actual == "3".charAt(0)
                || actual == "4".charAt(0)
                || actual == "5".charAt(0)
                || actual == "6".charAt(0)
                || actual == "7".charAt(0)
                || actual == "8".charAt(0)
                || actual == "9".charAt(0)) {
            containsNumbers = true;
        }

        if (containsNumbers) {
            return evt;
        } else {
            evt.consume();
            return evt;
        }
    }

    public static KeyEvent acceptCpfCell(KeyEvent evt, String s) {
        char actual = evt.getCharacter().charAt(0);
        boolean containsNumbers = false;

        if (s.length() >= 11) {
            evt.consume();
            return evt;
        }

        if (actual == "0".charAt(0)
                || actual == "1".charAt(0)
                || actual == "2".charAt(0)
                || actual == "3".charAt(0)
                || actual == "4".charAt(0)
                || actual == "5".charAt(0)
                || actual == "6".charAt(0)
                || actual == "7".charAt(0)
                || actual == "8".charAt(0)
                || actual == "9".charAt(0)) {
            containsNumbers = true;
        }

        if (containsNumbers) {
            return evt;
        } else {
            evt.consume();
            return evt;
        }
    }

    public static Float validateMoney(String s) {
        StringBuilder x = new StringBuilder(s);
        char comma = ",".charAt(0);
        char point = ".".charAt(0);
        char aux;
        int indexComma = - 1;
        Float value;

        for (int i = 0; i < s.length(); i++) {
            aux = s.charAt(i);
            if (aux == comma) {
                indexComma = i;
                x.setCharAt(i, point);
                break;
            }
        }

        if (indexComma == - 1) {
            s = x + ".00";
        }
        if (indexComma == s.length() - 1) {
            s = x + "00";
        }
        if (indexComma == s.length() - 2) {
            s = x + "0";
        }
        if (indexComma == s.length() - 3) {
            s = x + "";
        }

        value = Float.parseFloat(s);
        return value;
    }

    public static boolean validateCPF(String CPF) {
        if (CPF.equals("00000000000") || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48);
            }
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return true;
            } else {
                return false;
            }
        } catch (InputMismatchException erro) {
            return false;
        }
    }

    public static String showCPF(String CPF) {
        return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "."
                + CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }
}
