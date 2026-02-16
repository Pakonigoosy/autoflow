package com.kargin.autoflow.rest;

import com.kargin.autoflow.dto.PaginationParams;
import com.kargin.autoflow.entity.Transmission;
import com.kargin.autoflow.rest.dto.PaginatedResponse;
import com.kargin.autoflow.rest.dto.RestDtoMapper;
import com.kargin.autoflow.rest.dto.TransmissionDto;
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
    public Response create(TransmissionDto dto) {
        try {
            Transmission entity = RestDtoMapper.toTransmission(dto);
            Transmission created = transmissionService.create(entity);
            return Response.status(Response.Status.CREATED).entity(RestDtoMapper.toDto(created)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, TransmissionDto dto) {
        try {
            dto.setId(id);
            Transmission entity = RestDtoMapper.toTransmission(dto);
            Transmission updated = transmissionService.update(entity);
            return Response.ok(RestDtoMapper.toDto(updated)).build();
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
        return Response.ok(RestDtoMapper.toDto(transmission)).build();
    }

    @GET
    public Response findAll(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize,
            @QueryParam("search") String search,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("sortOrder") @DefaultValue("asc") String sortOrder) {
        PaginationParams params = new PaginationParams(page, pageSize, search, sortBy, sortOrder);
        return Response.ok(PaginatedResponse.from(transmissionService.findAll(params), RestDtoMapper::toDto)).build();
    }

    @GET
    @Path("/available")
    public Response findAvailable() {
        List<Transmission> available = transmissionService.findAvailable();
        return Response.ok(RestDtoMapper.toTransmissionDtoList(available)).build();
    }
}
