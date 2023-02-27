package com.infin.repository;
import com.infin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   // User findByUserId(int id);
    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE User SET is_verified =:status WHERE id=:id")
    Integer verifyUserAccountById(Long status,Long id);
}
