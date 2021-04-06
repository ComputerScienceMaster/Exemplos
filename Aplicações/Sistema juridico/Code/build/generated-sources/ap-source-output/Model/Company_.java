package Model;

import Model.Admin;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-30T08:58:17")
@StaticMetamodel(Company.class)
public class Company_ { 

    public static volatile SingularAttribute<Company, String> country;
    public static volatile SingularAttribute<Company, String> city;
    public static volatile SingularAttribute<Company, String> address2;
    public static volatile SingularAttribute<Company, String> phone;
    public static volatile SingularAttribute<Company, String> address1;
    public static volatile SingularAttribute<Company, String> name;
    public static volatile SingularAttribute<Company, Admin> adminloginUser;
    public static volatile SingularAttribute<Company, String> cnpj;
    public static volatile SingularAttribute<Company, String> state;
    public static volatile SingularAttribute<Company, String> email;
    public static volatile SingularAttribute<Company, String> cep;

}