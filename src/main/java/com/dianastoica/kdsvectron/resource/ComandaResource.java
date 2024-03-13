package com.dianastoica.kdsvectron.resource;

import com.dianastoica.kdsvectron.model.Comanda;
import com.dianastoica.kdsvectron.model.ProdusComanda;
import com.dianastoica.kdsvectron.repository.ComandaRepository;
import com.dianastoica.kdsvectron.service.ComandaService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/comenzi")
public class ComandaResource {
    private final ComandaRepository comandaRepository;
    private final ComandaService comandaService;
    private final SimpMessagingTemplate template;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public ComandaResource(ComandaRepository comandaRepository, ComandaService comandaService, SimpMessagingTemplate template) {
        this.comandaRepository = comandaRepository;
        this.comandaService = comandaService;
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
        if (comandaRepository.existsByIdComanda(comanda.getIdComanda())) {
            return new ResponseEntity<>("Comanda already exists", HttpStatus.BAD_REQUEST);
        }

        if (comanda.getDataComanda() == null) {
            comanda.setDataComanda(new Date());
        }

        Set<ConstraintViolation<Comanda>> violations = validator.validate(comanda);
        for (ConstraintViolation<Comanda> violation : violations) {
            return new ResponseEntity<>(violation.getMessage(), HttpStatus.BAD_REQUEST);
        }

        for (ProdusComanda produsComanda : comanda.getProduseComanda()) {
            Set<ConstraintViolation<ProdusComanda>> produsComandaViolations = validator.validate(produsComanda);
            for (ConstraintViolation<ProdusComanda> violation : produsComandaViolations) {
                return new ResponseEntity<>(violation.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }

        Comanda savedComanda = comandaRepository.save(comanda);
        broadcastUpdate(savedComanda, "create");
        return new ResponseEntity<>(savedComanda, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAll(@RequestParam(required = false) Boolean ended) {
        List<Comanda> comenzi = comandaRepository.findAll();

        Predicate<Comanda> filterPredicate = (ended != null && ended)
                ? comanda -> comanda.getEndTime() != null
                : comanda -> comanda.getEndTime() == null;

        if (ended != null) {
            comenzi = comenzi.stream()
                    .filter(filterPredicate)
                    .collect(Collectors.toList());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("comenzi", comenzi);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/updateStartTime")
    public ResponseEntity<?> updateStartTime(@RequestParam String id) {
        Comanda comanda = comandaRepository.findByIdComanda(id);
        if (comanda != null) {
            comanda.setStartTime((new Date()));
            comandaService.updateStartTime(id, new Date());
            broadcastUpdate(comanda, "updateStartTime");
            return new ResponseEntity<>(comanda, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comanda not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateEndTime")
    public ResponseEntity<?> updateEndTime(@RequestParam String id) {
        Comanda comanda = comandaRepository.findByIdComanda(id);
        if (comanda != null) {
            comanda.setEndTime((new Date()));
            comandaService.updateEndTime(id, new Date());
            broadcastUpdate(comanda, "updateEndTime");
            return new ResponseEntity<>(comanda, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Comanda not found", HttpStatus.NOT_FOUND);
        }
    }
}
