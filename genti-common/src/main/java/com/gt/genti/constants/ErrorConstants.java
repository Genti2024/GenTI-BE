package com.gt.genti.constants;

public class ErrorConstants {
	private static final String USER = "USER";
	private static final String PICTURE_GENERATE_RESPONSE = "PGRES";
	private static final String PICTURE_GENERATE_REQUEST = "PGREQ";
	private static final String CREATOR = "CREATOR";
	private static final String ADMIN = "ADMIN";
	private static final String SERVER = "SERVER";
	private static final String UPLOAD = "UPLOAD";
	private static final String REPORT = "REPORT";
	private static final String TEMP = "TEMP";
	private static final String PICTURE = "PICTURE";

	private static final String AUTH = "AUTH";
	private static final String DEPOSIT = "DEPOSIT";
	private static final String WITHDRAW = "WITHDRAW";
	private static final String ENUM = "ENUM";
	private static final String UNDEFINED = "UNDEFINED";

	private static final String OAUTH = "OAUTH";
	private static final String VALIDATION = "VALIDATION";

	private static String CODE(String type, int seq) {
		return type + String.format("-%05d", seq);
	}

	public static final String TOKEN_EXPIRED = CODE(AUTH, 1);
	public static final String INVALID_TOKEN = CODE(AUTH, 2);
	public static final String INSUFFICIENT_PERMISSIONS = CODE(AUTH, 3);
	public static final String REFRESH_TOKEN_EXPIRED = CODE(AUTH, 4);
	public static final String REFRESH_TOKEN_INVALID = CODE(AUTH, 5);
	public static final String TOKEN_CREATION_FAILED = CODE(AUTH, 6);
	public static final String TOKEN_REFRESH_FAILED = CODE(AUTH, 7);

	public static final String TOKEN_NOT_PROVIDED = CODE(AUTH, 8);
	public static final String UnHandledException = CODE(SERVER, 1);
	public static final String ActivePictureGenerateRequestNotExists = CODE(PICTURE_GENERATE_REQUEST, 1);
	public static final String FileTypeNotProvided = CODE(UPLOAD, 1);
	public static final String PictureGenerateRequestNotFound = CODE(PICTURE_GENERATE_REQUEST, 2);
	public static final String UserNotFound = CODE(USER, 1);
	public static final String PictureGenerateRequestAlreadyInProgress = CODE(PICTURE_GENERATE_REQUEST, 3);
	public static final String ZeroMatchingRequests = CODE(PICTURE_GENERATE_REQUEST, 4);
	public static final String UploadFileTypeNotAvailable = CODE(UPLOAD, 2);
	public static final String PictureGenerateResponseNotFound = CODE(PICTURE_GENERATE_RESPONSE, 1);
	public static final String ReportNotFound = CODE(REPORT, 1);
	public static final String NotSupportedTemp = CODE(TEMP, 1);
	public static final String CreatorNotFound = CODE(CREATOR, 1);
	public static final String PictureNotFound = CODE(PICTURE, 1);
	public static final String NotMatchedYet = CODE(PICTURE_GENERATE_REQUEST, 5);
	public static final String PictureGenerateRequestNotAssignedToCreator = CODE(PICTURE_GENERATE_REQUEST, 6);
	public static final String SubmitBlockedDueToPictureGenerateResponseIsExpired = CODE(PICTURE_GENERATE_RESPONSE, 2);
	public static final String PictureGenerateRequestNotAcceptableDueToExpired = CODE(PICTURE_GENERATE_REQUEST, 7);
	public static final String DepositNotFound = CODE(DEPOSIT, 1);
	public static final String AddPointAmountCannotBeMinus = CODE(DEPOSIT, 2);
	public static final String FinalPictureNotUploadedYet = CODE(ADMIN, 1);
	public static final String CreatorsPictureNotUploadedYet = CODE(PICTURE_GENERATE_RESPONSE, 3);
	public static final String UserDeactivated = CODE(USER, 2);

	public static final String AlreadyActivatedUser = CODE(USER, 3);
	public static final String CannotRestoreUser = CODE(USER, 4);
	public static final String PictureUserFaceNotFound = CODE(PICTURE, 1);
	public static final String PicturePoseNotFound = CODE(PICTURE, 2);
	public static final String PictureCompletedNotFound = CODE(PICTURE, 3);
	public static final String PictureCreatedByCreatorNotFound = CODE(PICTURE, 4);
	public static final String PictureProfileNotFound = CODE(PICTURE, 5);
	public static final String NotNullableEnum = CODE(ENUM, 1);
	public static final String DBToEnumFailed = CODE(UNDEFINED, 1);
	public static final String Undefined = CODE(UNDEFINED, 2);
	public static final String WithDrawnUser = CODE(USER, 5);
	public static final String NotAllowedOauthProvider = CODE(OAUTH, 1);
	public static final String ControllerValidationError = CODE(VALIDATION, 1);
	public static final String OnlyRequesterCanViewRequest = CODE(PICTURE_GENERATE_REQUEST, 8);
	public static final String NoHandlerFoundException = CODE(SERVER, 2);
	public static final String UnrecognizedPropertyException = CODE(SERVER, 3);
	public static final String InvalidDataAccessApiUsageException = CODE(SERVER, 4);
	public static final String UserNotLoggedIn = CODE(USER, 6);
	public static final String MethodNotSupported = CODE(SERVER, 5);

	public static final String MethodArgumentTypeMismatch = CODE(SERVER, 7);
	public static final String AlreadyCompletedResponse = CODE(PICTURE_GENERATE_RESPONSE, 4);
	public static final String PGRESStateException = CODE(PICTURE_GENERATE_RESPONSE, 5);
	public static final String NoSettlementForWithdrawalException = CODE(WITHDRAW, 1);
	public static final String WithdrawRequestNotFound = CODE(WITHDRAW, 2);
	public static final String NotEnoughBalance = CODE(WITHDRAW, 3);
	public static final String NoPictureGenerateRequest = CODE(PICTURE_GENERATE_REQUEST, 9);
	public static final String NoPGREQFound = CODE(PICTURE_GENERATE_REQUEST, 10);
	public static final String MissingPathVariableException = CODE(SERVER, 8);
	public static final String InValidFormat = CODE(VALIDATION, 2);
	public static final String UnexpectedPictureGenerateRequestStatus = CODE(PICTURE_GENERATE_REQUEST, 11);
	public static final String INVALID_REFRESH_TOKEN = CODE(AUTH, 9);

	public static final String Forbidden = CODE(AUTH, 10);
	public static final String UnAuthorized = CODE(AUTH, 11);

}
