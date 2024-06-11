package com.gt.genti.domain.enums.converter.db;

import com.gt.genti.domain.enums.ReportStatus;

import jakarta.persistence.Converter;

@Converter
public class ReportStatusConverter extends DefaultEnumDBConverter<ReportStatus> {

	public ReportStatusConverter() {
		super(ReportStatus.class);
	}

}