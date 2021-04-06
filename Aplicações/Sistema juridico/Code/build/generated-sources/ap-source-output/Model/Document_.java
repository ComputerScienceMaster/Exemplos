package Model;

import Model.Client;
import Model.Documentmodel;
import Model.Tag;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-01-30T08:58:17")
@StaticMetamodel(Document.class)
public class Document_ { 

    public static volatile SingularAttribute<Document, String> summary;
    public static volatile SingularAttribute<Document, Client> clientcpfRegister;
    public static volatile SingularAttribute<Document, Documentmodel> documentModelidDocumentModel;
    public static volatile SingularAttribute<Document, Integer> idDocument;
    public static volatile SingularAttribute<Document, String> corpus;
    public static volatile SingularAttribute<Document, String> companycnpj;
    public static volatile SingularAttribute<Document, Tag> tagidTag;
    public static volatile SingularAttribute<Document, String> title;

}