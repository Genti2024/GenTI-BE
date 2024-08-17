package com.gt.genti.openfeign.apple.service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.PrivateKey;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

public class PrivateKeyGenerator {

	public static PrivateKey getPrivateKey(String clientSecret) throws IOException {
		String privateKey = clientSecret;
		Reader pemReader = new StringReader(privateKey);
		PEMParser pemParser = new PEMParser(pemReader);
		JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
		PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
		return converter.getPrivateKey(object);
	}
}
