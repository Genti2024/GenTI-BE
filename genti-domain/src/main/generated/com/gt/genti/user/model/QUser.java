package com.gt.genti.user.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -2106511114L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.gt.genti.common.basetimeentity.model.QBaseTimeEntity _super = new com.gt.genti.common.basetimeentity.model.QBaseTimeEntity(this);

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.gt.genti.creator.model.QCreator creator;

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final com.gt.genti.deposit.model.QDeposit deposit;

    public final StringPath email = createString("email");

    public final BooleanPath emailVerified = createBoolean("emailVerified");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath introduction = createString("introduction");

    public final DateTimePath<java.time.LocalDateTime> lastLoginDate = createDateTime("lastLoginDate", java.time.LocalDateTime.class);

    public final EnumPath<OauthPlatform> lastLoginOauthPlatform = createEnum("lastLoginOauthPlatform", OauthPlatform.class);

    public final StringPath loginId = createString("loginId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<com.gt.genti.picture.profile.model.PictureProfile, com.gt.genti.picture.profile.model.QPictureProfile> pictureProfileList = this.<com.gt.genti.picture.profile.model.PictureProfile, com.gt.genti.picture.profile.model.QPictureProfile>createList("pictureProfileList", com.gt.genti.picture.profile.model.PictureProfile.class, com.gt.genti.picture.profile.model.QPictureProfile.class, PathInits.DIRECT2);

    public final ListPath<com.gt.genti.picture.userface.model.PictureUserFace, com.gt.genti.picture.userface.model.QPictureUserFace> pictureUserFaceList = this.<com.gt.genti.picture.userface.model.PictureUserFace, com.gt.genti.picture.userface.model.QPictureUserFace>createList("pictureUserFaceList", com.gt.genti.picture.userface.model.PictureUserFace.class, com.gt.genti.picture.userface.model.QPictureUserFace.class, PathInits.DIRECT2);

    public final NumberPath<Integer> requestTaskCount = createNumber("requestTaskCount", Integer.class);

    public final EnumPath<Sex> sex = createEnum("sex", Sex.class);

    public final StringPath socialId = createString("socialId");

    public final StringPath username = createString("username");

    public final EnumPath<UserRole> userRole = createEnum("userRole", UserRole.class);

    public final EnumPath<UserStatus> userStatus = createEnum("userStatus", UserStatus.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creator = inits.isInitialized("creator") ? new com.gt.genti.creator.model.QCreator(forProperty("creator"), inits.get("creator")) : null;
        this.deposit = inits.isInitialized("deposit") ? new com.gt.genti.deposit.model.QDeposit(forProperty("deposit"), inits.get("deposit")) : null;
    }

}

