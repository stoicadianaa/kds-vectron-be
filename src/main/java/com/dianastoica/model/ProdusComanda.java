package com.dianastoica.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdusComanda {
    @Field("id_produs")
    @JsonProperty("id_produs")
    String id;

    @Field("denumire_produs")
    @JsonProperty("denumire_produs")
    String denumire;

    @JsonProperty("id_categorie_produs")
    @Field("id_categorie_produs")
    String idCategorieProdus;

    @JsonProperty("denumire_categorie_produs")
    @Field("denumire_categorie_produs")
    String denumireCategorie;

    @JsonProperty("observatii_produs")
    @Field("observatii_produs")
    String observatii;

    @JsonProperty("cantitate_produs")
    @Field("cantitate_produs")
    double cantitate;

    @JsonProperty("pret_produs")
    @Field("pret_produs")
    double pretUnitar;

    @JsonProperty("valoare_produs")
    @Field("id_comanda")
    int idComanda;
}
