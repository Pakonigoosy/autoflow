package com.kargin.autoflow.rest;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.Transmission;
import com.kargin.autoflow.rest.dto.PaginatedResponse;
import com.kargin.autoflow.service.TransmissionService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/transmissions")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class TransmissionResource {

    @EJB
    private TransmissionService transmissionService;

    @POST
    public Response create(Transmission transmission) {
        try {
            Transmission created = transmissionService.create(transmission);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Transmission transmission) {
        try {
            transmission.setId(id);
            Transmission updated = transmissionService.update(transmission);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            transmissionService.delete(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Transmission transmission = transmissionService.findById(id);
        if (transmission == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(transmission).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize,
            @QueryParam("search") String search,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") @DefaultValue("asc") String sortOrder) {
        PaginationParams params = new PaginationParams(page, pageSize, search, sortBy, sortOrder);
        return Response.ok(PaginatedResponse.from(transmissionService.findAll(params))).build();
    }

    @GET
    @Path("/available")
    public Response findAvailable() {
        List<Transmission> available = transmissionService.findAvailable();
        return Response.ok(available).build();
    }
}
