package com.ixaris.springprimer.module.impl.data;

import java.util.Date;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ixaris.springprimer.module.bd.busobject.Message;

@Entity
@Table(name = "sp_message")
@NamedQueries({
    @NamedQuery(name = "MessageImpl.all",
                query = "SELECT p FROM MessageImpl p ORDER BY p.timestamp DESC"), 
    @NamedQuery(name = "MessageImpl.countByUsername",
                query = "SELECT COUNT(p) FROM MessageImpl p WHERE p.username=:username"),    
    @NamedQuery(name = "MessageImpl.byUsername",
                query = "SELECT p FROM MessageImpl p WHERE p.username=:username ORDER BY p.timestamp DESC"),    
    @NamedQuery(name = "MessageImpl.mentioningUsername",
                query = "SELECT p FROM MessageImpl p JOIN p.mentions m WHERE m=:username ORDER BY p.timestamp DESC")    
})
public class MessageImpl implements Message {

    private static long lastTime = 0L;
    
    // advance clock for id generation. Time will eventually catch up
    private synchronized static long getNextId() {
        long currentTime = System.currentTimeMillis();
        if (lastTime < currentTime) {
            lastTime = currentTime;
        } else {
            lastTime++;
        }
        return lastTime;
    }
    
	@Id
	private long timestamp;
	
	@Column(nullable = false)
	private String content;
	
	@Column(nullable = false)
    private String username;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "sp_mention", 
                     joinColumns = @JoinColumn(name = "messageTimestamp", referencedColumnName = "timestamp"))
	@Column(name = "mentionedUsername")
    private Set<String> mentions;
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "sp_hashtag", 
                     joinColumns = @JoinColumn(name = "messageTimestamp", referencedColumnName = "timestamp"))
    @Column(name = "hashtag")
    private Set<String> hashtags;
	
	protected MessageImpl() { }

	public MessageImpl(final String content, final String username, final Set<String> mentions, final Set<String> hashtags) {
	    
        timestamp = getNextId();
		this.content = content;
		this.username = username;
		this.mentions = mentions;
		this.hashtags = hashtags;
	}

	@Override
	public Date getDate() {
	    return new Date(timestamp);
	}
	
	@Override
	public String getContent() {
		return content;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String toString() {
		return content + " by " + username + " on " + timestamp;
	}

	@Override
    public int hashCode() {
	    return (int)(timestamp ^ (timestamp >>> 32));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (!(o instanceof MessageImpl)) {
            return false;
        } else {
            MessageImpl other = (MessageImpl)o;
            return timestamp == other.timestamp;
        }
    }
    
}
