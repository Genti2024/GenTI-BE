package com.gt.genti.creator.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCreator is a Querydsl query type for Creator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCreator extends EntityPathBase<Creator> {

    private static final long serialVersionUID = 1998200104L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCreator creator = new QCreator("creator");

    public final com.gt.genti.common.basetimeentity.model.QBaseTimeEntity _super = new com.gt.genti.common.basetimeentity.model.QBaseTimeEntity(this);

    public final StringPath accountHolder = createString("accountHolder");

    public final StringPath accountNumber = createString("accountNumber");

    public final EnumPath<BankType> bankType = createEnum("bankType", BankType.class);

    public final NumberPath<Integer> completedTaskCount = createNumber("completedTaskCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest, com.gt.genti.picturegeneraterequest.model.QPictureGenerateRequest> pictureGenerateRequest = this.<com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest, com.gt.genti.picturegeneraterequest.model.QPictureGenerateRequest>createList("pictureGenerateRequest", com.gt.genti.picturegeneraterequest.model.PictureGenerateRequest.class, com.gt.genti.picturegeneraterequest.model.QPictureGenerateRequest.class, PathInits.DIRECT2);

    public final com.gt.genti.user.model.QUser user;

    public final BooleanPath workable = createBoolean("workable");

    public QCreator(String variable) {
        this(Creator.class, forVariable(variable), INITS);
    }

    public QCreator(Path<? extends Creator> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCreator(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCreator(PathMetadata metadata, PathInits inits) {
        this(Creator.class, metadata, inits);
    }

    public QCreator(Class<? extends Creator> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.gt.genti.user.model.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

