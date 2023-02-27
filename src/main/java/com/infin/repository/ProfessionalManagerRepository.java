package com.infin.repository;

import com.infin.dto.client.ClientAdminResponse;
import com.infin.dto.platform.admin.PlatformAdminResponse;
import com.infin.dto.professional.manager.ProfessionalManagerResponse;
import com.infin.entity.professional.manager.ProfessionalManagerDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionalManagerRepository extends JpaRepository<ProfessionalManagerDetail, Long> {
    @Query(value = "SELECT new com.infin.dto.professional.manager.ProfessionalManagerResponse(u.id,u.firstName,u.lastName,u.email,u.mobile,c.id,c.validIdProof,u.isVerified,u.isEnabled) FROM User u INNER JOIN ProfessionalManagerDetail c on c.user.id = u.id WHERE u.id =:professionalManagerId", nativeQuery = false)
    Optional<ProfessionalManagerResponse> getProfessionalManagerProfile(@Param("professionalManagerId") Long professionalManagerId);
    @Query(value = "SELECT new com.infin.dto.professional.manager.ProfessionalManagerResponse(u.id,u.firstName,u.lastName,u.email,u.mobile,c.id,c.validIdProof,u.isVerified,u.isEnabled) FROM User u INNER JOIN ProfessionalManagerDetail c on c.user.id = u.id WHERE u.createdBy =:professionalAdminId AND u.roleId =5", nativeQuery = false)
    Page<ProfessionalManagerResponse> getProfessionalManagerByProfessionalAdmin(@Param("professionalAdminId") Long professionalAdminId, Pageable pageable);
}
