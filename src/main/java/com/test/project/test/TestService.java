package com.test.project.test;

import org.springframework.stereotype.Service;

import com.test.project.jpa.table.GoodsStock;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TestService {
private final TestRepository tRepository;

private final TestCustomRepository tCRepository;
    
 
	public void getTest(){
		for(int i = 0; i < 11; i++){
			GoodsStock stock = new GoodsStock();
			stock.setCnt(10);
			tRepository.save(stock);
		}
	}
    
}