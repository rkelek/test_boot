package com.test.project.test;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.project.jpa.table.GoodsStock;



@Repository
@Transactional
public class TestRepositoryImpl extends QuerydslRepositorySupport implements TestCustomRepository {

	private final JPAQueryFactory query;

	public TestRepositoryImpl(JPAQueryFactory queryFactory) {
		 super(GoodsStock.class);
		 this.query = queryFactory;
	}



	
	
	

}