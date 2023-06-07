package com.tiendq.cinebooking.controller;

import com.tiendq.cinebooking.websocket.ChatMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ChatController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/publicChatRoom")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/publicChatRoom")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat/{username}")
    @SendTo("/topic/{username}" + "-admin")
    public ChatMessage userConnectAdmin(@Payload ChatMessage chatMessage, @DestinationVariable("username") String username) {
        return chatMessage;
    }

    @RequestMapping("/chats")
    public String index(HttpServletRequest request, Model model) {
//        String username = (String) request.getSession().getAttribute("username");
//
//        if (username == null || username.isEmpty()) {
//            return "redirect:/login";
//        }
//        model.addAttribute("username", username);

        return "chat-user";
    }

    @RequestMapping("/chats-admin")
    public String chatAdmin() {
        return "chat-admin";
    }
}