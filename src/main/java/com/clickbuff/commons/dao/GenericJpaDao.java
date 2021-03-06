package com.clickbuff.commons.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.clickbuff.commons.doamin.BaseEntity;

/**
 * Provides generic common implementation of GenericDao interface persistence
 * methods. Extend this abstract class to implement DAO for your specific needs.
 * 
 * @param <T>
 *            the generic type
 * @param <ID>
 *            the generic type
 * @author Vikrant.chand
 */
public abstract class GenericJpaDao<T, ID extends Serializable> implements
		GenericDao<T, ID> {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = Logger.getLogger(GenericJpaDao.class);

	/** The persistent class. */
	private Class<T> persistentClass;

	/** The entity manager. */
	private EntityManager entityManager;

	/**
	 * Instantiates a new generic jpa dao.
	 * 
	 * @param persistentClass
	 *            the persistent class
	 */
	public GenericJpaDao(Class<T> persistentClass) {

		LOGGER.debug("Setting the persistent unit type into GENERIC JPA DAO");

		this.persistentClass = persistentClass;

	}

	/**
	 * Gets the entity manager.
	 * 
	 * @return the entity manager
	 */
	protected EntityManager getEntityManager() {

		LOGGER.debug("getting Entity Manager");

		return entityManager;

	}

	/**
	 * Sets the entity manager.
	 * 
	 * @param entityManager
	 *            the new entity manager
	 */
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	public void setEntityManager(EntityManager entityManager) {

		LOGGER.debug("Creating new Entity Manager");

		this.entityManager = entityManager;

	}

	/**
	 * Gets the persistent class.
	 * 
	 * @return the persistent class
	 */
	public Class<T> getPersistentClass() {

		LOGGER.debug("Getting the persistent Class");

		return persistentClass;
	}

	/**
	 * 
	 * find the the entity by ID
	 * 
	 * @return entity
	 */
	public T findById(ID id) {

		LOGGER.debug("Finding the Entity by for ID: "
				+ getPersistentClass().getSimpleName());

		T entity = (T) getEntityManager().find(getPersistentClass(), id);

		return entity;
	}

	/**
	 * find All entities of persistent Entity
	 * 
	 * @return List<PersistentEntity>
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {

		LOGGER.debug("Finding all Entites of type "
				+ getPersistentClass().getSimpleName());

		return getEntityManager().createQuery(
				"select x from " + getPersistentClass().getSimpleName() + " x")
				.getResultList();

	}

	/**
	 * save the persistent entity
	 * 
	 * @return PersistentEntity
	 */

	public T save(T entity) {

		LOGGER.debug("Saving new Entity" + entity.getClass().getSimpleName());

		getEntityManager().persist(entity);

		LOGGER.debug("Entity Saved" + entity.getClass().getSimpleName());

		return entity;
	}

	/**
	 * update the existing Entity
	 * 
	 * @return Persistent Entity
	 */
	public T update(T entity) {

		LOGGER.debug("updating Entity" + entity.getClass().getSimpleName());

		T mergedEntity = getEntityManager().merge(entity);

		LOGGER.debug("Entity Updated " + entity.getClass().getSimpleName());

		return mergedEntity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see training.impetus.tp.commons.dao.GenericDao#delete(java.lang.Object)
	 */
	public void delete(T entity) {

		LOGGER.debug("Deleting Entity " + entity.getClass().getSimpleName());

		if (BaseEntity.class.isAssignableFrom(persistentClass)) {

			getEntityManager().remove(
					getEntityManager().getReference(entity.getClass(),
							((BaseEntity) entity).getId()));

		} else {

			entity = getEntityManager().merge(entity);

			getEntityManager().remove(entity);
		}

		LOGGER.debug("Deleted Entity " + entity.getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see training.impetus.tp.commons.dao.GenericDao#flush()
	 */
	public void flush() {

		LOGGER.debug("Flushing EntityManage");

		getEntityManager().flush();

		LOGGER.debug("EntityManage Flushed");
	}
}
