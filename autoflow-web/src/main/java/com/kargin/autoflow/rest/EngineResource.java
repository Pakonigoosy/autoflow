package com.kargin.autoflow.rest;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.Engine;
import com.kargin.autoflow.rest.dto.PaginatedResponse;
import com.kargin.autoflow.service.EngineService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/engines")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class EngineResource {

    @EJB
    private EngineService engineService;

    @POST
    public Response create(Engine engine) {
        try {
            Engine created = engineService.create(engine);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Engine engine) {
        try {
            engine.setId(id);
            Engine updated = engineService.update(engine);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            engineService.delete(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Engine engine = engineService.findById(id);
        if (engine == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(engine).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize,
            @QueryParam("search") String search,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") @DefaultValue("asc") String sortOrder) {
        PaginationParams params = new PaginationParams(page, pageSize, search, sortBy, sortOrder);
        return Response.ok(PaginatedResponse.from(engineService.findAll(params))).build();
    }

    @GET
    @Path("/available")
    public Response findAvailable() {
        List<Engine> available = engineService.findAvailable();
        return Response.ok(available).build();
    }
}
