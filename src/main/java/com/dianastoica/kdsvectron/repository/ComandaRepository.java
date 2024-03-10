package com.dianastoica.kdsvectron.repository;

import com.dianastoica.kdsvectron.model.Comanda;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComandaRepository extends MongoRepository<Comanda, Integer> {
    void deleteByIdComanda(String id_comanda);
}