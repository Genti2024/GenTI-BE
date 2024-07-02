package com.gt.genti.withdrawrequest.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWithdrawRequest is a Querydsl query type for WithdrawRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWithdrawRequest extends EntityPathBase<WithdrawRequest> {

    private static final long serialVersionUID = 1493740922L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWithdrawRequest withdrawRequest = new QWithdrawRequest("withdrawRequest");

    public final com.gt.genti.common.baseentity.model.QBaseEntity _super;

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final com.gt.genti.user.model.QUser createdBy;

    public final com.gt.genti.creator.model.QCreator creator;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    // inherited
    public final com.gt.genti.user.model.QUser modifiedBy;

    public final EnumPath<WithdrawRequestStatus> status = createEnum("status", WithdrawRequestStatus.class);

    public final NumberPath<Integer> taskCount = createNumber("taskCount", Integer.class);

    public QWithdrawRequest(String variable) {
        this(WithdrawRequest.class, forVariable(variable), INITS);
    }

    public QWithdrawRequest(Path<? extends WithdrawRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWithdrawRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWithdrawRequest(PathMetadata metadata, PathInits inits) {
        this(WithdrawRequest.class, metadata, inits);
    }

    public QWithdrawRequest(Class<? extends WithdrawRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.gt.genti.common.baseentity.model.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.creator = inits.isInitialized("creator") ? new com.gt.genti.creator.model.QCreator(forProperty("creator"), inits.get("creator")) : null;
        this.modifiedAt = _super.modifiedAt;
        this.modifiedBy = _super.modifiedBy;
    }

}

