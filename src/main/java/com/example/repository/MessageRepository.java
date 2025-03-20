package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

    // @Transactional
    // @Modifying
    // @Query("Delete from Message m where m.id = ?1")
    // int deleteMessageById(Integer messageId);

    // @Query("SELECT m FROM Message m WHERE m.posted_by = ?1")
    List<Message> findAllByPostedBy(Integer posted_by);

}
