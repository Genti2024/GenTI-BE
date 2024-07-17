package com.gt.genti.picturegeneraterequest.service.mapper;

import static com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus.*;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;

import com.gt.genti.mapper.AbstractEnumMapper;
import com.gt.genti.picturegeneraterequest.model.PictureGenerateRequestStatus;

public class PGREQStatusToPGREQStatusForUserMapper
	extends AbstractEnumMapper<PictureGenerateRequestStatus, PictureGenerateRequestStatusForUser> {

	private final static List<SimpleEntry<PictureGenerateRequestStatus, PictureGenerateRequestStatusForUser>> mappings = Arrays.asList(
		new SimpleEntry<>(AWAIT_USER_VERIFICATION, PictureGenerateRequestStatusForUser.AWAIT_USER_VERIFICATION),
		new SimpleEntry<>(COMPLETED, PictureGenerateRequestStatusForUser.COMPLETED),
		new SimpleEntry<>(IN_PROGRESS, PictureGenerateRequestStatusForUser.IN_PROGRESS),
		new SimpleEntry<>(CREATED, PictureGenerateRequestStatusForUser.IN_PROGRESS),
		new SimpleEntry<>(ASSIGNING, PictureGenerateRequestStatusForUser.IN_PROGRESS),
		new SimpleEntry<>(MATCH_TO_ADMIN, PictureGenerateRequestStatusForUser.IN_PROGRESS),
		new SimpleEntry<>(REPORTED, PictureGenerateRequestStatusForUser.IN_PROGRESS),
		new SimpleEntry<>(CANCELED, PictureGenerateRequestStatusForUser.IN_PROGRESS)
	);

	public PGREQStatusToPGREQStatusForUserMapper() {
		super(PictureGenerateRequestStatus.class, PictureGenerateRequestStatusForUser.class, mappings);
	}
}
