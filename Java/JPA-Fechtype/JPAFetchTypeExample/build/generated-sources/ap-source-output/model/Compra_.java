package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Pessoa;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-06T19:31:56")
@StaticMetamodel(Compra.class)
public class Compra_ { 

    public static volatile SingularAttribute<Compra, Pessoa> pessoaidPessoa;
    public static volatile SingularAttribute<Compra, String> formaDePagamento;
    public static volatile SingularAttribute<Compra, Date> data;
    public static volatile SingularAttribute<Compra, Integer> idCompra;

}