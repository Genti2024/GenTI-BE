package com.gt.genti.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenGenerateCommand {
	String userId;
	String role;

	@Builder
	public TokenGenerateCommand(String userId, String role) {
		this.userId = userId;
		this.role = role;
	}
}
