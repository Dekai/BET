package com.dk.alirr.repository;

import com.dk.alirr.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.stream.DoubleStream;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("select u from User u inner join u.cars c where c.brand = :carBrand")
    Page<User> findUsersbyCarBrand(@Param("carBrand") String carBrand, Pageable page);

    @Override
    @EntityGraph(value = "graph.user-with-cars-and-drivers")
    Page<User> findAll(Specification<User> spec, Pageable pageable);

    Optional<User> findOneByEmailIgnoreCase(String userEmail);

    Optional<User> findOneByNameIgnoreCase(String userName);
}
