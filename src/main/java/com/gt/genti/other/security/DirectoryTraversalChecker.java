package com.gt.genti.other.security;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gt.genti.domain.DirectoryTraversalAttack;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DirectoryTraversalChecker {
	private final DirectoryRepository directoryRepository;

	@Transactional
	public Boolean isPreviousAttackedUrl(String url) {
		Optional<DirectoryTraversalAttack> optionalDirectory = directoryRepository.findByUrl(url);

		optionalDirectory.ifPresentOrElse(
			DirectoryTraversalAttack::addCount,
			() -> directoryRepository.save(new DirectoryTraversalAttack(url))
		);

		return optionalDirectory.isPresent();
	}

}
