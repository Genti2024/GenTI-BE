package com.gt.genti.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gt.genti.domain.Creator;
import com.gt.genti.domain.User;

public interface CreatorRepository extends JpaRepository<Creator, Long> {

	@Query("select c, count(pgr) as requestCount "
		+ "from Creator c "
		+ "left join c.pictureGenerateRequest pgr "
		+ "with pgr.pictureGenerateRequestStatus in ( "
		+ "com.gt.genti.domain.enums.PictureGenerateRequestStatus.ASSIGNING, "
		+ "com.gt.genti.domain.enums.PictureGenerateRequestStatus.IN_PROGRESS) "
		+ "where c.workable = true "
		+ "group by c "
		+ "having count(pgr) < 3 "
		+ "order by requestCount asc, c.id asc")
	List<Creator> findAllAvailableCreator();

	@Query("select c from Creator c "
		+ "where c.user.id = :userId ")
	Optional<Creator> findByUserId(Long userId);

	Optional<Creator> findByUser(User user);

	@Query("select c from Creator c "
		+ "where c.user.userRole = com.gt.genti.domain.enums.UserRole.ADMIN ")
	List<Creator> findAdminCreator(Pageable pageable);
}
