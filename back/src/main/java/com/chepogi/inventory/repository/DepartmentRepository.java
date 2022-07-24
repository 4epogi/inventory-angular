package com.chepogi.inventory.repository;

import com.chepogi.inventory.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {
    @Modifying
    @Query(value = "DELETE FROM Department dep where dep.id = :id")
    Integer deleteDepartmentById(@Param("id")Long id);

    Page findByNameContaining(String name, Pageable pageable);

    List<Department> findAll(Sort sort);

    Optional<Department> findByNameIgnoreCase(String name);
}
