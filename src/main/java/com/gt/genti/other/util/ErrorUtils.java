package com.gt.genti.other.util;

public class ErrorUtils {
	private static final String USER = "USER";
	private static final String RESPONSE = "RESPONSE";
	private static final String REQUEST = "REQUEST";
	private static final String CREATOR = "CREATOR";
	private static final String ADMIN = "ADMIN";
	private static final String SERVER = "SERVER";
	private static final String UPLOAD = "UPLOAD";
	private static final String REPORT = "REPORT";
	private static final String TEMP = "TEMP";
	private static final String PICTURE = "PICTURE";

	private static final String AUTH = "AUTH";
	private static final String DEPOSIT = "DEPOSIT";
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
	public static final String ActivePictureGenerateRequestNotExists = CODE(REQUEST, 1);
	public static final String FileTypeNotProvided = CODE(UPLOAD, 1);
	public static final String PictureGenerateRequestNotFound = CODE(REQUEST, 2);
	public static final String UserNotFound = CODE(USER, 1);
	public static final String RequestAlreadyInProgress = CODE(REQUEST, 3);
	public static final String ZeroMatchingRequests = CODE(REQUEST, 4);
	public static final String UploadFileTypeNotAvailable = CODE(UPLOAD, 2);
	public static final String PictureGenerateResponseNotFound = CODE(RESPONSE, 1);
	public static final String ReportNotFound = CODE(REPORT, 1);
	public static final String NotSupportedTemp = CODE(TEMP, 1);
	public static final String CreatorNotFound = CODE(CREATOR, 1);
	public static final String PictureNotFound = CODE(PICTURE, 1);
	public static final String NotMatchedYet = CODE(REQUEST, 5);
	public static final String NotAssignedToMe = CODE(REQUEST, 6);
	public static final String ExpiredPictureGenerateRequest = CODE(RESPONSE, 2);
	public static final String ExpiredMatching = CODE(REQUEST, 7);
	public static final String DepositNotFound = CODE(DEPOSIT, 1);
	public static final String AddPointAmountCannotBeMinus = CODE(DEPOSIT, 2);
	public static final String FinalPictureNotUploadedYet = CODE(ADMIN, 1);
	public static final String CreatorsPictureNotUploadedYet = CODE(RESPONSE, 3);
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
	public static final String OnlyRequesterCanViewRequest = CODE(REQUEST, 8);
	public static final String NoHandlerFoundException = CODE(SERVER, 2);
	public static final String UnrecognizedPropertyException = CODE(SERVER, 3);
	public static final String InvalidDataAccessApiUsageException = CODE(SERVER, 4);
}
