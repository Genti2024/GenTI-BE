package com.gt.genti.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class MatchingRegistry {
	private final ConcurrentHashMap<Long, List<Long>> matchRecord = new ConcurrentHashMap<>();

	public void add(Long requestId, Long creatorId) {
		if (matchRecord.containsKey(requestId)) {
			matchRecord.get(requestId).add(creatorId);
		} else {
			List<Long> newCreatorList = new ArrayList<>();
			newCreatorList.add(creatorId);
			matchRecord.put(requestId, newCreatorList);
		}
	}

	public List<Long> getMatchedCreatorBefore(Long requestId) {
		if (matchRecord.containsKey(requestId)) {
			return matchRecord.get(requestId);
		}
		return new ArrayList<>();
	}
}
