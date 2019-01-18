package io.github.thang86.repository;


import io.github.thang86.entities.User;
import io.github.thang86.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
*  UserRepository.java
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
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findOneByEmail(String email);
	Optional<User> findOneByUsername(String username);
	List<User> findByUsername(String username);
	List<User> findAllByRolesInAndAdmin_Superior_Id(List<Role> roles, Long id);

}
