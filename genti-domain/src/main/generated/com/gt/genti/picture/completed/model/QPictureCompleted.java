package com.gt.genti.picture.completed.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPictureCompleted is a Querydsl query type for PictureCompleted
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPictureCompleted extends EntityPathBase<PictureCompleted> {

    private static final long serialVersionUID = 273704738L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPictureCompleted pictureCompleted = new QPictureCompleted("pictureCompleted");

    public final com.gt.genti.common.picture.model.QPictureEntity _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath key;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    public final com.gt.genti.picturegenerateresponse.model.QPictureGenerateResponse pictureGenerateResponse;

    //inherited
    public final EnumPath<com.gt.genti.picture.PictureRatio> pictureRatio;

    public final com.gt.genti.user.model.QUser requester;

    // inherited
    public final com.gt.genti.user.model.QUser uploadedBy;

    public QPictureCompleted(String variable) {
        this(PictureCompleted.class, forVariable(variable), INITS);
    }

    public QPictureCompleted(Path<? extends PictureCompleted> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPictureCompleted(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPictureCompleted(PathMetadata metadata, PathInits inits) {
        this(PictureCompleted.class, metadata, inits);
    }

    public QPictureCompleted(Class<? extends PictureCompleted> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.gt.genti.common.picture.model.QPictureEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.key = _super.key;
        this.modifiedAt = _super.modifiedAt;
        this.pictureGenerateResponse = inits.isInitialized("pictureGenerateResponse") ? new com.gt.genti.picturegenerateresponse.model.QPictureGenerateResponse(forProperty("pictureGenerateResponse"), inits.get("pictureGenerateResponse")) : null;
        this.pictureRatio = _super.pictureRatio;
        this.requester = inits.isInitialized("requester") ? new com.gt.genti.user.model.QUser(forProperty("requester"), inits.get("requester")) : null;
        this.uploadedBy = _super.uploadedBy;
    }

}

