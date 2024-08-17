package com.gt.genti.openfeign.apple.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.PrivateKey;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class PrivateKeyGenerator {

	@Value("${apple.private-key-path}")
	private String privateKeyPath;

	public PrivateKey getPrivateKey() throws IOException {
		ClassPathResource resource = new ClassPathResource(privateKeyPath);

		try (Reader pemReader = new InputStreamReader(resource.getInputStream());
			 PEMParser pemParser = new PEMParser(pemReader)) {
			PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo)pemParser.readObject();
			JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
			return converter.getPrivateKey(privateKeyInfo);
		}
	}
}
