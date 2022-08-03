package com.example.project1.repository;

import com.example.project1.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    Content findOneById(Long contentId);
    List<Content> findByName(String name);

}

