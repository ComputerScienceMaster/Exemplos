package controllerJPA;

import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmProvider {
  private static final String DB_PU = "DeskCARPU";

    public static final boolean DEBUG = true;

    private static final EmProvider singleton = new EmProvider();

    private EntityManagerFactory emf;

    private EmProvider() {}

    public static EmProvider getInstance() {
        return singleton;
    }


    public EntityManagerFactory getEntityManagerFactory() {
        if(emf == null) {
            System.out.println("Factory created");
            emf = Persistence.createEntityManagerFactory(DB_PU);
        }
        if(DEBUG) {
            System.out.println("Em created " + new Date());
        }
        return emf;
    }

    public void closeEmf() {
        if(emf.isOpen() || emf != null) {
            emf.close();
        }
        emf = null;
        if(DEBUG) {
            System.out.println("EMF closed at: " + new Date());
        }
    }
}
