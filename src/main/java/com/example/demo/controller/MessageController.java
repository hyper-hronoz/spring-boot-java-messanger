package com.example.demo.controller;

import com.example.demo.exeptions.NotFoundException;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/message")
public class MessageController {
    private List<HashMap<String, String>> messages = new ArrayList<>() {{
//        add(new HashMap<String, String>() {{
//            put("id", "1");
//            put("message", "this is first message");
//        }});
//        add(new HashMap<String, String>() {{
//            put("id", "2");
//            put("message", "this is second message");
//        }});
//        add(new HashMap<String, String>() {{
//            put("id", "3");
//            put("message", "this is third message");
//        }});
    }};

    private int counter () {
        return this.messages.size();
    }

    @GetMapping
    @CrossOrigin
    public List<HashMap<String, String>> list() {
        System.out.println(messages);
        return messages;
    }

    @GetMapping("{id}")
    public HashMap<String, String> listGetOne(@PathVariable String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id)).findFirst().orElseThrow(NotFoundException::new);
    }

    @PostMapping
    @CrossOrigin
    public HashMap<String, String> create(@RequestBody HashMap<String, String> message) {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate = myDateObj.format(myFormatObj);
        message.put("id", String.valueOf(this.counter() + 1));
        message.put("userId", message.get("userId"));
        message.put("username", message.get("username"));
        message.put("time", formattedDate);
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public HashMap<String, String> update(@PathVariable String id, @RequestBody  HashMap<String, String> message) {
        if (message.containsKey("message")) {
            HashMap<String, String> messageFromDataBase = listGetOne(id);
            messageFromDataBase.put("message", message.get("message"));
            messageFromDataBase.put("id", id);
            return messageFromDataBase;
        } else {
            throw new NotFoundException();
        }
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        HashMap<String, String> message = listGetOne(id);
        messages.remove(message);
    }
}
