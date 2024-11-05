package com.example.resource;

import com.example.dao.LoanDAO;
import com.example.model.Loan;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/loans")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoanResource {

    private LoanDAO loanDAO = new LoanDAO();

    @POST
    public Response createLoan(Loan loan) {
        loanDAO.persist(loan);
        return Response.status(Response.Status.CREATED).entity(loan).build();
    }

    @GET
    public List<Loan> getAllLoans() {
        return loanDAO.getAllLoans();
    }

    @GET
    @Path("/{id}")
    public Response getLoanById(@PathParam("id") Long id) {
        Loan loan = loanDAO.find(id);
        if (loan != null) {
            return Response.ok(loan).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Loan not found").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateLoan(@PathParam("id") Long id, Loan loan) {
        loan.setId(id);
        loanDAO.update(loan);
        return Response.ok(loan).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteLoan(@PathParam("id") Long id) {
        Loan loan = loanDAO.find(id);
        if (loan != null) {
            loanDAO.remove(loan);
            return Response.noContent().build(); 
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Loan not found").build();
        }
    }
}
