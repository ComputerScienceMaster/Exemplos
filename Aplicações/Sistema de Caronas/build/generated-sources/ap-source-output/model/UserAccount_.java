package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Sector;
import model.Task;
import model.Travel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T14:31:32")
@StaticMetamodel(UserAccount.class)
public class UserAccount_ { 

    public static volatile SingularAttribute<UserAccount, String> userLogin;
    public static volatile SingularAttribute<UserAccount, String> password;
    public static volatile SingularAttribute<UserAccount, Sector> sectoridSector;
    public static volatile SingularAttribute<UserAccount, String> phone;
    public static volatile ListAttribute<UserAccount, Travel> travelList1;
    public static volatile ListAttribute<UserAccount, Travel> travelList;
    public static volatile ListAttribute<UserAccount, Task> taskList;
    public static volatile SingularAttribute<UserAccount, String> userName;
    public static volatile SingularAttribute<UserAccount, Boolean> isAdministrator;
    public static volatile SingularAttribute<UserAccount, String> email;

}