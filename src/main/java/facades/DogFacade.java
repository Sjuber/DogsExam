package facades;

import dtos.DogDTO;
import dtos.WalkerDTO;
import entities.Dog;
import entities.Walker;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
//import security.errorhandling.AuthenticationException;

public class DogFacade {

    private static EntityManagerFactory emf;
    private static DogFacade instance;

    private DogFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static DogFacade getDogFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogFacade();
        }
        return instance;
    }

    public long getDogCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long dogCount = (long) em.createQuery("SELECT COUNT(d) FROM Dog d").getSingleResult();
            return dogCount;
        } finally {
            em.close();
        }

    }

    public List<DogDTO> getAllDogs() {
        List<DogDTO> dogdtos = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        TypedQuery<Dog> q1 = em.createQuery("SELECT d From Dog d", Dog.class);
        List<Dog> dl = q1.getResultList();
        dl.forEach(d -> dogdtos.add(new DogDTO(d)));
        return dogdtos;
    }

    public DogDTO createDog(String name, String breed, String gender, String birthDate) throws Exception {
        Dog dog = new Dog(name, breed, gender, birthDate);
        DogDTO dogDTO;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(dog);
        em.getTransaction().commit();
        dogDTO = new DogDTO(dog);
        return dogDTO;
    }

    public List<WalkerDTO> getWalkersByDog(String dogName) throws Exception {
        EntityManager em = emf.createEntityManager();
        List<WalkerDTO> walkETOS = new ArrayList<>();
        Dog dog = null;
        if (dogName != null) {
            try {
                em.getTransaction().begin();
                TypedQuery<Dog> dogQ = em.createQuery("SELECT d FROM Dog d WHERE d.name=:name", Dog.class);
                dogQ.setParameter("name", dogName);
                dog = dogQ.getSingleResult();
//                dog = em.find(Dog.class, dogName);
                if (dog == null) {
                    throw new Exception("There is no such dog in the databse by that given name :/");
                }
                em.getTransaction().commit();
            } finally {
                em.close();
            }

        } else {
            throw new Exception("You must insert a name to search for a dog");
        }
        List<Walker> walkList = dog.getWalkerList();
        for (Walker walke : dog.getWalkerList()) {
            walkETOS.add(new WalkerDTO(walke));
        }

        return walkETOS;
    }

    public void deleteDog(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Dog> dog = em.createQuery("SELECT d FROM Dog d WHERE d.name=:name", Dog.class);
            dog.setParameter("name", name);
            Dog dogToBeDeleted = dog.getSingleResult();
            em.remove(dogToBeDeleted);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
