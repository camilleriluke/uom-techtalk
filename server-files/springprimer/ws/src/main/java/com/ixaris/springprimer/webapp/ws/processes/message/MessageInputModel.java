package com.ixaris.springprimer.webapp.ws.processes.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ixaris.springprimer.module.bd.busobject.Message;

public class MessageInputModel {
	
    @NotNull
    @Size(min = 2, max = Message.USERNAME_MAX_LENGTH)
    private String username;
    
    @NotNull
    @Size(min = 5, max = Message.CONTENT_MAX_LENGTH)
    private String content;
    
    public MessageInputModel() { }
    
    public final String getUsername() {
        return username;
    }
    
    public final void setUsername(final String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
