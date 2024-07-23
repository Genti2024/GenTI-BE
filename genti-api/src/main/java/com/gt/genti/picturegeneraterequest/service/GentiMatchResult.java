package com.gt.genti.picturegeneraterequest.service;

import java.util.ArrayList;
import java.util.List;

import com.gt.genti.matchingstrategy.model.RequestMatchStrategy;

import lombok.Data;

@Data
public class GentiMatchResult {
	RequestMatchStrategy requestMatchStrategy;
	List<String> matchResult = new ArrayList<>();
	String summary = "";
	public GentiMatchResult(RequestMatchStrategy requestMatchStrategy) {
		this.requestMatchStrategy = requestMatchStrategy;
	}

	public void addMatchResult(String matchResult) {
		this.matchResult.add(matchResult);
	}

	public void addSummary(String summary) {
		this.setSummary(getSummary() + summary);
	}
}
