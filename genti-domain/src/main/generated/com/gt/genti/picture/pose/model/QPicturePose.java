package com.gt.genti.picture.pose.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPicturePose is a Querydsl query type for PicturePose
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPicturePose extends EntityPathBase<PicturePose> {

    private static final long serialVersionUID = 1047758938L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPicturePose picturePose = new QPicturePose("picturePose");

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

    public QPicturePose(String variable) {
        this(PicturePose.class, forVariable(variable), INITS);
    }

    public QPicturePose(Path<? extends PicturePose> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPicturePose(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPicturePose(PathMetadata metadata, PathInits inits) {
        this(PicturePose.class, metadata, inits);
    }

    public QPicturePose(Class<? extends PicturePose> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.gt.genti.common.picture.model.QPictureEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.key = _super.key;
        this.modifiedAt = _super.modifiedAt;
        this.pictureRatio = _super.pictureRatio;
        this.uploadedBy = _super.uploadedBy;
    }

}

