package com.board.novaapi.repository.user;

import com.board.novaapi.OAuth.Entity.RoleType;
import com.board.novaapi.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUserId(String userId);

    @Query(value =
            "select COUNT(user.userId)>0 "+
                    "from User user " +
                    "where user.userId = :userId"
    )
    public Boolean checkUserIdExist(String userId);

    @Query(value =
            "select user.userId, user.username, user.roleType " +
                    "from User user ")
    public List<String> getAllSimpleMemberInfo();

    @Modifying(clearAutomatically = true)
    @Query(value =
            "update User user " +
                    "set user.roleType = :roleType "+
                    "where user.userId = :userId")
    public void UpdateRoleTypeByuserId(String userId, RoleType roleType);

    @Query(value =
            "select user.roleType "+
                    "from User user "+
                    "where user.userId = :userId")
    public String getMemberRoleTypeByUserId(String userId);
}
