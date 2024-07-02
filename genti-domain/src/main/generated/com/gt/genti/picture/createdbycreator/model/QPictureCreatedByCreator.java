package com.gt.genti.picture.createdbycreator.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPictureCreatedByCreator is a Querydsl query type for PictureCreatedByCreator
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPictureCreatedByCreator extends EntityPathBase<PictureCreatedByCreator> {

    private static final long serialVersionUID = 2118500626L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPictureCreatedByCreator pictureCreatedByCreator = new QPictureCreatedByCreator("pictureCreatedByCreator");

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

    // inherited
    public final com.gt.genti.user.model.QUser uploadedBy;

    public QPictureCreatedByCreator(String variable) {
        this(PictureCreatedByCreator.class, forVariable(variable), INITS);
    }

    public QPictureCreatedByCreator(Path<? extends PictureCreatedByCreator> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPictureCreatedByCreator(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPictureCreatedByCreator(PathMetadata metadata, PathInits inits) {
        this(PictureCreatedByCreator.class, metadata, inits);
    }

    public QPictureCreatedByCreator(Class<? extends PictureCreatedByCreator> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.gt.genti.common.picture.model.QPictureEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.key = _super.key;
        this.modifiedAt = _super.modifiedAt;
        this.pictureGenerateResponse = inits.isInitialized("pictureGenerateResponse") ? new com.gt.genti.picturegenerateresponse.model.QPictureGenerateResponse(forProperty("pictureGenerateResponse"), inits.get("pictureGenerateResponse")) : null;
        this.pictureRatio = _super.pictureRatio;
        this.uploadedBy = _super.uploadedBy;
    }

}

