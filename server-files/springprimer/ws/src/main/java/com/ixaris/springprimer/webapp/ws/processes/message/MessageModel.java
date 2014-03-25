package com.ixaris.springprimer.webapp.ws.processes.message;

import java.util.Date;

import com.ixaris.springprimer.module.bd.busobject.Message;

public class MessageModel extends MessageInputModel {
	
    private Date date;
    
    public MessageModel() { }
    
    public MessageModel(final Message post) {
        setUsername(post.getUsername());
        setContent(post.getContent());
        this.date = post.getDate();
    }

    public Date getDate() {
        return date;
    }

}
