package com.rvintonyak.ejb;
import javax.annotation.PreDestroy;
import javax.ejb.*;
import java.util.concurrent.TimeUnit;

@Stateful
@StatefulTimeout(value = 1, unit = TimeUnit.MINUTES)
public class SessionBean {

    private String id;

    private String data;

    @EJB
    SessionManager sessionManager;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @PrePassivate
    public void prePassivate(){
        System.out.println("bean with id " + data + " IS BEING passivated");
    }

    @PostActivate
    public void postActivate(){
        System.out.println("bean with id " + data + " IS BEING activated");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("bean with id " + id + " IS BEING removed");
        sessionManager.getSessions().remove(this.getId());
    }

    @Remove
    public void remove(){
        System.out.println("bean with name " + id + " WAS successfully removed");
    }
}
