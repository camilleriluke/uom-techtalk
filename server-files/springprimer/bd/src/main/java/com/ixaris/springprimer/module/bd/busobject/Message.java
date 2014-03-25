package com.ixaris.springprimer.module.bd.busobject;

import java.util.Date;


public interface Message {

    final int CONTENT_MAX_LENGTH = 250;
    
    final int USERNAME_MAX_LENGTH = 50;
    
	String getContent();
	
	String getUsername();
	
	Date getDate();
	
}
