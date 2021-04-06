package Model;

import Model.Tag;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-30T08:58:17")
@StaticMetamodel(Documentpart.class)
public class Documentpart_ { 

    public static volatile SingularAttribute<Documentpart, String> summary;
    public static volatile SingularAttribute<Documentpart, Integer> idDocumentPart;
    public static volatile SingularAttribute<Documentpart, String> corpus;
    public static volatile SingularAttribute<Documentpart, String> title;
    public static volatile SingularAttribute<Documentpart, Tag> partidPart;

}