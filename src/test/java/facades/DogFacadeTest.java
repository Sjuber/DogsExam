package facades;

import entities.Dog;
import utils.EMF_Creator;
import entities.Walker;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class DogFacadeTest {

    private static EntityManagerFactory emf;
    private static DogFacade facade;

    public DogFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = DogFacade.getDogFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Dog.deleteAllRows").executeUpdate();
            em.createNamedQuery("Walker.deleteAllRows").executeUpdate();
            Walker w1 = new Walker("John", "NiceStreet", "12 34 56 78");
            Walker w2 = new Walker("Wick", "CoolStreet", "87 65 43 21");
            Dog dog1 = new Dog("Daniel", "Sorrow", "Male", "13/12/2278");
            Dog dog2 = new Dog("Joshua", "Caesars Legion", "Male", "06/12/2278");
            Dog dog3 = new Dog("SaltUponWounds", "White Legs", "Male", "27/12/2278");
            dog2.setBreed("Dead Horses");
            w1.addDog(dog1);
            w1.addDog(dog2);
            w2.addDog(dog1);
            w2.addDog(dog3);
            em.persist(dog1);
            em.persist(dog2);
            em.persist(dog3);
            em.persist(w1);
            em.persist(w2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void getAmountOfDogsTest() {
        assertEquals(3, facade.getDogCount(), "Expects six rows in the database");
    }

//    @Test
//    public void getAllDogsTest() {
//        assertEquals("[DogDTO{id=7, name=Joshua, breed=Dead Horses, gender=Male, birthDate=06/12/2278, walkers=[WalkerDTO{id=6, name=John, address=NiceStreet, phone=12 34 56 78}]}, DogDTO{id=8, name=Daniel, breed=Sorrow, gender=Male, birthDate=13/12/2278, walkers=[WalkerDTO{id=5, name=Wick, address=CoolStreet, phone=87 65 43 21}, WalkerDTO{id=6, name=John, address=NiceStreet, phone=12 34 56 78}]}, DogDTO{id=9, name=SaltUponWounds, breed=White Legs, gender=Male, birthDate=27/12/2278, walkers=[WalkerDTO{id=5, name=Wick, address=CoolStreet, phone=87 65 43 21}]}]", facade.getAllDogs().toString(), "Expects to get all dogs");
//    }
//
//    @Test
//    public void getWalkersByDogTest() throws Exception {
//        assertEquals("[WalkerDTO{id=2, name=John, address=NiceStreet, phone=12 34 56 78}, WalkerDTO{id=1, name=Wick, address=CoolStreet, phone=87 65 43 21}]", facade.getWalkersByDog("Daniel").toString());
//    }
}
