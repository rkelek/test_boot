package com.test.project.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.project.jpa.table.GoodsStock;

@Repository
public interface TestRepository extends JpaRepository<GoodsStock, Integer> {
   
}