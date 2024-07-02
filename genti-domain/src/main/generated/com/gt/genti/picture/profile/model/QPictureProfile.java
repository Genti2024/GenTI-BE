package com.gt.genti.picture.profile.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPictureProfile is a Querydsl query type for PictureProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPictureProfile extends EntityPathBase<PictureProfile> {

    private static final long serialVersionUID = 919224034L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPictureProfile pictureProfile = new QPictureProfile("pictureProfile");

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

    public final com.gt.genti.user.model.QUser user;

    public QPictureProfile(String variable) {
        this(PictureProfile.class, forVariable(variable), INITS);
    }

    public QPictureProfile(Path<? extends PictureProfile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPictureProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPictureProfile(PathMetadata metadata, PathInits inits) {
        this(PictureProfile.class, metadata, inits);
    }

    public QPictureProfile(Class<? extends PictureProfile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.gt.genti.common.picture.model.QPictureEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.key = _super.key;
        this.modifiedAt = _super.modifiedAt;
        this.pictureRatio = _super.pictureRatio;
        this.uploadedBy = _super.uploadedBy;
        this.user = inits.isInitialized("user") ? new com.gt.genti.user.model.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

