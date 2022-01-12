package com.test.project.jpa.table;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "ar_goods_stock")
public class GoodsStock {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer seq;
    private Integer gdmgrno;
    private int cnt;
    
    private String dt;    
    
    @Column(insertable=false)   
    private Date credt;
    
    @Column(insertable=false)   
    private String delyn; 

}