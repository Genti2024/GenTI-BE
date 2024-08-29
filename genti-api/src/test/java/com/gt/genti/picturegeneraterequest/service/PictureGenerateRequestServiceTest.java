package com.gt.genti.picturegeneraterequest.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gt.genti.BaseTest;
import com.gt.genti.TestUtils;
import com.gt.genti.creator.model.Creator;
import com.gt.genti.creator.repository.CreatorRepository;
import com.gt.genti.error.ExpectedException;
import com.gt.genti.error.ResponseCode;
import com.gt.genti.user.model.User;
import com.gt.genti.user.model.UserRole;
import com.gt.genti.user.repository.UserRepository;

class PictureGenerateRequestServiceTest extends BaseTest {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private CreatorRepository creatorRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PictureGenerateRequestService pictureGenerateRequestService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@DisplayName("Redis lock 테스트")
	@Test
	void duplicatePictureGenerateRequestSaveFailedByLockTest() {
		User adminUser = TestUtils.Domain.createUser(UserRole.ADMIN);
		User savedAdminUser = userRepository.save(adminUser);
		Creator adminCreator = TestUtils.Domain.createCreator(savedAdminUser);
		creatorRepository.save(adminCreator);

		User requester = TestUtils.Domain.createUser(UserRole.USER);

		User savedRequester = userRepository.save(requester);

		List<CompletableFuture<ExpectedException>> futures = IntStream.range(0, 10)
			.mapToObj(i -> CompletableFuture.supplyAsync(() -> {
				try {
					pictureGenerateRequestService.createPGREQ(savedRequester.getId(),
						TestUtils.Command.getPGREQSaveCommand());
					return null;
				} catch (ExpectedException e) {
					return e;
				}
			}))
			.toList();

		AtomicInteger success = new AtomicInteger();
		futures.stream().map(CompletableFuture::join).forEach(d -> {
			if (d == null) {
				success.getAndIncrement();
			} else {
				assertThat(d.getResponseCode()).isEqualTo(ResponseCode.PictureGenerateRequestAlreadyProcessed);
			}
		});
		assertThat(success.get()).isEqualTo(1);

	}

}