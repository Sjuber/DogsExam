package utils;

import entities.Dog;
import entities.Walker;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

    public static void main(String[] args) {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
        // CHANGE the three passwords below, before you uncomment and execute the code below
        // Also, either delete this file, when users are created or rename and add to .gitignore
        // Whatever you do DO NOT COMMIT and PUSH with the real passwords
        User user = new User("Chris", "hunde123");
        User admin = new User("Jul", "HarryPotterAndThePrisonerFromAzkaban");
        User both = new User("Pet", "Em");
        Dog d1 = new Dog("Pedro", "Dalmantino", "Male", "13/06/2007");
        Dog d2 = new Dog("Fiffi", "Shibe Inu", "Female", "26/11/2013");
        Dog d3 = new Dog("Odin", "Tibetan Mastiff", "Male", "07/03/2010");
        Dog d4 = new Dog("Laika", "Husky", "Female", "10/10/2010");
        Dog d5 = new Dog("Christie", "Afghan hound", "Female", "09/09/2019");
        Dog d6 = new Dog("Debuggy", "Javan Unit-Terrierest", "Male", "01/01/2011");
        Walker w1 = new Walker("Christian", "Frederiksdalsvej 117", "73 84 91 83");
        Walker w2 = new Walker("Juliane", "Frederiksdalsvej 25", "43 04 85 83");
        Walker w3 = new Walker("Peter", "Nybrovej 281", "27 36 11 96");

        if (admin.getUserPass().equals("test") || user.getUserPass().equals("test") || both.getUserPass().equals("test")) {
            throw new UnsupportedOperationException("You have not changed the passwords");
        }

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        w3.addDog(d1);
        w3.addDog(d2);
        w1.addDog(d3);
        w1.addDog(d4);
        w2.addDog(d5);
        w2.addDog(d6);
        w3.addDog(d4);
        w1.addDog(d6);
        w1.addDog(d5);
        w2.addDog(d3);
        user.addRole(userRole);
        admin.addRole(adminRole);
        admin.addRole(userRole);
        both.addRole(userRole);
        both.addRole(adminRole);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.persist(w1);
        em.persist(w2);
        em.persist(w3);
        em.persist(d1);
        em.persist(d2);
        em.persist(d3);
        em.persist(d4);
        em.persist(d5);
        em.persist(d6);
        em.getTransaction().commit();
        System.out.println("PW: " + user.getUserPass());
//    System.out.println("Testing user with OK password: " + user.verifyPassword("Earth"));
//    System.out.println("Testing user with wrong password: " + user.verifyPassword("Hell"));
        System.out.println("Created TEST Users");

    }

}
