package Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ {

	public static volatile SingularAttribute<Person, Integer> id;
	public static volatile SingularAttribute<Person, String> email;
	public static volatile SingularAttribute<Person, String> name;
	public static volatile SingularAttribute<Person, String> login;
	public static volatile SingularAttribute<Person, String> password;

}

