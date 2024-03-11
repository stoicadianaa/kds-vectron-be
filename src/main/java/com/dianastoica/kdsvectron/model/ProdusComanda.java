package com.dianastoica.kdsvectron.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Id is required")
    String id;

    @Field("denumire_produs")
    @JsonProperty("denumire_produs")
    @NotNull(message = "Denumire is required")
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
    @NotNull(message = "Cantitate is required")
    Double cantitate;

    @JsonProperty("pret_produs")
    @Field("pret_produs")
    Double pretUnitar;

    @JsonProperty("id_comanda")
    @Field("id_comanda")
    @NotNull(message = "idComanda is required")
    String idComanda;
}
