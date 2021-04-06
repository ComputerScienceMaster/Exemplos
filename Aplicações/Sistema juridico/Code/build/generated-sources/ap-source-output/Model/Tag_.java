package Model;

import Model.Cause;
import Model.Document;
import Model.Documentmodel;
import Model.Documentpart;
import Model.Tag;
import Model.Trial;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-30T08:58:17")
@StaticMetamodel(Tag.class)
public class Tag_ { 

    public static volatile ListAttribute<Tag, Tag> tagList;
    public static volatile SingularAttribute<Tag, Integer> level;
    public static volatile ListAttribute<Tag, Document> documentList;
    public static volatile ListAttribute<Tag, Cause> causeList;
    public static volatile ListAttribute<Tag, Trial> trialList;
    public static volatile SingularAttribute<Tag, Integer> idTag;
    public static volatile SingularAttribute<Tag, String> title;
    public static volatile ListAttribute<Tag, Documentmodel> documentmodelList;
    public static volatile ListAttribute<Tag, Documentpart> documentpartList;
    public static volatile SingularAttribute<Tag, Tag> parentId;

}