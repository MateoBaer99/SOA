# RESTApiJerseyCde

## Fonctionnel
Gestion des commandes d'un client.
Les ressources du domaine sont :
* Client avec adresse de livraison
* Commandes avec des lignes de commandes comprendant une quantité et un lien vers le produit
* Produits qui contient la liste des produits du catalogue

Projet Springboot pour montrer l'utilisation des annotations JAX-RS en code first 
* implémentation Jersey
* data binding XML et JSON

Utilisation du mode tracing de Jersey qui rajoute dans les en-têtes HTTP dans la réponse les traces de ce qui s'est 
passé sur le serveur

La gestion des exceptions est faite simplement en montrant deux cas :
* une exception retournée directement sans utiliser l'objet Response (pas de message dans la réponse, malgré le message dans l'exception)
* un exception retournée en utilisant l'objet Reponse et en sérialisant en "dur" au format JSON, on voit la limite de ce fonctionnement 
car elle ne tient plus compte des désiratas du client (ex : si le client voulait une réponse en XML)


