package Model;

import Model.Cause;
import Model.Client;
import Model.Company;
import Model.Trial;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-30T08:58:17")
@StaticMetamodel(Admin.class)
public class Admin_ { 

    public static volatile SingularAttribute<Admin, String> rgEmiter;
    public static volatile SingularAttribute<Admin, String> emailUser;
    public static volatile SingularAttribute<Admin, String> city;
    public static volatile SingularAttribute<Admin, String> address2;
    public static volatile SingularAttribute<Admin, String> address1;
    public static volatile ListAttribute<Admin, Trial> trialList;
    public static volatile SingularAttribute<Admin, String> generalRegister;
    public static volatile SingularAttribute<Admin, String> passwordUser;
    public static volatile SingularAttribute<Admin, String> rgState;
    public static volatile SingularAttribute<Admin, Date> birthDate;
    public static volatile SingularAttribute<Admin, byte[]> imageUser;
    public static volatile SingularAttribute<Admin, String> cep;
    public static volatile SingularAttribute<Admin, String> oab;
    public static volatile SingularAttribute<Admin, String> loginUser;
    public static volatile SingularAttribute<Admin, String> nationality;
    public static volatile SingularAttribute<Admin, String> phone;
    public static volatile ListAttribute<Admin, Cause> causeList;
    public static volatile ListAttribute<Admin, Company> companyList;
    public static volatile SingularAttribute<Admin, String> state;
    public static volatile ListAttribute<Admin, Client> clientList;
    public static volatile SingularAttribute<Admin, String> complement;
    public static volatile SingularAttribute<Admin, String> fullNameUser;
    public static volatile SingularAttribute<Admin, String> maritalStatus;

}