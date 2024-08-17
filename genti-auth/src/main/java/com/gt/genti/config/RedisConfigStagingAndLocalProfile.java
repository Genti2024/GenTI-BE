package com.gt.genti.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Profile({"staging", "local"})
@Configuration
@EnableRedisRepositories
public class RedisConfigStagingAndLocalProfile {

	@Value("${redis.host}")
	String host;
	@Value("${redis.port}")
	int port;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
		redisConfiguration.setHostName(host);
		redisConfiguration.setPort(port);
		return new LettuceConnectionFactory(redisConfiguration);
	}

	@Bean
	@Primary
	public RedisTemplate<String, String> redisTemplate() {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}
}
