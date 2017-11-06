package com.rvintonyak.ejb;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import java.util.concurrent.TimeUnit;

@Stateful
@StatefulTimeout(value = 30, unit = TimeUnit.SECONDS)
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

    @PreDestroy
    public void preDestroy(){
        System.out.println("bean with name " + data + " IS BEING removed");
        sessionManager.getSessions().remove(this.getId());
    }

    @Remove
    public void remove(){
        System.out.println("bean with name " + data + " WAS successfully removed");
        System.out.println(sessionManager.getSessions());

    }
}
