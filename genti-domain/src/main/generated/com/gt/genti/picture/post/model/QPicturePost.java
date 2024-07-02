package com.gt.genti.picture.post.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPicturePost is a Querydsl query type for PicturePost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPicturePost extends EntityPathBase<PicturePost> {

    private static final long serialVersionUID = 1265582776L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPicturePost picturePost = new QPicturePost("picturePost");

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

    public final com.gt.genti.post.model.QPost post;

    // inherited
    public final com.gt.genti.user.model.QUser uploadedBy;

    public QPicturePost(String variable) {
        this(PicturePost.class, forVariable(variable), INITS);
    }

    public QPicturePost(Path<? extends PicturePost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPicturePost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPicturePost(PathMetadata metadata, PathInits inits) {
        this(PicturePost.class, metadata, inits);
    }

    public QPicturePost(Class<? extends PicturePost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.gt.genti.common.picture.model.QPictureEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.key = _super.key;
        this.modifiedAt = _super.modifiedAt;
        this.pictureRatio = _super.pictureRatio;
        this.post = inits.isInitialized("post") ? new com.gt.genti.post.model.QPost(forProperty("post"), inits.get("post")) : null;
        this.uploadedBy = _super.uploadedBy;
    }

}

