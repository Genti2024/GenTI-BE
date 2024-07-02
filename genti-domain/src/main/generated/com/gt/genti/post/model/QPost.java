package com.gt.genti.post.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 1413237206L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.gt.genti.common.basetimeentity.model.QBaseTimeEntity _super = new com.gt.genti.common.basetimeentity.model.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likes = createNumber("likes", Integer.class);

    public final com.gt.genti.picture.post.model.QPicturePost mainPicture;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final ListPath<com.gt.genti.picture.post.model.PicturePost, com.gt.genti.picture.post.model.QPicturePost> pictureList = this.<com.gt.genti.picture.post.model.PicturePost, com.gt.genti.picture.post.model.QPicturePost>createList("pictureList", com.gt.genti.picture.post.model.PicturePost.class, com.gt.genti.picture.post.model.QPicturePost.class, PathInits.DIRECT2);

    public final EnumPath<PostStatus> postStatus = createEnum("postStatus", PostStatus.class);

    public final com.gt.genti.user.model.QUser user;

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.mainPicture = inits.isInitialized("mainPicture") ? new com.gt.genti.picture.post.model.QPicturePost(forProperty("mainPicture"), inits.get("mainPicture")) : null;
        this.user = inits.isInitialized("user") ? new com.gt.genti.user.model.QUser(forProperty("user"), inits.get("user")) : null;
    }

}
