package com.example.backend0.service;

import com.example.backend0.dto.MessageDTO;
import com.example.backend0.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MessageService
 * @Description
 **/
@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    public List<MessageDTO> getMessagesByUserID(Integer userID) {
//        return messageRepository.getMessagesByUserID(userID);
        return null;
    }
}