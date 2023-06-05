package com.example.oauth2jwt.domain.repository;

import com.example.oauth2jwt.domain.entity.BoardMission1;
import com.example.oauth2jwt.domain.entity.BoardMission2;
import com.example.oauth2jwt.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardMission2Repository extends JpaRepository<BoardMission2, Long> {
    List<BoardMission2> findByUser(User user);
}
