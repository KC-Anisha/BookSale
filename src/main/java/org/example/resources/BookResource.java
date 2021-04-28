package org.example.resources;

import org.example.api.Book;
import org.example.db.BookDAO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    private final BookDAO bookDAO;

    public BookResource(BookDAO bd) {
        bookDAO = bd;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        List<Book> books = bookDAO.getAllBooks();
        return Response.ok(books).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") int id) {
        Book book = bookDAO.getBook(id);
        if (book == null) {
            throw new WebApplicationException("Book with given ID doesn't exist", Response.Status.NOT_FOUND);
        }
        return Response.ok(book).build();
    }

    @Path("/user/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksByUser(@PathParam("id") int id) {
        List<Book> books = bookDAO.getBooksByUser(id);
//        if (books.size() == 0) {
//            throw new WebApplicationException("No Books by user or user doesn't exist", Response.Status.NOT_FOUND);
//        }
        return Response.ok(books).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBook(@NotNull @Valid Book add) {
        if (add.getSeller_id() == 0) {
            throw new WebApplicationException("seller_id cannot be empty", Response.Status.BAD_REQUEST);
        }
        int generatedID = bookDAO.addBook(add.getSeller_id(), add.getTitle(), add.getEdition(), add.getPrice(),
                add.getBook_subject(), add.getBook_description(), add.getAuthor());
        add.setId(generatedID);
        return Response.status(Response.Status.CREATED).entity(add).build();
    }

    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @PUT
    public Response editBook(@PathParam("id") int id, @NotNull @Valid Book update) {
        boolean updated = bookDAO.updateBook(update.getTitle(), update.getEdition(), update.getPrice(),
                update.getBook_subject(), update.getAuthor(), id);
        if (!updated) {
            throw new WebApplicationException("Book with given ID doesn't exist", Response.Status.NOT_FOUND);
        }
        Book book = bookDAO.getBook(id);
        return Response.ok(book).build();
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@PathParam("id") int id) {
        Book book = bookDAO.getBook(id);
        if (book == null) {
            throw new WebApplicationException("Book with given ID doesn't exist", Response.Status.NOT_FOUND);
        }
        boolean deleted = bookDAO.deleteBook(id);
        if (!deleted) {
            throw new WebApplicationException("Problem deleting the book", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.ok(book).build();
    }
}
