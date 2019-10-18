package com.bogdan.api;

import com.bogdan.dao.ImageDao;
import com.bogdan.model.Image;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.List;

@Path("/")
public class ImageApi {

    private ImageDao imageDao = new ImageDao();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addImage(@FormParam("name") String name, @FormParam("base") String base) {
        Image image = new Image(name, base);
        String json = gson.toJson(imageDao.addImage(image));
        return Response
                .status(Response.Status.OK)
                .entity("Image added with ID: " + json)
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAllImages() {
        List images = imageDao.getAllImages();
        String json = gson.toJson(images);
        return Response
                .status(Response.Status.OK)
                .entity(json)
                .build();
    }

    @POST
    @Path("/image")
    @Produces("image/jpg")
    public Response getImage(@FormParam("id") long id) {
        byte[] arr;
        try {
            arr = Base64.getDecoder().decode(imageDao.getImage(id).toString());

            if (id == 0) {
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();
            }
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .build();
        } return Response.ok(new ByteArrayInputStream(arr)).build();
    }
}
