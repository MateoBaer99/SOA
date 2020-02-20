/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.api.impl;

import org.universite.ventes.model.Commande;
import org.universite.ventes.model.Adresse;
import org.universite.ventes.model.Client;
import org.universite.ventes.model.Produit;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.springframework.stereotype.Component;
import org.universite.ventes.api.CommandesResource;
import org.universite.ventes.model.Adresse.TypeVoieEnum;
import org.universite.ventes.model.LigneCommande;


/**
 *
 * @author PAKI6340
 */
@Component("CommandesResource")
public class CommandesResourceRAM implements CommandesResource{
  
    //Initialisation de données en RAM
    private static Map<String,Client> clients=new HashMap<>();
    private static Map<UUID,Commande> commandes=new HashMap<>();
    private static Map<UUID,Produit> produits=new HashMap<>();
        
    static {
       // Initialisation d'un Client
        Adresse adresse=new Adresse().numeroVoie(12)
                                     .typeVoie(TypeVoieEnum.RUE)
                                     .nomVoie("des allouettes")
                                     .codePostal(31000)
                                     .commune("Toulouse");
        Client client = new Client().identifiant("45874AV")
                             .nom("Larivee")
                             .mail("jerome.larivee@univ-tlse.fr")
                             .tel("33609090909")
                             .vip(false)
                             .adresseLivraison(adresse);
        clients.put(client.getIdentifiant(), client);
        
        // Création de produits
        Produit prod1=new Produit().name("Cutter4X").description("Cutter fer 4 lames").poids((float) 0.2).prix(23.34);
        produits.put(prod1.getId(), prod1);
        Produit prod2=new Produit().name("CutterPs").description("Cutter plastique 1ier prix").poids((float) 0.1).prix(5.30);
        produits.put(prod2.getId(), prod2);
        Produit prod3=new Produit().name("CiseauxXT").description("Ciseaux carton").poids((float) 0.15).prix(13.34);
        produits.put(prod3.getId(), prod3);        
        Produit prod4=new Produit().name("MasseH100").description("Masse professionnelle").poids((float) 1.5).prix(128.55);
        produits.put(prod4.getId(), prod4); 
        Produit prod5=new Produit().name("MarteauTR2").description("Marteau de charpentier").poids((float) 1.0).prix(45);
        produits.put(prod5.getId(), prod5);
        Produit prod6=new Produit().name("CrayonXX124").description("Crayon mine bois pro").poids((float) 0.05).prix(2.5);
        produits.put(prod6.getId(), prod6);
        
       
        // Création d'1 commande de 3 produits                
        Commande com1=new Commande().date(LocalDate.of(2019, 03, 13))
                           .addLigne(prod1,5)
                           .addLigne(prod3,8)
                           .addLigne(prod5,20);
        commandes.put(com1.getId(),com1);
        
        //Création d'1 autre commande avec 2 produits
        Commande com2=new Commande().date(LocalDate.of(2020, 01, 18))
                           .addLigne(prod3,1)
                           .addLigne(prod6,10);
        commandes.put(com2.getId(),com2);        
    }
    
    

    public List<Commande> getToutesCommandes() {
        return (List<Commande>) commandes.values().stream().collect(Collectors.toList());
    }
    
    public Response creerCommande(Commande commande) {
        // On vérifie que les produits commandés existent bien
        try {
            if (commande.getLignes().size()== commande.getLignes().stream()
                                                                  .filter(lc -> produits.containsKey(lc.getProduit().getId()))
                                                                  .count()) { 
                //On affecte le produit du catalogue
                commande.getLignes().forEach( (LigneCommande lc) -> lc.setProduit(produits.get(lc.getProduit().getId())));
            } else {
                throw new WebApplicationException(Response.status(Status.BAD_REQUEST)
                                                      .entity("{\"Erreur\": \"Produits commandés non valides : inconnus au catalogue\"}")
                                                      .type("application/json")
                                                      .build());
            }
   
        } catch (NullPointerException ne) {
            throw new WebApplicationException("Infos commandes incorrectes",Response.Status.BAD_REQUEST);
        }
        commandes.put(commande.getId(),commande);
       // return Response.created(URI.create("/commandes/"+commande.getId())).build();
       return Response.ok(commande).build();  

    }    
}
