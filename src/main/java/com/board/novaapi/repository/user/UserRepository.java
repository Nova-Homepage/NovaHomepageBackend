package com.board.novaapi.repository.user;

import com.board.novaapi.OAuth.Entity.RoleType;
import com.board.novaapi.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(String userId);

    Page<User> findAll(Pageable pageable);

    @Query(value =
            "select COUNT(user.userId)>0 "+
                    "from User user " +
                    "where user.userId = :userId"
    )
    Boolean checkUserIdExist(String userId);

    @Modifying(clearAutomatically = true)
    @Query(value =
            "update User user " +
                    "set user.roleType = :roleType "+
                    "where user.userId = :userId")
    void UpdateRoleTypeByuserId(String userId,RoleType roleType);

    @Query(value =
            "select user.roleType "+
                    "from User user "+
                    "where user.userId = :userId")
    String getMemberRoleTypeByUserId(String userId);
}
