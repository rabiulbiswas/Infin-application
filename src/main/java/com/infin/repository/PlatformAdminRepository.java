package com.infin.repository;

import com.infin.dto.platform.admin.PlatformAdminResponse;
import com.infin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformAdminRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT new com.infin.dto.platform.admin.PlatformAdminResponse(u.id,u.firstName,u.lastName,u.email,u.mobile,u.isVerified,u.isEnabled) FROM User u WHERE u.id =:platformAdminId", nativeQuery = false)
    Optional<PlatformAdminResponse> findByPlatformAdminId(@Param("platformAdminId") Long platformAdminId);




}
