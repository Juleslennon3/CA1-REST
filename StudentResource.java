package com.example.resource;

import com.example.dao.StudentDAO;
import com.example.model.Student;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentResource {

    private StudentDAO studentDAO = new StudentDAO();

    @POST
    public Response createStudent(Student student) {
        studentDAO.persist(student);
        return Response.status(Response.Status.CREATED).entity(student).build();
    }

    @GET
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @GET
    @Path("/{id}")
    public Response getStudentById(@PathParam("id") Long id) {
        Student student = studentDAO.find(id);
        if (student != null) {
            return Response.ok(student).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateStudent(@PathParam("id") Long id, Student student) {
        student.setId(id); 
        studentDAO.update(student);
        return Response.ok(student).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") Long id) {
        Student student = studentDAO.find(id);
        if (student != null) {
            studentDAO.remove(student);
            return Response.noContent().build(); 
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Student not found").build();
        }
    }
}
