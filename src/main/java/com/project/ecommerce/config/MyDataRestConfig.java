package com.project.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.project.ecommerce.entity.Country;
import com.project.ecommerce.entity.Order;
import com.project.ecommerce.entity.Product;
import com.project.ecommerce.entity.ProductCategory;
import com.project.ecommerce.entity.State;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
	
	@Value("${allowed.origins}")
	private String[] theAllowedOrigins;
	
	private EntityManager entityManager;
	
	@Autowired
	public MyDataRestConfig(EntityManager theEntityManger) {
		this.entityManager=theEntityManger;
	}
	
	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		
		HttpMethod[] theUnsupportedActions= {HttpMethod.PUT,HttpMethod.DELETE,HttpMethod.POST,HttpMethod.PATCH};
		Class[] classArray = {Product.class,ProductCategory.class,Country.class,State.class,Order.class};
		
		//disbale the method for product:PUT,POST,DELETE
		for(Class tempClass: classArray) {
			disableHttpMethods(tempClass,config, theUnsupportedActions);
		}
		
		//call internal helper method to expose the id's
		exposeIds(config);
		//configure the corss mapping 
		cors.addMapping(config.getBasePath()+"/**").allowedOrigins(theAllowedOrigins);
	}

	private void disableHttpMethods(Class theClass,RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
		config.getExposureConfiguration()
		.forDomainType(theClass)
		.withItemExposure((metdata,httpMethods)-> httpMethods.disable(theUnsupportedActions))
		.withCollectionExposure((metdata,httpMethods)-> httpMethods.disable(theUnsupportedActions));
	}
	
	private void exposeIds(RepositoryRestConfiguration config) {

        // expose entity ids
        //

        // - get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // - create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the entity types for the entities
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
