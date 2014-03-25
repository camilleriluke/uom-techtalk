package com.ixaris.springprimer.module.impl.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ixaris.springprimer.module.bd.busobject.Message;
import com.ixaris.springprimer.module.bd.facade.MessagesService;
import com.ixaris.springprimer.module.impl.data.MessageImpl;
import com.ixaris.springprimer.module.impl.data.MessageImpl_;

@Service
@Transactional
public class MessagesServiceImpl implements MessagesService {

    private static final Pattern MENTION_PATTERN = Pattern.compile("@(\\w+|\\W+)");
    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#(\\w+|\\W+)");
    
	@PersistenceContext
	private EntityManager em;

	@Override
	public MessageImpl addMessage(final String username, final String content) {

	    if ((content == null) || (content.length() > Message.CONTENT_MAX_LENGTH)) {
	        throw new IllegalArgumentException("content");
	    }
	    if ((username == null) || (username.length() > Message.USERNAME_MAX_LENGTH)) {
	        throw new IllegalArgumentException("username");
	    }
	    
	    // extract mentions
	    final Set<String> mentions = new HashSet<>();
	    final Matcher mentionsMatcher = MENTION_PATTERN.matcher(content);
	    while (mentionsMatcher.find()) {
	      mentions.add(mentionsMatcher.group(1));
	    }

	    final Set<String> hashtags = new HashSet<>();
        final Matcher hashtagMatcher = HASHTAG_PATTERN.matcher(content);
        while (hashtagMatcher.find()) {
          hashtags.add(hashtagMatcher.group(1));
        }
	        
		final MessageImpl message = new MessageImpl(content, username, mentions, hashtags);
		em.persist(message);
		return message;
	}

    @Override
    public List<? extends Message> lookupMessagesByHashtags(final Set<String> hashtags, final int offset, final int limit) {
        
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<MessageImpl> q = cb.createQuery(MessageImpl.class);
        final Root<MessageImpl> from = q.from(MessageImpl.class);
        final List<Predicate> predicates = new ArrayList<>();
        for (String hashtag : hashtags) {
            final SetJoin<MessageImpl, String> join = from.join(MessageImpl_.hashtags);
            predicates.add(cb.equal(join, hashtag));
        }
        q.where(predicates.toArray(new Predicate[predicates.size()]));
        return em.createQuery(q).getResultList();
    }

    @Override
    public List<? extends Message> lookupMessages(final int offset, final int limit) {
        final TypedQuery<MessageImpl> q = em.createNamedQuery("MessageImpl.all", MessageImpl.class);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return q.getResultList();
    }

    @Override
    public int countMessagesByUser(final String username) {
        final TypedQuery<Long> q = em.createNamedQuery("MessageImpl.countByUsername", Long.class);
        q.setParameter("username", username);
        return (int)q.getSingleResult().longValue();
    }
    
    @Override
    public List<MessageImpl> lookupMessagesByUser(final String username, final int offset, final int limit) {
        final TypedQuery<MessageImpl> q = em.createNamedQuery("MessageImpl.byUsername", MessageImpl.class);
        q.setParameter("username", username);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return q.getResultList();
    }

    @Override
    public List<? extends Message> lookupMessagesMentioningUser(final String username, final int offset, final int limit) {
        final TypedQuery<MessageImpl> q = em.createNamedQuery("MessageImpl.mentioningUsername", MessageImpl.class);
        q.setParameter("username", username);
        q.setFirstResult(offset);
        q.setMaxResults(limit);
        return q.getResultList();
    }

}
