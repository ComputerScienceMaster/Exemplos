package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Car;
import model.Task;
import model.UserAccount;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T14:31:32")
@StaticMetamodel(Travel.class)
public class Travel_ { 

    public static volatile SingularAttribute<Travel, Integer> idTravel;
    public static volatile SingularAttribute<Travel, String> timeEnd;
    public static volatile SingularAttribute<Travel, UserAccount> owner;
    public static volatile SingularAttribute<Travel, Boolean> isRideAllowed;
    public static volatile SingularAttribute<Travel, String> timeInitial;
    public static volatile SingularAttribute<Travel, String> destination;
    public static volatile SingularAttribute<Travel, String> description;
    public static volatile ListAttribute<Travel, Task> taskList;
    public static volatile SingularAttribute<Travel, Date> dateInitial;
    public static volatile SingularAttribute<Travel, Date> dateEnd;
    public static volatile SingularAttribute<Travel, Boolean> isDone;
    public static volatile SingularAttribute<Travel, Car> carPlate;
    public static volatile ListAttribute<Travel, UserAccount> userAccountList;

}