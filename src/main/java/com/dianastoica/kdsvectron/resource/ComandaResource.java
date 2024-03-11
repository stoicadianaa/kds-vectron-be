package com.dianastoica.kdsvectron.resource;

import com.dianastoica.kdsvectron.model.Comanda;
import com.dianastoica.kdsvectron.repository.ComandaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Comanda comanda = comandaRepository.findByIdComanda(id);
        if (comanda != null) {
            comandaRepository.deleteByIdComanda(id);
            broadcastUpdate(comanda, "delete");
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comanda not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStartTime/{id}")
    public ResponseEntity<?> updateStartTime(@PathVariable("id") String id) {
        Comanda comanda = comandaRepository.findByIdComanda(id);
        if (comanda != null) {
            comanda.setStartTime(ZonedDateTime.now(ZoneId.of("Europe/Bucharest")));
            Comanda updatedComanda = comandaRepository.save(comanda);
            broadcastUpdate(updatedComanda, "updateStartTime");
            return new ResponseEntity<>(comanda, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comanda not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateEndTime/{id}")
    public void updateEndTime(@PathVariable("id") String id) {
        Comanda comanda = comandaRepository.findByIdComanda(id);
        if (comanda != null) {
            comanda.setEndTime(ZonedDateTime.now(ZoneId.of("Europe/Bucharest")));
            Comanda updatedComanda = comandaRepository.save(comanda);
            broadcastUpdate(updatedComanda, "updateEndTime");
        }
    }
}
