package com.dianastoica.kdsvectron.resource;

import com.dianastoica.kdsvectron.model.Comanda;
import com.dianastoica.kdsvectron.repository.ComandaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/comenzi")
public class ComandaResource {
    private final ComandaRepository comandaRepository;

    public ComandaResource(ComandaRepository comandaRepository) {
        this.comandaRepository = comandaRepository;
    }

    @GetMapping("/all")
    public Map<String, List<Comanda>> getAll() {
        List<Comanda> comenzi = comandaRepository.findAll();
        Map<String, List<Comanda>> response = new HashMap<>();
        response.put("comenzi", comenzi);
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public void delete(String id) {
        comandaRepository.deleteByIdComanda(id);
    }

    @PutMapping("/updateStartTime/{id}")
    public void updateStartTime(@PathVariable("id") String id) {
        Comanda comanda = comandaRepository.findByIdComanda(id);
        if (comanda != null) {
            comanda.setStartTime(new Date());
            comandaRepository.save(comanda);
        }
    }

    @PutMapping("/updateEndTime/{id}")
    public void updateEndTime(@PathVariable("id") String id) {
        Comanda comanda = comandaRepository.findByIdComanda(id);
        if (comanda != null) {
            comanda.setEndTime(new Date());
            comandaRepository.save(comanda);
        }
    }
}
