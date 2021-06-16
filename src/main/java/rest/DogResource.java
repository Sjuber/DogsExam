package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DogDTO;
import dtos.WalkerDTO;
import errorhandling.ExceptionDTO;
import facades.DogFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import utils.EMF_Creator;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("dogs")
public class DogResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final DogFacade FACADE = DogFacade.getDogFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello Dogs!\"}";
    }

    @Path("countDogs")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getDogCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("allDogs")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllDogs() {
        List<DogDTO> dogDTO;

        dogDTO = FACADE.getAllDogs();
        return GSON.toJson(dogDTO);
    }

    @POST
    @RolesAllowed({"admin"})
    @Path("createDog")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createDog(String json) throws Exception {
        DogDTO dogDTO = GSON.fromJson(json, DogDTO.class);
        DogDTO dogToBePersisted;

        dogToBePersisted = FACADE.createDog(dogDTO.getName(), dogDTO.getBreed(), dogDTO.getGender(), dogDTO.getBirthDate());
        return GSON.toJson(dogToBePersisted);
    }

    @Path("deleteDog/{name}")
    @RolesAllowed({"admin"})
    @DELETE
    public void deleteByName(@PathParam("name") String name) {
        FACADE.deleteDog(name);
    }

    @Path("getwalkers/{dogname}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getASByCharacter(@PathParam("dogname") String dogName) {
        List<WalkerDTO> walkDTO;
        ExceptionDTO eDTO;
        try {
            walkDTO = FACADE.getWalkersByDog(dogName);
        } catch (Exception ex) {
            eDTO = new ExceptionDTO(404, ex.getMessage());
            return eDTO.toString();
        }
        return GSON.toJson(walkDTO);
    }

}
