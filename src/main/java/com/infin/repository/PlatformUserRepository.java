package com.infin.repository;
;
import com.infin.dto.platform.user.PlatformUserResponse;
import com.infin.entity.platform.user.PlatformUserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformUserRepository extends JpaRepository<PlatformUserDetail,Long> {

    @Query(value = "SELECT new com.infin.dto.platform.user.PlatformUserResponse(u.id,u.name,u.email,u.mobile,p.id,p.contactAddress,p.uploadedDocument,p.validIdProof,p.platformManagerId) FROM User u INNER JOIN PlatformUserDetail p on p.user.id = u.id WHERE u.roleId =3", nativeQuery = false)
    Page<PlatformUserResponse> findAllPlatformUser(Pageable pageable);

    @Query(value = "SELECT new com.infin.dto.platform.user.PlatformUserResponse(u.id,u.name,u.email,u.mobile,p.id,p.contactAddress,p.uploadedDocument,p.validIdProof,p.platformManagerId) FROM User u INNER JOIN PlatformUserDetail p on p.user.id = u.id WHERE u.id =:platformUserId", nativeQuery = false)
    Optional<PlatformUserResponse> findByPlatformUserId(@Param("platformUserId") Long platformUserId);
}
