package com.gt.genti.openfeign.apple.dto.response;

import java.util.List;

import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ApplePublicKeys {

	private List<ApplePublicKey> keys;

	public ApplePublicKey getMatchesKey(String alg, String kid) {
		return this.keys
			.stream()
			.filter(k -> k.getAlg().equals(alg) && k.getKid().equals(kid))
			.findFirst()
			.orElseThrow(() -> ExpectedException.withLogging(ResponseCode.AppleOauthJwtValueInvalid));
	}
}