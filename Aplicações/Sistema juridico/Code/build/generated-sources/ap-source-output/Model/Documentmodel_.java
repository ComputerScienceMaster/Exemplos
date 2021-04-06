package Model;

import Model.Document;
import Model.Tag;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-30T08:58:17")
@StaticMetamodel(Documentmodel.class)
public class Documentmodel_ { 

    public static volatile SingularAttribute<Documentmodel, String> summary;
    public static volatile SingularAttribute<Documentmodel, Integer> idDocumentModel;
    public static volatile ListAttribute<Documentmodel, Document> documentList;
    public static volatile SingularAttribute<Documentmodel, String> corpus;
    public static volatile SingularAttribute<Documentmodel, Tag> tagidTag;
    public static volatile SingularAttribute<Documentmodel, String> title;

}