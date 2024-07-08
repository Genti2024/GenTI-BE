package com.gt.genti.report.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gt.genti.report.model.ReportStatus;
import com.gt.genti.report.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {


	Page<Report> findAllByReportStatusIs(ReportStatus reportStatus, Pageable pageable);
}
