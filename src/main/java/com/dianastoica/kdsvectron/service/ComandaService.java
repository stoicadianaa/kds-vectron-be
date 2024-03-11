package com.dianastoica.kdsvectron.service;

import com.dianastoica.kdsvectron.model.Comanda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ComandaService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void updateEndTime(String id, Date endTime) {
        Query query = new Query(Criteria.where("id_comanda").is(id));
        Update update = new Update().set("end_time", endTime);
        mongoTemplate.findAndModify(query, update, Comanda.class);
    }

    public void updateStartTime(String id, Date startTime) {
        Query query = new Query(Criteria.where("id_comanda").is(id));
        Update update = new Update().set("start_time", startTime);
        mongoTemplate.findAndModify(query, update, Comanda.class);
    }
}
