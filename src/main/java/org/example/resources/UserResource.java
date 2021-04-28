package org.example.resources;
import org.example.api.User;
import org.example.db.UserDAO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO userDAO;
    public UserResource(UserDAO ud) {
        userDAO = ud;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> users = userDAO.getAllUsers();
        return Response.ok(users).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        User user = userDAO.getUser(id);
        if (user == null) {
            throw new WebApplicationException("User with given ID doesn't exist", Response.Status.NOT_FOUND);
        }
        return Response.ok(user).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@NotNull @Valid User add) {
        if (add.getEmail() == null) {
            throw new WebApplicationException("email cannot be empty", Response.Status.BAD_REQUEST);
        }
        int generatedID = userDAO.addUser(add.getFirst_name(), add.getUsername(), add.getLast_name(), add.getEmail());
        add.setId(generatedID);
        return Response.status(Response.Status.CREATED).entity(add).build();
    }

    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    public Response editUser(@PathParam("id") int id, @NotNull @Valid User update) {
        if (update.getEmail() != null) {
            throw new WebApplicationException("Cannot change the email of a user", Response.Status.BAD_REQUEST);
        }
        boolean updated = userDAO.updateUser(update.getFirst_name(), update.getUsername(), update.getLast_name(), id);
        if (!updated) {
            throw new WebApplicationException("User with given ID doesn't exist", Response.Status.NOT_FOUND);
        }
        User user = userDAO.getUser(id);
        return Response.ok(user).build();
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id) {
        User user = userDAO.getUser(id);
        if (user == null) {
            throw new WebApplicationException("User with given ID doesn't exist", Response.Status.NOT_FOUND);
        }
        boolean deleted = userDAO.deleteUser(id);
        if (!deleted) {
            throw new WebApplicationException("Problem deleting the user", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.ok(user).build();
    }

}
