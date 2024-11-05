package com.example.resource;

import com.example.dao.DepositDAO;
import com.example.dao.LoanDAO;
import com.example.dao.StudentDAO;
import com.example.model.Deposit;
import com.example.model.Loan;
import com.example.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/deposits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepositResource {

    private DepositDAO depositDAO = new DepositDAO(); 
    private StudentDAO studentDAO = new StudentDAO(); 
    private LoanDAO loanDAO = new LoanDAO(); 

    @POST
    public Response createDeposit(Deposit deposit, @QueryParam("studentId") Long studentId) {
        // Retrieve student by ID
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Student not found").build();
        }

        // Create a new loan
        Loan loan = new Loan();
        loan.setDescription("Description of the loan"); // Set a description for the loan
        loan.setLoanAmount(1000.00); // Set the loan amount
        loan.setStudent(student); // Associate the loan with the student

        
        loanDAO.persist(loan); // Save the loan to the database

       
        deposit.setLoan(loan); 

        
        depositDAO.persist(deposit); // Save the deposit to the database

        return Response.status(Response.Status.CREATED).entity(deposit).build(); // Return success response
    }

    @GET
    public List<Deposit> getAllDeposits() {
        return depositDAO.getAllDeposits();
    }

    @GET
    @Path("/{id}")
    public Response getDepositById(@PathParam("id") Long id) {
        Deposit deposit = depositDAO.getDepositById(id);
        if (deposit != null) {
            return Response.ok(deposit).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Deposit not found").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateDeposit(@PathParam("id") Long id, Deposit deposit) {
        deposit.setId(id); 
        depositDAO.updateDeposit(deposit);
        return Response.ok(deposit).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDeposit(@PathParam("id") Long id) {
        depositDAO.removeDeposit(id);
        return Response.noContent().build();
    }
}
