package com.rvintonyak.services;

import com.rvintonyak.ejb.SessionBean;
import com.rvintonyak.ejb.SessionManager;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("manager")
@Produces(MediaType.APPLICATION_JSON)
public class EjbSessionManager {

    @EJB
    SessionManager manager;

    @GET
    public Response getAllBeans(){
       JsonArray sessions = manager.getSessions().keySet()
               .stream()
               .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add)
               .build();

        return Response.ok(Json.createArrayBuilder().add(sessions).build()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewSession(JsonObject data) throws NamingException {
        String name = data.getString("name");
        InitialContext context = new InitialContext();
        SessionBean sessionBean = (SessionBean) context.lookup("java:module/SessionBean!com.rvintonyak.ejb.SessionBean");
        String id = UUID.randomUUID().toString();
        sessionBean.setId(id);
        sessionBean.setData(name);
        manager.getSessions().put(id, sessionBean);
        //context
        return Response.ok().build();
    }


    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id){
        SessionBean sessionBean = manager.getSessions().get(id);
        System.out.println(sessionBean.getData());
        sessionBean.remove();
        System.out.println(sessionBean);
        return Response.ok().build();
    }

}
