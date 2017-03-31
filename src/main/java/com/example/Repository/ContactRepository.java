package com.example.Repository;

import com.example.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ryan on 3/31/17.
 */

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>, ContactRepositoryCustom {
}
