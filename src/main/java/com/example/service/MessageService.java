package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMesssages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id){
        Optional<Message> optMessage = messageRepository.findById(id);
        return optMessage.orElse(null);
    }

    public List<Message> getAllMessageForUser(Integer id){
        return messageRepository.findAllByPostedBy(id);
    }

    public Message addMessage(Message message) {
        
        if(message.getMessageText().length() > 255 
        && message.getMessageText().isEmpty()){

            return null;
        }
        return messageRepository.save(message);
    }

    public Message updateMessage(Integer id, Message message){
        if(!messageRepository.findById(id).isEmpty()) {
            if(message.getMessageText().isEmpty()){
                return null;
            }
            if(message.getMessageText().length() > 255){
                return null;
            }
            return messageRepository.save(message);
        }
        return null;
    }
    public int deleteMessage(Integer id) {
        Optional<Message> row = messageRepository.findById(id);
        if(!row.isEmpty()){
            messageRepository.deleteById(id);
            return 1;
        }
      return 0;
    }
}
