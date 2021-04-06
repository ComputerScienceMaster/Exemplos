package testes;

import org.junit.Test;

public class TestCEP {

    @Test
    public void testeCEP() {
        GetCEP c = new GetCEP();
        try {
            System.out.println(c.getCidade("86340-000"));
            System.out.println(c.getBairro("86340-000"));
            System.out.println(c.getEndereco("86340-000"));
            System.out.println(c.getUF("86340-000"));
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
