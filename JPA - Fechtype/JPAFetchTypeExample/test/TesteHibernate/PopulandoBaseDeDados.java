package TesteHibernate;

import controler.CompraJpaController;
import controler.ItemJpaController;
import controler.PessoaJpaController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.Compra;
import model.Item;
import model.Pessoa;
import view.EmProvider;

public class PopulandoBaseDeDados {

    static PessoaJpaController controlPessoa = new PessoaJpaController(EmProvider.getInstance().getEntityManagerFactory());
    static ItemJpaController controlItem = new ItemJpaController(EmProvider.getInstance().getEntityManagerFactory());
    static CompraJpaController controlCompra = new CompraJpaController(EmProvider.getInstance().getEntityManagerFactory());

    public static void main(String[] args) {

        createPessoas();
        createItems();
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
        List<Item> compra = controlItem.findItemEntities();
        List<Pessoa> compradores = controlPessoa.findPessoaEntities();
        Compra c1 = new Compra(Calendar.getInstance().getTime(), "cartão", compra, compradores.get(0));
        Compra c2 = new Compra(Calendar.getInstance().getTime(), "dinheiro", compra, compradores.get(2));
        Compra c3 = new Compra(Calendar.getInstance().getTime(), "cartão", compra, compradores.get(1));
        Compra c4 = new Compra(Calendar.getInstance().getTime(), "dinheiro", compra, compradores.get(0));

        controlCompra.create(c1);
        controlCompra.create(c2);
        controlCompra.create(c3);
        controlCompra.create(c4);

    }

    public static void createItems() {

        Item i1 = new Item("Arroz tio joão", "arroz muito bom", 5.0);
        Item i2 = new Item("Feijão carioca", "Feijão muito bom ", 10.0);
        Item i3 = new Item("Brocolis fino", "Brocolis muito bom", 15.0);
        Item i4 = new Item("bolacha traquinas", "Bolacha muito boa", 13.0);

        controlItem.create(i1);
        controlItem.create(i2);
        controlItem.create(i3);
        controlItem.create(i4);
    }
}
