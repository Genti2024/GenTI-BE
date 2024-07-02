package com.gt.genti.settlement.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSettlement is a Querydsl query type for Settlement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSettlement extends EntityPathBase<Settlement> {

    private static final long serialVersionUID = 2066900534L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSettlement settlement = new QSettlement("settlement");

    public final com.gt.genti.common.basetimeentity.model.QBaseTimeEntity _super = new com.gt.genti.common.basetimeentity.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> elapsedMinutes = createNumber("elapsedMinutes", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.gt.genti.picturegenerateresponse.model.QPictureGenerateResponse pictureGenerateResponse;

    public final NumberPath<Long> reward = createNumber("reward", Long.class);

    public final com.gt.genti.withdrawrequest.model.QWithdrawRequest withdrawRequest;

    public QSettlement(String variable) {
        this(Settlement.class, forVariable(variable), INITS);
    }

    public QSettlement(Path<? extends Settlement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSettlement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSettlement(PathMetadata metadata, PathInits inits) {
        this(Settlement.class, metadata, inits);
    }

    public QSettlement(Class<? extends Settlement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.pictureGenerateResponse = inits.isInitialized("pictureGenerateResponse") ? new com.gt.genti.picturegenerateresponse.model.QPictureGenerateResponse(forProperty("pictureGenerateResponse"), inits.get("pictureGenerateResponse")) : null;
        this.withdrawRequest = inits.isInitialized("withdrawRequest") ? new com.gt.genti.withdrawrequest.model.QWithdrawRequest(forProperty("withdrawRequest"), inits.get("withdrawRequest")) : null;
    }

}

