package com.example.Repository;

import com.example.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ryan on 3/31/17.
 */

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

}
