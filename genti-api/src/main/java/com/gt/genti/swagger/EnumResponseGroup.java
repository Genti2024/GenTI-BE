package com.gt.genti.swagger;

import static com.gt.genti.error.ResponseCode.*;

public enum EnumResponseGroup {
	@EnumResponses(value = {
		@EnumResponse(TOKEN_NOT_PROVIDED),
		@EnumResponse(TOKEN_EXPIRED),
		@EnumResponse(INVALID_TOKEN),
		@EnumResponse(INSUFFICIENT_PERMISSIONS),
		@EnumResponse(REFRESH_TOKEN_EXPIRED),
	})
	AUTH,

	@EnumResponses(value = {
		@EnumResponse(PictureGenerateRequestNotFound),
		@EnumResponse(PictureGenerateResponseNotFound),
		@EnumResponse(UserNotFound),
		@EnumResponse(ReportNotFound),
		@EnumResponse(CreatorNotFound),
		@EnumResponse(PictureNotFound),
		@EnumResponse(DepositNotFound),
		@EnumResponse(PictureUserFaceNotFound),
		@EnumResponse(PicturePoseNotFound),
		@EnumResponse(PictureCompletedNotFound),
		@EnumResponse(PictureCreatedByCreatorNotFound),
		@EnumResponse(PictureProfileNotFound),
		@EnumResponse(CashoutNotFound)

	})
	NOT_FOUND,

	@EnumResponses(value = {
		@EnumResponse(OauthProviderNotAllowed)
	})
	OAUTH
}
