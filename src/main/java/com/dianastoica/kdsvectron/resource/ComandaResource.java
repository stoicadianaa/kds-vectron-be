package com.dianastoica.kdsvectron.resource;

import com.dianastoica.kdsvectron.model.Comanda;
import com.dianastoica.kdsvectron.model.ProdusComanda;
import com.dianastoica.kdsvectron.repository.ComandaRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rest/comenzi")
public class ComandaResource {
    private final ComandaRepository comandaRepository;
    private final SimpMessagingTemplate template;
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

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
        } else {
            return new ResponseEntity<>("Comanda not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateEndTime/{id}")
    public void updateEndTime(@PathVariable("id") String id) {
        Comanda comanda = comandaRepository.findByIdComanda(id);
        if (comanda != null) {
            comanda.setEndTime(new Date());
            Comanda updatedComanda = comandaRepository.save(comanda);
            broadcastUpdate(updatedComanda, "updateEndTime");
        }
    }
}
