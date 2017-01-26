package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.dao.TypeDAOHibernate;
import com.example.domain.Type;
import com.example.repository.TypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StressController {

	private static Logger LOG = LoggerFactory.getLogger(StressController.class);

	@Autowired
	TypeDAOHibernate typeDAOHibernate;

	@Autowired
	TypeRepository typeRepository;


	@RequestMapping(value = "/stress-sessionfactory")
	public void stressTest(@RequestParam int threadsCount) {
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < threadsCount; i++) {
			final int k = i;
			Runnable runnable
					= () -> {

				List<Type> all = typeDAOHibernate.findAll();
				LOG.info("{}:sessionfactory:{} ", k, all.size());
			};
			Thread t = new Thread(runnable);
			threads.add(t);
		}
		threads.stream().forEach(t -> t.start());
	}

	@RequestMapping(value = "/stress-jpa")
	public void stressTest2(@RequestParam int threadsCount) {
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < threadsCount; i++) {
			final int k = i;
			Runnable runnable
					= () -> {

				List<Type> all = typeRepository.findAll();
				LOG.info("{}:jpa:{} ", k, all.size());
			};
			Thread t = new Thread(runnable);
			threads.add(t);
		}
		threads.stream().forEach(t -> t.start());
	}

}
