package com.gt.genti.picturegeneraterequest.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPictureGenerateRequest is a Querydsl query type for PictureGenerateRequest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPictureGenerateRequest extends EntityPathBase<PictureGenerateRequest> {

    private static final long serialVersionUID = -1581925674L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPictureGenerateRequest pictureGenerateRequest = new QPictureGenerateRequest("pictureGenerateRequest");

    public final com.gt.genti.common.basetimeentity.model.QBaseTimeEntity _super = new com.gt.genti.common.basetimeentity.model.QBaseTimeEntity(this);

    public final EnumPath<CameraAngle> cameraAngle = createEnum("cameraAngle", CameraAngle.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.gt.genti.creator.model.QCreator creator;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath matchToAdmin = createBoolean("matchToAdmin");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final EnumPath<PictureGenerateRequestStatus> pictureGenerateRequestStatus = createEnum("pictureGenerateRequestStatus", PictureGenerateRequestStatus.class);

    public final com.gt.genti.picture.pose.model.QPicturePose picturePose;

    public final EnumPath<com.gt.genti.picture.PictureRatio> pictureRatio = createEnum("pictureRatio", com.gt.genti.picture.PictureRatio.class);

    public final StringPath prompt = createString("prompt");

    public final StringPath promptAdvanced = createString("promptAdvanced");

    public final com.gt.genti.user.model.QUser requester;

    public final ListPath<com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse, com.gt.genti.picturegenerateresponse.model.QPictureGenerateResponse> responseList = this.<com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse, com.gt.genti.picturegenerateresponse.model.QPictureGenerateResponse>createList("responseList", com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse.class, com.gt.genti.picturegenerateresponse.model.QPictureGenerateResponse.class, PathInits.DIRECT2);

    public final EnumPath<ShotCoverage> shotCoverage = createEnum("shotCoverage", ShotCoverage.class);

    public final ListPath<com.gt.genti.picture.userface.model.PictureUserFace, com.gt.genti.picture.userface.model.QPictureUserFace> userFacePictureList = this.<com.gt.genti.picture.userface.model.PictureUserFace, com.gt.genti.picture.userface.model.QPictureUserFace>createList("userFacePictureList", com.gt.genti.picture.userface.model.PictureUserFace.class, com.gt.genti.picture.userface.model.QPictureUserFace.class, PathInits.DIRECT2);

    public QPictureGenerateRequest(String variable) {
        this(PictureGenerateRequest.class, forVariable(variable), INITS);
    }

    public QPictureGenerateRequest(Path<? extends PictureGenerateRequest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPictureGenerateRequest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPictureGenerateRequest(PathMetadata metadata, PathInits inits) {
        this(PictureGenerateRequest.class, metadata, inits);
    }

    public QPictureGenerateRequest(Class<? extends PictureGenerateRequest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new com.gt.genti.creator.model.QCreator(forProperty("creator"), inits.get("creator")) : null;
        this.picturePose = inits.isInitialized("picturePose") ? new com.gt.genti.picture.pose.model.QPicturePose(forProperty("picturePose"), inits.get("picturePose")) : null;
        this.requester = inits.isInitialized("requester") ? new com.gt.genti.user.model.QUser(forProperty("requester"), inits.get("requester")) : null;
    }

}

