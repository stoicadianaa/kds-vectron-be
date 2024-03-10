package com.dianastoica.kdsvectron.resource;

import com.dianastoica.kdsvectron.model.Comanda;
import com.dianastoica.kdsvectron.repository.ComandaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/comenzi")
public class ComandaResource {
    private final ComandaRepository comandaRepository;

    public ComandaResource(ComandaRepository comandaRepository) {
        this.comandaRepository = comandaRepository;
    }

    @GetMapping("/all")
    public List<Comanda> getAll() {
        return comandaRepository.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void delete(String id) {
        comandaRepository.deleteByIdComanda(id);
    }

    @PostMapping("/add")
    public void add(@RequestBody Comanda comanda) {
        comandaRepository.save(comanda);
    }
}
