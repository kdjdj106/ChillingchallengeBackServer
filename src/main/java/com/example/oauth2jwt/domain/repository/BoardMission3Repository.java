package com.example.oauth2jwt.domain.repository;

import com.example.oauth2jwt.domain.entity.BoardMission2;
import com.example.oauth2jwt.domain.entity.BoardMission3;
import com.example.oauth2jwt.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardMission3Repository extends JpaRepository<BoardMission3, Long> {
    List<BoardMission3> findByUser(User user);
}
