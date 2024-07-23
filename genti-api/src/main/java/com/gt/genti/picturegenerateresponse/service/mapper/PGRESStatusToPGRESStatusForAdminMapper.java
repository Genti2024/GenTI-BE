package com.gt.genti.picturegenerateresponse.service.mapper;

import static com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;

import com.gt.genti.mapper.AbstractEnumMapper;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponseStatus;

public class PGRESStatusToPGRESStatusForAdminMapper
	extends AbstractEnumMapper<PictureGenerateResponseStatus, PictureGenerateResponseStatusForAdmin> {

	private final static List<SimpleEntry<PictureGenerateResponseStatus, PictureGenerateResponseStatusForAdmin>> mappings = Arrays.asList(
		new SimpleEntry<>(CREATOR_BEFORE_WORK, PictureGenerateResponseStatusForAdmin.BEFORE_WORK),
		new SimpleEntry<>(CREATOR_SUBMITTED_FIRST, PictureGenerateResponseStatusForAdmin.BEFORE_WORK),
		new SimpleEntry<>(ADMIN_BEFORE_WORK, PictureGenerateResponseStatusForAdmin.BEFORE_WORK),
		new SimpleEntry<>(ADMIN_IN_PROGRESS, PictureGenerateResponseStatusForAdmin.IN_PROGRESS),
		new SimpleEntry<>(ADMIN_SUBMITTED_FINAL, PictureGenerateResponseStatusForAdmin.COMPLETED),
		new SimpleEntry<>(REPORTED, PictureGenerateResponseStatusForAdmin.COMPLETED),
		new SimpleEntry<>(COMPLETED, PictureGenerateResponseStatusForAdmin.COMPLETED),
		new SimpleEntry<>(EXPIRED, PictureGenerateResponseStatusForAdmin.EXPIRED)
	);

	public PGRESStatusToPGRESStatusForAdminMapper() {
		super(PictureGenerateResponseStatus.class, PictureGenerateResponseStatusForAdmin.class, mappings);
	}

}
