package com.dianastoica.repository;

import com.dianastoica.kdsvectron.resource.kdsvectron.model.Comanda;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComandaRepository extends MongoRepository<Comanda, Integer> {
    Comanda findByIdComanda(String id_comanda);
}