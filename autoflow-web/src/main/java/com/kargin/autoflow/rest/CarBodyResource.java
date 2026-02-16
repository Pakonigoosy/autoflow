package com.kargin.autoflow.rest;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.CarBody;
import com.kargin.autoflow.rest.dto.CarBodyDto;
import com.kargin.autoflow.rest.dto.PaginatedResponse;
import com.kargin.autoflow.rest.dto.RestDtoMapper;
import com.kargin.autoflow.service.CarBodyService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/carbodies")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CarBodyResource {

    @EJB
    private CarBodyService carBodyService;

    @POST
    public Response create(CarBodyDto dto) {
        try {
            CarBody entity = RestDtoMapper.toCarBody(dto);
            CarBody created = carBodyService.create(entity);
            return Response.status(Response.Status.CREATED).entity(RestDtoMapper.toDto(created)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CarBodyDto dto) {
        try {
            dto.setId(id);
            CarBody entity = RestDtoMapper.toCarBody(dto);
            CarBody updated = carBodyService.update(entity);
            return Response.ok(RestDtoMapper.toDto(updated)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        try {
            carBodyService.delete(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        CarBody carBody = carBodyService.findById(id);
        if (carBody == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(RestDtoMapper.toDto(carBody)).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize,
            @QueryParam("search") String search,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") @DefaultValue("asc") String sortOrder) {
        PaginationParams params = new PaginationParams(page, pageSize, search, sortBy, sortOrder);
        return Response.ok(PaginatedResponse.from(carBodyService.findAll(params), RestDtoMapper::toDto)).build();
    }

    @GET
    @Path("/available")
    public Response findAvailable() {
        List<CarBody> available = carBodyService.findAvailable();
        return Response.ok(RestDtoMapper.toCarBodyDtoList(available)).build();
    }
}
