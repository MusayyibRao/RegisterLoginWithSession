package com.registerlogin.dao;

import com.registerlogin.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends JpaRepository<Register,Long> {

    @Query("select r from Register r where r.username=?1")
     Register findByUsername(String userName);

}
