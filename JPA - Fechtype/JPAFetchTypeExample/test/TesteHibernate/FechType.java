package TesteHibernate;

import controler.CompraJpaController;
import controler.ItemJpaController;
import controler.PessoaJpaController;
import java.util.List;
import model.Compra;
import model.Pessoa;
import view.EmProvider;

public class FechType {

    static PessoaJpaController controlPessoa = new PessoaJpaController(EmProvider.getInstance().getEntityManagerFactory());
    static ItemJpaController controlItem = new ItemJpaController(EmProvider.getInstance().getEntityManagerFactory());
    static CompraJpaController controlCompra = new CompraJpaController(EmProvider.getInstance().getEntityManagerFactory());

    public static void main(String[] args) {
        
//        Pessoa p = controlPessoa.findPessoa(1);
//        System.out.println(p);

       
//        List<Pessoa> listaDePessoas = controlPessoa.findPessoaEntities();
//        for (Pessoa p : listaDePessoas) {
//            System.out.println(p);
//            System.out.println("\n");
//        }

       
//        List<Compra> listaDeCompra = controlCompra.findCompraEntities();
//
//        for (Compra c : listaDeCompra) {
//            System.out.println(c);
//            System.out.println("\n");
//        }

    }

}
