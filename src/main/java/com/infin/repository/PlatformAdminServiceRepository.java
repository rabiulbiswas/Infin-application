package com.infin.repository;

import com.infin.dto.client.ClientAdminResponse;
import com.infin.dto.platform.admin.PlatformAdminResponse;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformAdminServiceRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT new com.infin.dto.platform.admin.PlatformAdminResponse(u.id,u.name,u.email,u.mobile,u.createdBy) FROM User u WHERE u.id =:platformAdminId", nativeQuery = false)
    Optional<PlatformAdminResponse> findByPlatformAdminId(@Param("platformAdminId") Long platformAdminId);

    @Query(value = "SELECT new com.infin.dto.professional.admin.ProfessionalAdminResponse(u.id,u.name,u.email,u.mobile,p.id,p.membershipNumber,p.contactAddress,u.createdBy) FROM User u INNER JOIN ProfessionalAdminDetail p on p.user.id = u.id WHERE u.roleId =4", nativeQuery = false)
    Page<ProfessionalAdminResponse> findAllProfessionalAdmin(Pageable pageable);

    @Query(value = "SELECT new com.infin.dto.client.ClientAdminResponse(u.id,u.name,u.email,u.mobile,c.id,c.companyName,c.panNumber,c.gstNumber,c.businessType,c.communicationAddress,u.createdBy) FROM User u INNER JOIN ClientAdminDetail c on c.user.id = u.id WHERE u.roleId =7", nativeQuery = false)
    Page<ClientAdminResponse> findAllClientAdmin(Pageable pageable);
}
