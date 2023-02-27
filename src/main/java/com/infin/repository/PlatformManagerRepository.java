package com.infin.repository;

import com.infin.dto.platform.manager.PlatformManagerResponse;
import com.infin.entity.platform.manager.PlatformManagerDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformManagerRepository extends JpaRepository<PlatformManagerDetail,Long> {
    @Query(value = "SELECT new com.infin.dto.platform.manager.PlatformManagerResponse(u.id,u.firstName,u.lastName,u.email,u.mobile,p.id,p.contactAddress,p.uploadedDocument,p.validIdProof,u.isVerified,u.isEnabled) FROM User u INNER JOIN PlatformManagerDetail p on p.user.id = u.id WHERE u.roleId =2", nativeQuery = false)
    Page<PlatformManagerResponse> findAllPlatformManager(Pageable pageable);
    @Query(value = "SELECT new com.infin.dto.platform.manager.PlatformManagerResponse(u.id,u.firstName,u.lastName,u.email,u.mobile,p.id,p.contactAddress,p.uploadedDocument,p.validIdProof,u.isVerified,u.isEnabled) FROM User u INNER JOIN PlatformManagerDetail p on p.user.id = u.id WHERE u.id =:platformManagerId", nativeQuery = false)
    Optional<PlatformManagerResponse> findByPlatformManagerId(@Param("platformManagerId") Long platformManagerId);
}
