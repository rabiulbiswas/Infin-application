package com.infin.repository;

import com.infin.dto.client.ClientAdminResponse;
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
public interface ProfessionalAdminRespository extends JpaRepository<User, Long> {
    @Query(value = "SELECT new com.infin.dto.client.ClientAdminResponse(u.id,u.name,u.email,u.mobile,c.companyName,c.panNumber,c.gstNumber,c.businessType,c.communicationAddress,u.createdBy) FROM User u INNER JOIN ClientAdminDetail c on c.user.id = u.id WHERE u.createdBy =:userId", nativeQuery = false)
    Page<ClientAdminResponse> findByUser(@Param("userId") Long userId, Pageable pageable);
    @Query(value = "SELECT new com.infin.dto.client.ClientAdminResponse(u.id,u.name,u.email,u.mobile,c.companyName,c.panNumber,c.gstNumber,c.businessType,c.communicationAddress,u.createdBy) FROM User u INNER JOIN ClientAdminDetail c on c.user.id = u.id WHERE u.id =:clientDetailId", nativeQuery = false)
    Optional<ClientAdminResponse> findByClientAdminId(@Param("clientDetailId") Long clientDetailId);

    @Query(value = "SELECT new com.infin.dto.professional.admin.ProfessionalAdminResponse(u.id,u.name,u.email,u.mobile,p.membershipNumber,p.contactAddress,u.createdBy) FROM User u INNER JOIN ProfessionalAdminDetail p on p.user.id = u.id WHERE u.id =2", nativeQuery = false)
    Optional<ProfessionalAdminResponse> findByProfessionalAdminId(@Param("professionalAdminId") Long professionalAdminId);

}
