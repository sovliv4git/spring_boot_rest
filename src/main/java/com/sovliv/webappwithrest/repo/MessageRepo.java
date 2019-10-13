package com.sovliv.webappwithrest.repo;

import com.sovliv.webappwithrest.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
