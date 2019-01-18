package io.github.thang86.serviceimpl;


import io.github.thang86.common.SearchResult;
import io.github.thang86.entities.Store;
import io.github.thang86.entities.StoreProduct;
import io.github.thang86.service.SearchService;
import org.apache.lucene.search.Query;
import org.hibernate.search.errors.EmptyQueryException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.min;
import static org.hibernate.search.jpa.Search.getFullTextEntityManager;

/**
*  SearchServiceImpl.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-20    ThangTX     Create
*/

@Service
public class SearchServiceImpl implements SearchService {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public SearchResult generalSearch(String queryString) {
		return new SearchResult(queryString, storeProductSearch(queryString, 500), storeSearch(queryString, 300));
	}

	@Override
	public SearchResult autoCompleteSearch(String queryString) {

			List<StoreProduct> storeProducts = storeProductSearch(queryString, 150);
			List<Store> stores = storeSearch(queryString, 150);

		
			return new SearchResult(queryString,
				storeProducts.subList(0, min(10, storeProducts.size())),
				stores.subList(0, min(10, stores.size()))
		);
	}

	@Override
	public List<StoreProduct> storeProductSearch(String queryString, Integer maxTimeLimit) {
		FullTextEntityManager fullTextEntityManager = getFullTextEntityManager(entityManager);

		QueryBuilder queryBuilder =
				fullTextEntityManager.getSearchFactory()
						.buildQueryBuilder().forEntity(StoreProduct.class).get();

		Query query;
		try {
			query = queryBuilder
					.keyword()
					.fuzzy()
					.withThreshold(0.4f) 
					.withPrefixLength(1)
					.onFields("name", "description", "product.name")
					.boostedTo(5)   
					.andField("product.brand.name")
					.andField("product.company.name")
					.andField("store.name")
					.matching(queryString)
					.createQuery();
		} catch (EmptyQueryException exception) {
			
			return new ArrayList<>();
		}


		FullTextQuery jpaQuery =
				fullTextEntityManager.createFullTextQuery(query, StoreProduct.class);

		jpaQuery.limitExecutionTimeTo(maxTimeLimit, TimeUnit.MILLISECONDS);


		List<StoreProduct> results = jpaQuery.getResultList();

		return results.subList(0, min(results.size(),50));
	}

	@Override
	public List<Store> storeSearch(String queryString, Integer maxTimeLimit) {
		FullTextEntityManager fullTextEntityManager = getFullTextEntityManager(entityManager);

		QueryBuilder queryBuilder =
				fullTextEntityManager.getSearchFactory()
						.buildQueryBuilder().forEntity(Store.class).get();

		Query query;
		try {
			query = queryBuilder
					.keyword()
					.fuzzy()
					.withPrefixLength(1)
					.onFields("name")
					.matching(queryString)
					.createQuery();
		} catch (EmptyQueryException exception) {
			
			return new ArrayList<>();
		}

		FullTextQuery jpaQuery =
				fullTextEntityManager.createFullTextQuery(query, Store.class);

		jpaQuery.limitExecutionTimeTo(maxTimeLimit, TimeUnit.MILLISECONDS);


		List results = jpaQuery.getResultList();

		return results.subList(0, min(results.size(),50));	}
}
