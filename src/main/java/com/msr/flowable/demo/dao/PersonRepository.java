package com.msr.flowable.demo.dao;

import com.msr.flowable.demo.common.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/9 16:22
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findByUsername(String username);
}
