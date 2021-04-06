package Model;

import Model.Admin;
import Model.Document;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-30T08:58:17")
@StaticMetamodel(Client.class)
public class Client_ { 

    public static volatile SingularAttribute<Client, String> rgEmiter;
    public static volatile SingularAttribute<Client, String> address2;
    public static volatile SingularAttribute<Client, String> city;
    public static volatile SingularAttribute<Client, String> address1;
    public static volatile SingularAttribute<Client, String> generalRegister;
    public static volatile SingularAttribute<Client, String> county;
    public static volatile SingularAttribute<Client, String> fullName;
    public static volatile SingularAttribute<Client, Date> birthDate;
    public static volatile SingularAttribute<Client, String> rgState;
    public static volatile SingularAttribute<Client, String> cep;
    public static volatile SingularAttribute<Client, String> cpfRegister;
    public static volatile SingularAttribute<Client, String> nationality;
    public static volatile SingularAttribute<Client, String> phone;
    public static volatile ListAttribute<Client, Document> documentList;
    public static volatile SingularAttribute<Client, String> district;
    public static volatile SingularAttribute<Client, Admin> adminloginUser;
    public static volatile SingularAttribute<Client, String> ctps;
    public static volatile SingularAttribute<Client, String> state;
    public static volatile SingularAttribute<Client, String> job;
    public static volatile SingularAttribute<Client, String> votersTitle;
    public static volatile SingularAttribute<Client, String> maritalStatus;
    public static volatile SingularAttribute<Client, String> email;

}