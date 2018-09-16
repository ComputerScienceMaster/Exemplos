package TesteHibernate;

import controler.CompraJpaController;
import controler.PessoaJpaController;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Compra;
import model.Pessoa;
import view.EmProvider;

public class TestandoPersistencia {

    public static void main(String[] args) {

        Pessoa p = new Pessoa();

        Date d = new Date();

        p.setNome("Isabela");
        p.setLoggin("isabela tal");
        p.setEmail("icastilhog@gmail.com");
        p.setSenha("teste");

        List<Compra> compras = new ArrayList<>();

        Compra c1 = new Compra();
        c1.setData(d);
        c1.setObservacao("compra impressora");
        c1.setRecebido(true);
        c1.setValor("890");
        CompraJpaController c = new CompraJpaController(EmProvider.getInstance().getEntityManagerFactory());
        c.create(c1);
        
        compras.add(c1);
        
        p.setCompraList(compras);
        
        PessoaJpaController j = new PessoaJpaController(EmProvider.getInstance().getEntityManagerFactory());
        j.create(p);

        System.out.println(j.findPessoaEntities());

    }

}
