package testes;

import Utilitarios.EscreveNumeroPorExtenso;
import org.junit.Test;

public class EscreveNumeroPorExtensoTest {

    @Test
    public void testValorPorExtenso() {
        String resultado = EscreveNumeroPorExtenso.valorPorExtenso(10000000.35);
        System.out.println(resultado);
    }

}
