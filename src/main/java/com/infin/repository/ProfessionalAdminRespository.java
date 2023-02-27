package com.infin.repository;

import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.entity.professional.admin.ProfessionalAdminDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProfessionalAdminRespository extends JpaRepository<ProfessionalAdminDetail, Long> {

    @Query(value = "SELECT new com.infin.dto.professional.admin.ProfessionalAdminResponse(u.id,u.firstName,u.lastName,u.email,u.mobile,p.id,p.membershipNumber,p.contactAddress,u.isVerified,u.isEnabled) FROM User u INNER JOIN ProfessionalAdminDetail p on p.user.id = u.id WHERE u.id =:professionalAdminId", nativeQuery = false)
    Optional<ProfessionalAdminResponse> findByProfessionalAdminId(@Param("professionalAdminId") Long professionalAdminId);
    @Query(value = "SELECT new com.infin.dto.professional.admin.ProfessionalAdminResponse(u.id,u.firstName,u.lastName,u.email,u.mobile,p.id,p.membershipNumber,p.contactAddress,u.isVerified,u.isEnabled) FROM User u INNER JOIN ProfessionalAdminDetail p on p.user.id = u.id WHERE u.roleId =4", nativeQuery = false)
    Page<ProfessionalAdminResponse> findAllProfessionalAdmin(Pageable pageable);


}
