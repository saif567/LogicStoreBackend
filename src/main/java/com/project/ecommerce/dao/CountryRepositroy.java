package com.project.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.project.ecommerce.entity.Country;

@RepositoryRestResource(collectionResourceRel = "countries",path = "countries")
public interface CountryRepositroy extends JpaRepository<Country, Integer> {
	
}
