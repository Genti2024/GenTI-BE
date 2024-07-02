package com.gt.genti.picturegenerateresponse.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPictureGenerateResponse is a Querydsl query type for PictureGenerateResponse
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPictureGenerateResponse extends EntityPathBase<PictureGenerateResponse> {

    private static final long serialVersionUID = -915385224L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPictureGenerateResponse pictureGenerateResponse = new QPictureGenerateResponse("pictureGenerateResponse");

    public final com.gt.genti.common.basetimeentity.model.QBaseTimeEntity _super = new com.gt.genti.common.basetimeentity.model.QBaseTimeEntity(this);

    public final StringPath adminInCharge = createString("adminInCharge");

    public final ListPath<com.gt.genti.picture.completed.model.PictureCompleted, com.gt.genti.picture.completed.model.QPictureCompleted> completedPictureList = this.<com.gt.genti.picture.completed.model.PictureCompleted, com.gt.genti.picture.completed.model.QPictureCompleted>createList("completedPictureList", com.gt.genti.picture.completed.model.PictureCompleted.class, com.gt.genti.picture.completed.model.QPictureCompleted.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<com.gt.genti.picture.createdbycreator.model.PictureCreatedByCreator, com.gt.genti.picture.createdbycreator.model.QPictureCreatedByCreator> createdByCreatorPictureList = this.<com.gt.genti.picture.createdbycreator.model.PictureCreatedByCreator, com.gt.genti.picture.createdbycreator.model.QPictureCreatedByCreator>createList("createdByCreatorPictureList", com.gt.genti.picture.createdbycreator.model.PictureCreatedByCreator.class, com.gt.genti.picture.createdbycreator.model.QPictureCreatedByCreator.class, PathInits.DIRECT2);

    public final com.gt.genti.creator.model.QCreator creator;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath memo = createString("memo");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.gt.genti.picturegeneraterequest.model.QPictureGenerateRequest request;

    public final EnumPath<PictureGenerateResponseStatus> status = createEnum("status", PictureGenerateResponseStatus.class);

    public final DateTimePath<java.time.LocalDateTime> submittedByAdminAt = createDateTime("submittedByAdminAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> submittedByCreatorAt = createDateTime("submittedByCreatorAt", java.time.LocalDateTime.class);

    public QPictureGenerateResponse(String variable) {
        this(PictureGenerateResponse.class, forVariable(variable), INITS);
    }

    public QPictureGenerateResponse(Path<? extends PictureGenerateResponse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPictureGenerateResponse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPictureGenerateResponse(PathMetadata metadata, PathInits inits) {
        this(PictureGenerateResponse.class, metadata, inits);
    }

    public QPictureGenerateResponse(Class<? extends PictureGenerateResponse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new com.gt.genti.creator.model.QCreator(forProperty("creator"), inits.get("creator")) : null;
        this.request = inits.isInitialized("request") ? new com.gt.genti.picturegeneraterequest.model.QPictureGenerateRequest(forProperty("request"), inits.get("request")) : null;
    }

}

