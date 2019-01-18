package io.github.thang86.repository;


import io.github.thang86.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
*  CompanyRepository.java
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
public interface CompanyRepository extends JpaRepository<Company, Long> {
    /**
     * Get Company by Name
     * @param name
     * @return  Company
     */
    Optional<Company> findOneByName(String name);

    /**
     *  Get Company by Id
     * @param id
     * @return Company
     */
    Optional<Company> findOneById(Integer id);

    /**
     *
     * Get all company list
     * @return  Company list
     */

    List<Company> findAll();

}
