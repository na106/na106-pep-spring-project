package com.example.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
// import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    // @Autowired
    private AccountService accountService;
    
    // @Autowired
    // private AccountRepository accountRepository;

    // @Autowired
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountRepository, MessageService messageRepository) {
        this.accountService = accountRepository;
        this.messageService = messageRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        Account acc = accountService.getAccByUsername(account.getUsername());
        if(acc != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Account savedAccount = accountService.saveAccount(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.OK);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = messageService.getAllMesssages();
        if(messages.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        Message msg = messageService.getMessageById(messageId);
        if(msg == null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessageForUser(@PathVariable Integer accountId){
        List<Message> messages = messageService.getAllMessageForUser(accountId);
        if(messages.isEmpty()){
            return ResponseEntity.ok(messages);
        }
        return ResponseEntity.ok(messages);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody Message message){
        
        if(message.getMessageText().isEmpty() || message.getMessageText().length() > 254){
            return ResponseEntity.badRequest().build();
        }

        if(messageService.getMessageById(message.getPostedBy()) == null){
            return ResponseEntity.badRequest().build();
        }
        Message newMessage = messageService.addMessage(message);
        return ResponseEntity.ok(newMessage);
    }

    @PatchMapping("/messages/{}messageID")
        public ResponseEntity<?> updateMessage(@PathVariable Integer messageId, @RequestBody Message messageUpdate){
            try{
                if(messageUpdate.getMessageText().trim().isEmpty() || messageUpdate.getMessageText().length() > 255){
                    return null;
                }
                Message updatedMsg = messageService.updateMessage(messageId, messageUpdate);
                if(updatedMsg == null){
                    return null;
                }
                return ResponseEntity.ok(1);
            } catch(Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }
    
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteById(@PathVariable Integer messageId){
        int result = messageService.deleteMessage(messageId);
        if(result == 0){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(result);
    }
}
