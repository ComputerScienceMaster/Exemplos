package Model;

import Model.Admin;
import Model.Causevariable;
import Model.Tag;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-30T08:58:17")
@StaticMetamodel(Cause.class)
public class Cause_ { 

    public static volatile ListAttribute<Cause, Causevariable> causevariableList;
    public static volatile SingularAttribute<Cause, String> name;
    public static volatile SingularAttribute<Cause, Admin> adminloginUser;
    public static volatile SingularAttribute<Cause, Tag> tagidTag;
    public static volatile SingularAttribute<Cause, Integer> idCause;

}