package com.example.dao;

import java.util.List;

import com.example.domain.Type;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class TypeDAOHibernate {

	private static final Log LOG = LogFactory.getLog(TypeDAOHibernate.class);

	@Autowired
	private SessionFactory sessionFactory;


	@Transactional
	public List<Type> findAll() {
		return sessionFactory.getCurrentSession().createQuery("from Type").list();
	}
}