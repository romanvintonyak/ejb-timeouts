package com.rvintonyak.ejb;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Startup
public class SessionManager {

    private Map<String, SessionBean> sessions = new HashMap<>();

    public Map<String, SessionBean> getSessions() {
        return sessions;
    }

    public void setSessions(Map<String, SessionBean> sessions) {
        this.sessions = sessions;
    }
}
