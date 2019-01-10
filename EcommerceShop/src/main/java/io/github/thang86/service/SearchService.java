package io.github.thang86.service;

import io.github.thang86.common.SearchResult;
import io.github.thang86.entities.Store;
import io.github.thang86.entities.StoreProduct;

import java.util.List;

/**
*  SearchService.java
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

public interface SearchService {
    SearchResult generalSearch(String queryString);
    SearchResult autoCompleteSearch(String queryString);
    List<StoreProduct> storeProductSearch(String queryString, Integer maxTimeLimit);
    List<Store> storeSearch(String queryString, Integer maxTimeLimit);
}
