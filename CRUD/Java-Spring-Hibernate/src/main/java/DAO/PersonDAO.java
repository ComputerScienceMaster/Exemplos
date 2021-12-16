package DAO;

import models.Person;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class PersonDAO implements Serializable {

    

    @PersistenceContext
    private EntityManager manager;

    public void create(Person person) {
        manager.persist(person);
    }

    public void excluir(int idPerson) {
        Person p = findById(idPerson);
        manager.remove(p);
    }
    
    public void editar(Person p){
        manager.merge(p);
    }
    
    public List<Person> list() {
        return manager.createQuery("Select p from Person p", Person.class).getResultList();
    }
    
    public Person findById(int idPerson) {
        return manager.find(Person.class, idPerson);
    }
    
}
