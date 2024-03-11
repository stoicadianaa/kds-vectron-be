package com.dianastoica.resource;

import com.dianastoica.kdsvectron.resource.kdsvectron.model.Comanda;
import com.dianastoica.kdsvectron.resource.kdsvectron.repository.ComandaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/comenzi")
public class ComandaResource {
    private final ComandaRepository comandaRepository;
    private final SimpMessagingTemplate template;

    public ComandaResource(ComandaRepository comandaRepository, SimpMessagingTemplate template) {
        this.comandaRepository = comandaRepository;
        this.template = template;
    }

    public void broadcastUpdate(Comanda comanda, String updateType) {
        Map<String, Object> updateInfo = new HashMap<>();
        updateInfo.put("updateType", updateType);
        updateInfo.put("comanda", comanda);
        template.convertAndSend("/topic/comandaUpdate", updateInfo);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createComanda(@RequestBody Comanda comanda) {
        Comanda savedComanda = comandaRepository.save(comanda);
        broadcastUpdate(savedComanda, "create");
        return new ResponseEntity<>(savedComanda, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAll() {
        List<Comanda> comenzi = comandaRepository.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("comenzi", comenzi);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updateStartTime/{id}")
    public ResponseEntity<?> updateStartTime(@PathVariable("id") String id) {
        Comanda comanda = comandaRepository.findByIdComanda(id);
        if (comanda != null) {
            comanda.setStartTime((new Date()));
            Comanda updatedComanda = comandaRepository.save(comanda);
            broadcastUpdate(updatedComanda, "updateStartTime");
            return new ResponseEntity<>(comanda, HttpStatus.OK);
        }
        return new ResponseEntity<>("Comanda not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/updateEndTime/{id}")
    public ResponseEntity<?> updateEndTime(@PathVariable("id") String id) {
        Comanda comanda = comandaRepository.findByIdComanda(id);
        if (comanda != null) {
            comanda.setEndTime(new Date());
            Comanda updatedComanda = comandaRepository.save(comanda);
            broadcastUpdate(updatedComanda, "updateEndTime");
        }
        return new ResponseEntity<>("Comanda not found", HttpStatus.NOT_FOUND);
    }
}
