package com.board.novaapi.Repository;


import com.board.novaapi.Entity.Member.Member;

public interface MemberRepository {

    void save(Member member);
    Member findById(Long studentId);
}
