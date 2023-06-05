package com.example.oauth2jwt.domain.repository;

import com.example.oauth2jwt.domain.entity.BoardMission1;
import com.example.oauth2jwt.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardMission1Repository extends JpaRepository<BoardMission1 , Long> {
    List<BoardMission1> findByUser(User user);
}
