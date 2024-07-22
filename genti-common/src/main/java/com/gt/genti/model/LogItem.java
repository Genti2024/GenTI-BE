package com.gt.genti.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LogItem {
	USER("user"),
	OAUTH("oauth"),
	OAUTH_APPLE("oauth : apple"),
	OAUTH_KAKAO("oauth : kakao"),
	OAUTH_GOOGLE("oauth : google"),
	JWT("jwt"),
	CREATOR_MY("creator : my"),
	CREATOR_STATUS("creator : status"),
	CREATOR_ACCOUNT("creator : account"),
	PICTURE("picture"),
	PICTURE_PRESIGNED_URL("picture : presigned-url"),
	PGREQ("pgreq"),
	PGREQ_ACCEPT("pgreq : accept"),
	PGREQ_REJECT("pgreq : reject"),
	PGREQ_INPROGESS("pgreq : inprogress"),
	PGRES("pgres"),
	PGRES_PICTURE_COMPLETED("pgres : picture_completed"),
	PGRES_PICTURE_CREATED_BY_CREATOR("pgres : picture_created_by_creator"),
	PGRES_ASSIGNEE("pgres : assignee"),
	PGRES_SUBMIT("pgres : submit"),
	PGRES_MEMO("pgres : memo"),
	PGRES_VERIFY("pgres : verify"),
	PGRES_STAR("pgres : star"),
	POST("post"),
	REPORT("report"),
	RESPONSE_EXAMPLE("response_example"),
	CASHOUT("cashout"),
	SETTLEMENT("settlement");

	private final String value;

	@Override
	public String toString() {
		return value;
	}
}
