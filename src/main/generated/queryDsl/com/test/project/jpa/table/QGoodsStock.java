package com.test.project.jpa.table;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGoodsStock is a Querydsl query type for GoodsStock
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGoodsStock extends EntityPathBase<GoodsStock> {

    private static final long serialVersionUID = -276834377L;

    public static final QGoodsStock goodsStock = new QGoodsStock("goodsStock");

    public final NumberPath<Integer> cnt = createNumber("cnt", Integer.class);

    public final DateTimePath<java.util.Date> credt = createDateTime("credt", java.util.Date.class);

    public final StringPath delyn = createString("delyn");

    public final StringPath dt = createString("dt");

    public final NumberPath<Integer> gdmgrno = createNumber("gdmgrno", Integer.class);

    public final NumberPath<Integer> seq = createNumber("seq", Integer.class);

    public QGoodsStock(String variable) {
        super(GoodsStock.class, forVariable(variable));
    }

    public QGoodsStock(Path<? extends GoodsStock> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGoodsStock(PathMetadata metadata) {
        super(GoodsStock.class, metadata);
    }

}

