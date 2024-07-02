package com.gt.genti.report.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = -93730794L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReport report = new QReport("report");

    public final com.gt.genti.common.baseentity.model.QBaseEntity _super;

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    // inherited
    public final com.gt.genti.user.model.QUser createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt;

    // inherited
    public final com.gt.genti.user.model.QUser modifiedBy;

    public final com.gt.genti.picturegenerateresponse.model.QPictureGenerateResponse pictureGenerateResponse;

    public final EnumPath<ReportStatus> reportStatus = createEnum("reportStatus", ReportStatus.class);

    public QReport(String variable) {
        this(Report.class, forVariable(variable), INITS);
    }

    public QReport(Path<? extends Report> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReport(PathMetadata metadata, PathInits inits) {
        this(Report.class, metadata, inits);
    }

    public QReport(Class<? extends Report> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.gt.genti.common.baseentity.model.QBaseEntity(type, metadata, inits);
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.modifiedAt = _super.modifiedAt;
        this.modifiedBy = _super.modifiedBy;
        this.pictureGenerateResponse = inits.isInitialized("pictureGenerateResponse") ? new com.gt.genti.picturegenerateresponse.model.QPictureGenerateResponse(forProperty("pictureGenerateResponse"), inits.get("pictureGenerateResponse")) : null;
    }

}

