package com.gt.genti.dto;

import java.time.LocalDateTime;

public interface CreatedAtSorter {
	LocalDateTime getCreatedAt();
	int asc(CreatedAtSorter other);
	int desc(CreatedAtSorter other);
}
