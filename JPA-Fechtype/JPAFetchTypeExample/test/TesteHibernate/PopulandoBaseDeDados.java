package TesteHibernate;

import controller.CompraJpaController;
import controller.PessoaJpaController;
import java.util.Calendar;
import java.util.List;
import model.Compra;
import model.Pessoa;
import view.EmProvider;

public class PopulandoBaseDeDados {

    static PessoaJpaController controlPessoa = new PessoaJpaController(EmProvider.getInstance().getEntityManagerFactory());
    static CompraJpaController controlCompra = new CompraJpaController(EmProvider.getInstance().getEntityManagerFactory());

    public static void main(String[] args) {

        createPessoas();
        createCompras();

    }

    public static void createPessoas() {

        Pessoa p1 = new Pessoa("vinicius dos santos", "vinicius", "vinistos@gmail.com", "1234");
        Pessoa p2 = new Pessoa("Mario da silva", "mario", "mario@gmail.com", "1234");
        Pessoa p3 = new Pessoa("Gabriel garcia", "gabriel", "gabriel@gmail.com", "1234");
        Pessoa p4 = new Pessoa("Isabela Ribeiro", "isabela", "isabela@gmail.com", "1234");

        controlPessoa.create(p1);
        controlPessoa.create(p2);
        controlPessoa.create(p3);
        controlPessoa.create(p4);
    }

    public static void createCompras() {
        List<Pessoa> compradores = controlPessoa.findPessoaEntities();
        Compra c1 = new Compra(Calendar.getInstance().getTime(), "cartão", compradores.get(0));
        Compra c2 = new Compra(Calendar.getInstance().getTime(), "dinheiro", compradores.get(2));
        Compra c3 = new Compra(Calendar.getInstance().getTime(), "cartão", compradores.get(1));
        Compra c4 = new Compra(Calendar.getInstance().getTime(), "dinheiro",  compradores.get(0));

        controlCompra.create(c1);
        controlCompra.create(c2);
        controlCompra.create(c3);
        controlCompra.create(c4);

    }

}
