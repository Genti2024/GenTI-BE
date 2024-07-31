package com.gt.genti.constants;

public class ErrorConstants {
	private static final String USER = "USER";
	private static final String CREATOR = "CREATOR";

	private static final String PGRES = "PGRES";
	private static final String PGREQ = "PGREQ";
	private static final String REPORT = "REPORT";
	private static final String PICTURE = "PICTURE";

	private static final String DEPOSIT = "DEPOSIT";
	private static final String WITHDRAW = "WITHDRAW";

	private static final String UPLOAD = "UPLOAD";
	private static final String DISCORD = "DISCORD";
	private static final String VALIDATION = "VALIDATION";

	private static final String AUTH = "AUTH";
	private static final String OAUTH = "OAUTH";
	private static final String SERVER = "SERVER";

	private static String CODE(String type, int seq) {
		return type + String.format("-%05d", seq);
	}

	public static final String TOKEN_EXPIRED = CODE(AUTH, 1);
	public static final String INVALID_TOKEN = CODE(AUTH, 2);
	public static final String INSUFFICIENT_PERMISSIONS = CODE(AUTH, 3);
	public static final String REFRESH_TOKEN_EXPIRED = CODE(AUTH, 4);
	public static final String REFRESH_TOKEN_INVALID = CODE(AUTH, 5);
	public static final String TOKEN_REFRESH_FAILED = CODE(AUTH, 6);
	public static final String TOKEN_NOT_PROVIDED = CODE(AUTH, 7);
	public static final String REFRESH_TOKEN_NOT_EXISTS = CODE(AUTH, 8);
	public static final String Forbidden = CODE(AUTH, 9);
	public static final String UnAuthorized = CODE(AUTH, 10);

	public static final String UnHandledException = CODE(SERVER, 1);
	public static final String HandlerNotFound = CODE(SERVER, 2);
	public static final String UnrecognizedProperty = CODE(SERVER, 3);
	public static final String InvalidDataAccessApiUsage = CODE(SERVER, 4);
	public static final String HttpRequestMethodNotSupported = CODE(SERVER, 5);
	public static final String MethodArgumentTypeMismatch = CODE(SERVER, 7);
	public static final String MissingPathVariableException = CODE(SERVER, 8);
	public static final String EncryptAlgorithmDeprecated = CODE(SERVER, 8);
	public static final String DBToEnumFailed = CODE(SERVER, 9);
	public static final String NotNullableEnum = CODE(SERVER, 10);
	public static final String TimeOut = CODE(SERVER, 11);
	public static final String EnumMappingFailed = CODE(SERVER, 12);


	public static final String ActivePictureGenerateRequestNotExists = CODE(PGREQ, 1);
	public static final String PictureGenerateRequestNotFound = CODE(PGREQ, 2);
	public static final String PictureGenerateRequestAlreadyInProgress = CODE(PGREQ, 3);
	public static final String PictureGenerateRequestNotAssignedToCreator = CODE(PGREQ, 4);
	public static final String PictureGenerateRequestNotAcceptableDueToExpired = CODE(PGREQ, 5);
	public static final String OnlyRequesterCanViewPictureGenerateRequest = CODE(PGREQ, 6);
	public static final String NoPictureGenerateRequest = CODE(PGREQ, 7);
	public static final String UnexpectedPictureGenerateRequestStatus = CODE(PGREQ, 8);

	public static final String FileTypeNotProvided = CODE(UPLOAD, 1);
	public static final String UploadFileTypeNotAvailable = CODE(UPLOAD, 2);

	public static final String UserNotFound = CODE(USER, 1);
	public static final String UserDeactivated = CODE(USER, 2);
	public static final String AlreadyActivatedUser = CODE(USER, 3);
	public static final String CannotRestoreUser = CODE(USER, 4);
	public static final String WithDrawnUser = CODE(USER, 5);
	public static final String UserNotLoggedIn = CODE(USER, 6);
	public static final String UserAlreadySignedUp = CODE(USER, 7);


	public static final String CreatorNotFound = CODE(CREATOR, 1);

	public static final String PictureGenerateResponseNotFound = CODE(PGRES, 1);
	public static final String SubmitBlockedDueToPictureGenerateResponseIsExpired = CODE(PGRES, 2);
	public static final String CreatorsPictureNotUploadedYet = CODE(PGRES, 3);
	public static final String AlreadyCompletedPictureGenerateResponse = CODE(PGRES, 4);
	public static final String PGRESStateException = CODE(PGRES, 5);
	public static final String FinalPictureNotUploadedYet = CODE(PGRES, 6);

	public static final String ReportNotFound = CODE(REPORT, 1);

	public static final String DepositNotFound = CODE(DEPOSIT, 1);
	public static final String AddPointAmountCannotBeMinus = CODE(DEPOSIT, 2);

	public static final String PictureNotFound = CODE(PICTURE, 1);
	public static final String PictureUserFaceNotFound = CODE(PICTURE, 2);
	public static final String PicturePoseNotFound = CODE(PICTURE, 3);
	public static final String PictureCompletedNotFound = CODE(PICTURE, 4);
	public static final String PictureCreatedByCreatorNotFound = CODE(PICTURE, 5);
	public static final String PictureProfileNotFound = CODE(PICTURE, 6);

	public static final String HandlerMethodValidation = CODE(VALIDATION, 1);
	public static final String HttpMessageNotReadable = CODE(VALIDATION, 2);

	public static final String CannotCreateWithdrawalDueToSettlementsNotAvailable = CODE(WITHDRAW, 1);
	public static final String WithdrawRequestNotFound = CODE(WITHDRAW, 2);
	public static final String NotEnoughBalance = CODE(WITHDRAW, 3);

	public static final String OauthProviderNotAllowed = CODE(OAUTH, 1);
	public static final String AppleOauthIdTokenIncorrect = CODE(OAUTH, 2);
	public static final String AppleOauthIdTokenExpired = CODE(OAUTH, 3);
	public static final String AppleOauthIdTokenInvalid = CODE(OAUTH, 4);
	public static final String AppleOauthClaimInvalid = CODE(OAUTH, 5);
	public static final String AppleOauthPublicKeyInvalid = CODE(OAUTH, 6);
	public static final String AppleOauthJwtValueInvalid = CODE(OAUTH, 7);

	public static final String NoWebhookEmbeds = CODE(DISCORD, 1);
	public static final String DiscordIOException = CODE(DISCORD, 2);


}
