package com.gt.genti.openfeign.apple.service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
		String privateKey = new String(Files.readAllBytes(Paths.get(resource.getURI())));

		Reader pemReader = new StringReader(privateKey);
		PEMParser pemParser = new PEMParser(pemReader);
		JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
		PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
		return converter.getPrivateKey(object);
	}
}
