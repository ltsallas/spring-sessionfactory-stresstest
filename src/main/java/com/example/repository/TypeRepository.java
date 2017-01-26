package com.example.repository;

import java.util.List;

import com.example.domain.Type;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TypeRepository extends PagingAndSortingRepository<Type,Long> {
	List<Type> findAll();

}
