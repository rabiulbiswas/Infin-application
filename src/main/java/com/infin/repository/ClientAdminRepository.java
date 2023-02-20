package com.infin.repository;

import com.infin.dto.client.ClientAdminResponse;
import com.infin.entity.client.ClientAdminDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientAdminRepository extends JpaRepository<ClientAdminDetail, Long> {
    @Query(value = "SELECT new com.infin.dto.client.ClientAdminResponse(u.id,u.name,u.email,u.mobile,c.id,c.companyName,c.panNumber,c.gstNumber,c.businessType,c.communicationAddress,u.createdBy) FROM User u INNER JOIN ClientAdminDetail c on c.user.id = u.id WHERE u.id =:clientDetailId", nativeQuery = false)
    Optional<ClientAdminResponse> findByClientAdminId(@Param("clientDetailId") Long clientDetailId);

}
