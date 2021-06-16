package facades;

import dtos.WalkerDTO;
import entities.Walker;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
//import security.errorhandling.AuthenticationException;

public class WalkerFacade {

    private static EntityManagerFactory emf;
    private static WalkerFacade instance;

    private WalkerFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static WalkerFacade getWalkerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new WalkerFacade();
        }
        return instance;
    }

    public long getWalkerCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long walkerCount = (long) em.createQuery("SELECT COUNT(w) FROM Walker w").getSingleResult();
            return walkerCount;
        } finally {
            em.close();
        }

    }

    public List<WalkerDTO> getAllWalkers() {
        List<WalkerDTO> wadtos = new ArrayList<>();
        EntityManager em = emf.createEntityManager();
        TypedQuery<Walker> q1 = em.createQuery("SELECT w From Walker w", Walker.class);
        List<Walker> wl = q1.getResultList();
        wl.forEach(b -> wadtos.add(new WalkerDTO(b)));
        return wadtos;
    }

}
