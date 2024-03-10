package com.dianastoica.kdsvectron.repository;

import com.dianastoica.kdsvectron.model.Comanda;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;

public interface ComandaRepository extends MongoRepository<Comanda, Integer> {
    void deleteByIdComanda(String id_comanda);
    Comanda findByIdComanda(String id_comanda);

    void updateComandaByStartTime(String id_comanda, Date start_time);

    void updateComandaByEndTime(String id_comanda, Date end_time);
}