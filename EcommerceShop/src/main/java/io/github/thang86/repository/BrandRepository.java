package io.github.thang86.repository;


import io.github.thang86.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
*  BrandRepository.java
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

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    /**
     * Get Brand by Name
     * @param name
     * @return  Brand
     */

    Optional<Brand> findOneByName(String name);

    /**
     * Get Brand by Id
     * @param id
     * @return  Brand
     */

    Optional<Brand> findOneById(Integer id);

    /**
     * Get all company list
     * @return  company list
     */
    List<Brand> findAll();

}
