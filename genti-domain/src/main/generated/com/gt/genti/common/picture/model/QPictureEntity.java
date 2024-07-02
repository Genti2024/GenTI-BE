package com.gt.genti.common.picture.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPictureEntity is a Querydsl query type for PictureEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QPictureEntity extends EntityPathBase<PictureEntity> {

    private static final long serialVersionUID = -423833178L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPictureEntity pictureEntity = new QPictureEntity("pictureEntity");

    public final com.gt.genti.common.basetimeentity.model.QBaseTimeEntity _super = new com.gt.genti.common.basetimeentity.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath key = createString("key");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<com.gt.genti.picture.PictureRatio> pictureRatio = createEnum("pictureRatio", com.gt.genti.picture.PictureRatio.class);

    public final com.gt.genti.user.model.QUser uploadedBy;

    public QPictureEntity(String variable) {
        this(PictureEntity.class, forVariable(variable), INITS);
    }

    public QPictureEntity(Path<? extends PictureEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPictureEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPictureEntity(PathMetadata metadata, PathInits inits) {
        this(PictureEntity.class, metadata, inits);
    }

    public QPictureEntity(Class<? extends PictureEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.uploadedBy = inits.isInitialized("uploadedBy") ? new com.gt.genti.user.model.QUser(forProperty("uploadedBy"), inits.get("uploadedBy")) : null;
    }

}

