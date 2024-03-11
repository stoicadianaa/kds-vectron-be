package com.dianastoica.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class Comanda {
    @Field("id_ospatar")
    @JsonProperty("id_ospatar")
    String idOspatar;

    @Field("nume_ospatar")
    @JsonProperty("nume_ospatar")
    String numeOspatar;

    @Field("tip_comanda")
    @JsonProperty("tip_comanda")
    String tipComanda;

    @Field("table_no")
    @JsonProperty("table_no")
    String nrMasa;

    @Id
    @Field("id_comanda")
    @JsonProperty("id_comanda")
    String idComanda;

    @Field("valoare_comanda")
    @JsonProperty("valoare_comanda")
    double valoareComanda;

    @JsonProperty("observatii_comanda")
    @Field("observatii_comanda")
    String observatiiComanda;

    @Field("produse_comanda")
    @JsonProperty("produse_comanda")
    List<ProdusComanda> produseComanda;

    @JsonProperty("numar_comanda")
    @Field("numar_comanda")
    String nrComanda;

    @Field("data_comanda")
    @JsonProperty("data_comanda")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date dataComanda;

    @Field("start_time")
    @JsonProperty("start_time")
    Date startTime;

    @Field("end_time")
    @JsonProperty("end_time")
    Date endTime;
}
