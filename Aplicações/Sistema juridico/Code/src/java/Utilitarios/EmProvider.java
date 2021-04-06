/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilitarios;

import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmProvider {
  private static final String DB_PU = "jusHelpPU";

    public static final boolean DEBUG = false;

    private static final EmProvider singleton = new EmProvider();

    private EntityManagerFactory emf;

    private EmProvider() {}

    public static EmProvider getInstance() {
        return singleton;
    }


    public EntityManagerFactory getEntityManagerFactory() {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory(DB_PU);
        }
        if(DEBUG) {
            System.out.println("factory created on: " + new Date());
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
