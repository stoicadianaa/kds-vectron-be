package com.dianastoica.kdsvectron.controller;

import com.dianastoica.kdsvectron.model.Comanda;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketHandler {

    @MessageMapping("/update")
    @SendTo("/topic/updates")
    public List<Comanda> sendUpdate(List<Comanda> comenzi) {
        return comenzi;
    }
}