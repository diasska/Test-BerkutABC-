package com.example.berkut_test.repository;

import com.example.berkut_test.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByUserUsername(String username);
}
