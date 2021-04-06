package Model;

import Model.Admin;
import Model.Tag;
import Model.Variable;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-30T08:58:17")
@StaticMetamodel(Trial.class)
public class Trial_ { 

    public static volatile SingularAttribute<Trial, Integer> idTrial;
    public static volatile SingularAttribute<Trial, String> name;
    public static volatile ListAttribute<Trial, Variable> variableList;
    public static volatile SingularAttribute<Trial, Tag> tagidTag;
    public static volatile SingularAttribute<Trial, Admin> userloginUser;

}