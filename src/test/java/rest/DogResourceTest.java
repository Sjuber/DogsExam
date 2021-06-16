package rest;

import entities.Dog;
import entities.RenameMe;
import entities.Walker;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class DogResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Dog d1, d2, d3;
    private static Walker w1, w2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        w1 = new Walker("John", "NiceStreet", "12 34 56 78");
        w2 = new Walker("Wick", "CoolStreet", "87 65 43 21");
        d1 = new Dog("Daniel", "Sorrow", "Male", "13/12/2278");
        d2 = new Dog("Joshua", "Caesars Legion", "Male", "06/12/2278");
        d3 = new Dog("SaltUponWounds", "White Legs", "Male", "27/12/2278");
        d2.setBreed("Dead Horses");
        w1.addDog(d1);
        w1.addDog(d2);
        w2.addDog(d1);
        w2.addDog(d3);
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Walker.deleteAllRows").executeUpdate();
            em.createNamedQuery("Dog.deleteAllRows").executeUpdate();
            em.persist(w1);
            em.persist(w2);
            em.persist(d1);
            em.persist(d2);
            em.persist(d3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/dogs").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void dogDummyString() throws Exception {
        given()
                .contentType("application/json")
                .get("/dogs/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello Dogs!"));
    }

    @Test
    public void testCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/dogs/countDogs").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("count", equalTo(3));
    }

//    @Test
//    public void testAll() throws Exception {
//        given()
//                .contentType("application/json")
//                .get("/dogs/countDogs").then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("", equalTo("{count=3}"));
//    }
}
