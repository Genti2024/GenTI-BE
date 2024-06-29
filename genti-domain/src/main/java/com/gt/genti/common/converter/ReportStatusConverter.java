package com.gt.genti.common.converter;

import com.gt.genti.common.DefaultEnumDBConverter;
import com.gt.genti.report.model.ReportStatus;

import jakarta.persistence.Converter;

@Converter
public class ReportStatusConverter extends DefaultEnumDBConverter<ReportStatus> {

	public ReportStatusConverter() {
		super(ReportStatus.class);
	}

}