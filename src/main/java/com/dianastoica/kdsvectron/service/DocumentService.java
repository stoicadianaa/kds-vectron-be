package com.dianastoica.kdsvectron.service;

import com.dianastoica.kdsvectron.model.Comanda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void documentChanged(Comanda comanda) {
        messagingTemplate.convertAndSend("/topic/documentChanges", comanda);
    }
}
