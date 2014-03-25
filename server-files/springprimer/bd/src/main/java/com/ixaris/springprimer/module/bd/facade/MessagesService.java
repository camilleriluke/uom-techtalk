package com.ixaris.springprimer.module.bd.facade;

import java.util.List;
import java.util.Set;

import com.ixaris.springprimer.module.bd.busobject.Message;

/**
 * Message service. All lookups are ordered newest to oldest
 * 
 * @author brian.vella
 */
public interface MessagesService {

	Message addMessage(String username, String content);

	List<? extends Message> lookupMessagesByHashtags(Set<String> hashtags, int offset, int limit);
	
	List<? extends Message> lookupMessages(int offset, int limit);
	
	int countMessagesByUser(String username);
	
	List<? extends Message> lookupMessagesByUser(String username, int offset, int limit);
	
	List<? extends Message> lookupMessagesMentioningUser(String username, int offset, int limit);

}
