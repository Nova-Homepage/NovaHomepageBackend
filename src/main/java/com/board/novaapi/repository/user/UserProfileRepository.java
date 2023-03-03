package com.board.novaapi.repository.user;

import com.board.novaapi.entity.user.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {


    Page<UserProfile> findAll(Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query(value =
            "update UserProfile profile " +
            "set profile.profileComment = :comment," +
                 "profile.phone = :phone," +
                 "profile.studentId = :studentId," +
                 "profile.blogInfo = :blogInfo "+
            "where profile.userProfileSeq = :userSeq"
    )
    void updateProfileByUserSeq(Long userSeq, String comment, String phone, String studentId, String blogInfo);
}
