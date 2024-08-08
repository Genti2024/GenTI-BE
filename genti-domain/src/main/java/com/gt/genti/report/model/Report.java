package com.gt.genti.report.model;

import com.gt.genti.user.model.User;
import com.gt.genti.common.baseentity.model.BaseEntity;
import com.gt.genti.common.converter.ReportStatusConverter;
import com.gt.genti.picturegenerateresponse.model.PictureGenerateResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "report")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@OneToOne
	@JoinColumn(name = "picture_generate_response_id", referencedColumnName = "id", nullable = false)
	PictureGenerateResponse pictureGenerateResponse;

	@Column(name = "content")
	String content;

	@Column(name = "report_status")
	@Convert(converter = ReportStatusConverter.class)
	ReportStatus reportStatus;

	public Report(User requester, PictureGenerateResponse pictureGenerateResponse, String content) {
		setCreatedBy(requester);
		this.pictureGenerateResponse = pictureGenerateResponse;
		this.reportStatus = ReportStatus.NOT_RESOLVED;
		this.content = content;
	}

	public void updateStatus(ReportStatus reportStatus) {
		this.reportStatus = reportStatus;
	}
}
