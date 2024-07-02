package com.gt.genti.picture.userface.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPictureUserFace is a Querydsl query type for PictureUserFace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPictureUserFace extends EntityPathBase<PictureUserFace> {

    private static final long serialVersionUID = -1866912152L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPictureUserFace pictureUserFace = new QPictureUserFace("pictureUserFace");

    public final com.gt.genti.common.picture.model.QPictureEntity _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath key;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    //inherited
    public final EnumPath<com.gt.genti.picture.PictureRatio> pictureRatio;

    // inherited
    public final com.gt.genti.user.model.QUser uploadedBy;

    public QPictureUserFace(String variable) {
        this(PictureUserFace.class, forVariable(variable), INITS);
    }

    public QPictureUserFace(Path<? extends PictureUserFace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPictureUserFace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPictureUserFace(PathMetadata metadata, PathInits inits) {
        this(PictureUserFace.class, metadata, inits);
    }

    public QPictureUserFace(Class<? extends PictureUserFace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.gt.genti.common.picture.model.QPictureEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.key = _super.key;
        this.modifiedAt = _super.modifiedAt;
        this.pictureRatio = _super.pictureRatio;
        this.uploadedBy = _super.uploadedBy;
    }

}

