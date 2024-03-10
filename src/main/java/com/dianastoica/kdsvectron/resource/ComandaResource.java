package com.dianastoica.kdsvectron.resource;

import com.dianastoica.kdsvectron.model.Comanda;
import com.dianastoica.kdsvectron.repository.ComandaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
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
        updateInfo.put("updateType", updateType); // e.g., "create", "update", "delete"
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

//    @GetMapping("/all/stream")
//    public ResponseBodyEmitter getAllStream() {
//        final ResponseBodyEmitter emitter = new ResponseBodyEmitter();
//
//        // Use a separate thread or an executor service for non-blocking IO operations
//        new Thread(() -> {
//            try {
//                List<Comanda> comenzi = comandaRepository.findAll();
//                for (Comanda comanda : comenzi) {
//                    // Assuming you have a way to convert Comanda objects to JSON or some other format
//                    emitter.send(comanda);
//                    // Mimic some delay or processing time
//                    Thread.sleep(1000);
//                }
//                emitter.complete();
//            } catch (IOException | InterruptedException e) {
//                emitter.completeWithError(e);
//            }
//        }).start();
//
//        return emitter;
//    }

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
            comanda.setStartTime(new Date());
            broadcastUpdate(comanda, "updateStartTime");
            return new ResponseEntity<>(comanda, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comanda not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateEndTime/{id}")
    public ResponseEntity<?> updateEndTime(@PathVariable("id") String id) {
        Comanda comanda = comandaRepository.findByIdComanda(id);
        if (comanda != null) {
            comanda.setEndTime(new Date());
            broadcastUpdate(comanda, "updateEndTime");
            return new ResponseEntity<>(comanda, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comanda not found", HttpStatus.NOT_FOUND);
        }
    }
}
