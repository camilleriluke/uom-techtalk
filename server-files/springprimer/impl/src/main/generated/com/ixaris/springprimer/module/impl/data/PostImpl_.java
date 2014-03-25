package com.ixaris.springprimer.module.impl.data;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MessageImpl.class)
public abstract class PostImpl_ {

	public static volatile SingularAttribute<MessageImpl, Long> timestamp;
	public static volatile SingularAttribute<MessageImpl, String> content;
	public static volatile SingularAttribute<MessageImpl, String> username;
	public static volatile SetAttribute<MessageImpl, String> hashtags;
	public static volatile SetAttribute<MessageImpl, String> mentions;

}

