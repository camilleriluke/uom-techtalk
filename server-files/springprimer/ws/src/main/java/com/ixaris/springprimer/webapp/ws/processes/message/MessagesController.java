package com.ixaris.springprimer.webapp.ws.processes.message;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ixaris.springprimer.module.bd.busobject.Message;
import com.ixaris.springprimer.module.bd.facade.MessagesService;

@Controller
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @RequestMapping(method = POST)
    @ResponseBody
    public void addPost(@RequestBody @Valid final MessageInputModel model) {

        messagesService.addMessage(model.getUsername(), model.getContent());

    }

    @RequestMapping(method = GET)
    @ResponseBody
    public List<MessageModel> lookup(@RequestParam(value = "offset", defaultValue = "0") final int offset,
                                     @RequestParam(value = "limit", defaultValue = "25") final int limit) {

        List<? extends Message> messages = messagesService.lookupMessages(offset, limit);

        final List<MessageModel> result = new ArrayList<>();
        for (Message message : messages) {
            result.add(new MessageModel(message));
        }

        return result;
    }

    @RequestMapping(value = "/hashtags/{tags}", method = GET)
    @ResponseBody
    public List<MessageModel> lookupByTags(@PathVariable final String[] tags,
                                           @RequestParam(value = "offset", defaultValue = "0") final int offset,
                                           @RequestParam(value = "limit", defaultValue = "25") final int limit) {

        final Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(tags));
        List<? extends Message> messages = messagesService.lookupMessagesByHashtags(set, offset, limit);

        final List<MessageModel> result = new ArrayList<>();
        for (Message message : messages) {
            result.add(new MessageModel(message));
        }

        return result;
    }
    
    @RequestMapping(value = "/mention/{user}", method = GET)
    @ResponseBody
    public List<MessageModel> lookupByMention(@PathVariable final String user,
                                              @RequestParam(value = "offset", defaultValue = "0") final int offset,
                                              @RequestParam(value = "limit", defaultValue = "25") final int limit) {

        List<? extends Message> messages = messagesService.lookupMessagesMentioningUser(user, offset, limit);

        final List<MessageModel> result = new ArrayList<>();
        for (Message message : messages) {
            result.add(new MessageModel(message));
        }

        return result;
    }
    
    @RequestMapping(value = "/user/{user}", method = GET)
    @ResponseBody
    public List<MessageModel> lookupByUser(@PathVariable final String user,
                                           @RequestParam(value = "offset", defaultValue = "0") final int offset,
                                           @RequestParam(value = "limit", defaultValue = "25") final int limit) {

        List<? extends Message> messages = messagesService.lookupMessagesByUser(user, offset, limit);

        final List<MessageModel> result = new ArrayList<>();
        for (Message message : messages) {
            result.add(new MessageModel(message));
        }

        return result;
    }

}
