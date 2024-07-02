package com.gt.genti.picture.responseexample.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResponseExample is a Querydsl query type for ResponseExample
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QResponseExample extends EntityPathBase<ResponseExample> {

    private static final long serialVersionUID = 1623160338L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResponseExample responseExample = new QResponseExample("responseExample");

    public final com.gt.genti.common.picture.model.QPictureEntity _super;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    public final StringPath examplePrompt = createString("examplePrompt");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath key;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    //inherited
    public final EnumPath<com.gt.genti.picture.PictureRatio> pictureRatio;

    public final BooleanPath promptOnly = createBoolean("promptOnly");

    // inherited
    public final com.gt.genti.user.model.QUser uploadedBy;

    public QResponseExample(String variable) {
        this(ResponseExample.class, forVariable(variable), INITS);
    }

    public QResponseExample(Path<? extends ResponseExample> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResponseExample(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResponseExample(PathMetadata metadata, PathInits inits) {
        this(ResponseExample.class, metadata, inits);
    }

    public QResponseExample(Class<? extends ResponseExample> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.gt.genti.common.picture.model.QPictureEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.key = _super.key;
        this.modifiedAt = _super.modifiedAt;
        this.pictureRatio = _super.pictureRatio;
        this.uploadedBy = _super.uploadedBy;
    }

}

