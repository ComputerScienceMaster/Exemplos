package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Car;
import model.UserAccount;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T14:31:32")
@StaticMetamodel(Sector.class)
public class Sector_ { 

    public static volatile SingularAttribute<Sector, String> name;
    public static volatile SingularAttribute<Sector, Integer> idSector;
    public static volatile ListAttribute<Sector, Car> carList;
    public static volatile ListAttribute<Sector, UserAccount> userAccountList;

}