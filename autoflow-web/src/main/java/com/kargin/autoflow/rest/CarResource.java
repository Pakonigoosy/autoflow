package com.kargin.autoflow.rest;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.Car;
import com.kargin.autoflow.rest.dto.PaginatedResponse;
import com.kargin.autoflow.rest.dto.RestDtoMapper;
import com.kargin.autoflow.service.CarService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/cars")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class CarResource {

    @EJB
    private CarService carService;

    @POST
    @Path("/assemble")
    public Response assembleCar(
            @QueryParam("bodyId") Long bodyId,
            @QueryParam("engineId") Long engineId,
            @QueryParam("transmissionId") Long transmissionId) {
        try {
            Car car = carService.assembleCar(bodyId, engineId, transmissionId);
            return Response.status(Response.Status.CREATED).entity(RestDtoMapper.toDto(car)).build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    @POST
    @Path("/assemble/json")
    public Response assembleCarJson(Map<String, Long> components) {
        try {
            Long bodyId = components.get("bodyId");
            Long engineId = components.get("engineId");
            Long transmissionId = components.get("transmissionId");
            Car car = carService.assembleCar(bodyId, engineId, transmissionId);
            return Response.status(Response.Status.CREATED).entity(RestDtoMapper.toDto(car)).build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    @DELETE
    @Path("/{id}/disassemble")
    public Response disassembleCar(@PathParam("id") Long id) {
        try {
            carService.disassembleCar(id);
            return Response.noContent().build();
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Car car = carService.findById(id);
        if (car == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(RestDtoMapper.toDto(car)).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize,
            @QueryParam("search") String search,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") @DefaultValue("asc") String sortOrder) {
        PaginationParams params = new PaginationParams(page, pageSize, search, sortBy, sortOrder);
        return Response.ok(PaginatedResponse.from(carService.findAll(params), RestDtoMapper::toDto)).build();
    }
}
