package com.gt.genti.deposit.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeposit is a Querydsl query type for Deposit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeposit extends EntityPathBase<Deposit> {

    private static final long serialVersionUID = -1883104628L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeposit deposit = new QDeposit("deposit");

    public final com.gt.genti.common.basetimeentity.model.QBaseTimeEntity _super = new com.gt.genti.common.basetimeentity.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> nowAmount = createNumber("nowAmount", Long.class);

    public final NumberPath<Long> totalAmount = createNumber("totalAmount", Long.class);

    public final com.gt.genti.user.model.QUser user;

    public QDeposit(String variable) {
        this(Deposit.class, forVariable(variable), INITS);
    }

    public QDeposit(Path<? extends Deposit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeposit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeposit(PathMetadata metadata, PathInits inits) {
        this(Deposit.class, metadata, inits);
    }

    public QDeposit(Class<? extends Deposit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.gt.genti.user.model.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

