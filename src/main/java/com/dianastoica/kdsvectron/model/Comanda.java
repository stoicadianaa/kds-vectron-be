package com.dianastoica.kdsvectron.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.*;
import jakarta.validation.constraints.NotNull;
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
    @Field("id_opatar")
    @JsonProperty("id_ospatar")
    private String idOspatar;

    @Field("nume_ospatar")
    @JsonProperty("nume_ospatar")
    private String numeOspatar;

    @Field("tip_comanda")
    @JsonProperty("tip_comanda")
    private String tipComanda;

    @Field("table_no")
    @JsonProperty("table_no")
    private String nrMasa;

    @Id
    @Field("id_comanda")
    @JsonProperty("id_comanda")
    @NotNull(message = "Id comanda is required")
    private String idComanda;

    @Field("valoare_comanda")
    @JsonProperty("valoare_comanda")
    private Double valoareComanda;

    @JsonProperty("observatii_comanda")
    @Field("observatii_comanda")
    private String observatiiComanda;

    @Field("produse_comanda")
    @JsonProperty("produse_comanda")
    @NotNull(message = "Produse comanda is required")
    private List<ProdusComanda> produseComanda;

    @JsonProperty("numar_comanda")
    @Field("numar_comanda")
    @NotNull(message = "Numar comanda is required")
    private String nrComanda;

    @Field("data_comanda")
    @JsonProperty("data_comanda")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dataComanda;

    @Field("start_time")
    @JsonProperty("start_time")
    private Date startTime;

    @Field("end_time")
    @JsonProperty("end_time")
    private Date endTime;
}
