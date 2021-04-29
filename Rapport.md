<img src="https://raw.githubusercontent.com/Marshellson/Cinematheque/012237b44f73f83f6dd1a0b8080fd51150b10f6d/rapport_image/15232.svg" width="250px" />
&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;






# <center>Projet Cinémathèque</center>
&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

Contributeurs: JIANG Yilun, KANG Zhuodong, WANG Haoyu

&nbsp;

&nbsp;

&nbsp;

&nbsp;








# <center>Sommaire</center>

* [Contexte du développement](#Contexte du développement)
  &nbsp;

* [Analyse des exigences](#Analyse des exigences)
  &nbsp;

* [Conception des contours](#Conception des contours)
  &nbsp;

* [Conception détaillée](#Conception détaillée)
  &nbsp;

* [Réaliser](#Réaliser)
  &nbsp;

* [Conclure](#Conclure)
  &nbsp;

&nbsp;
&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

## Contexte du développement
Dans les années 1830, le cinéma entame sa période de préparation technologique prénatale, appelée aussi période d'invention, avec l'invention de la machine de cinéma optique par Renault en 1888, l'invention du cinématographe par Edison en 1889, et en 1895, alors que l'image se joue lentement, les frères Lumière réussissent à inventer la première génération d'appareils de projection cinématographique, une grande invention dans laquelle le film peut être vu par de nombreuses personnes ensemble et qu'il peut offrir à chacun une expérience à la fois inégalée et étonnante.
&nbsp;

&nbsp;

Et avec le développement des temps, la technologie des films progresse également, l'effet des films devient de plus en plus réaliste, semblable à la 3D, la présentation des films devient de plus en plus grande, l'équipement pour lire les films devient de plus en plus grand, la façon de sauvegarder les films devient également de plus en plus grande, semblable au Blu-ray, DVD, etc., qui peut permettre à de plus en plus de gens de regarder des films, la variété des films devient de plus en plus grande,  Mais à mesure que le monde devient de plus en plus cinématographique, la diversité dérange les gens.
Nous ne pouvons pas nous souvenir des films que nous aimons, des films que nous avons regardés, des films que nous possédons, nous ne pouvons pas classer et organiser ces films de manière efficace, et il n'y a aucun programme sur le marché actuellement qui puisse à la fois intégrer sa propre bibliothèque de films et enregistrer les films qu'il possède et ceux qu'il a regardés.
&nbsp;

&nbsp;

Donc nous pensons qu'il y a un besoin pour un tel programme sur le marché aujourd'hui, et la conception de la programmation devient de plus en plus avancée de nos jours, et la base de données de films devient de plus en plus robuste, et les conditions sont très matures, et dans ce temps, nous avons décidé de choisir ce sujet de projet, et nous allons essayer d'utiliser ce que nous avons appris pour l'améliorer pour atteindre le but de notre projet, et nous serons les premiers à utiliser ce programme.
&nbsp;

&nbsp;

Ce projet consiste à créer un logiciel permettant de gérer les films/séries vues par des utilisateurs. Le projet est évolutif, c'est à dire que différentes versions peuvent être proposées, allant de la plus simple à la plus compliquée, de façon à ce que tous puissent, à la fin du semestre, proposer un logiciel en état de fonctionnement.
&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

&nbsp;

## Analyse des exigences
Après avoir décidé de notre thème, nous avons eu une discussion animée sur notre sujet, et nous avons finalement déterminé nos besoins pour ce programme, et les fonctions du programme ont été résumées comme nécessitant les éléments suivants au total.
1 Gérer la bibliothèque de films
Gère les informations de base de tous les films de la bibliothèque, y compris l'ajout, la suppression, etc, et peut organiser les films en fonction de ces informations.
2 Gérer la base de données
Gérer les informations de base des films qui nous sont fournies par le site web, y compris les identifiants des films et la possibilité de sélectionner des films à ajouter à la bibliothèque de films.
Ensuite, nous avons essayé de dessiner un aperçu de notre interface.
&nbsp;

&nbsp;

Cette image est l'interface principale que nous avons dessinée à l'époque, nous avons dessiné l'interface avec notre liste de films sur le côté gauche, les détails des films sur le côté droit, et le titre, le filtre et l'ajout de films sur le côté droit au-dessus, tandis que la fonction de recherche est utilisée sur le côté gauche au-dessus, et nous avons décidé à l'époque que le bouton gauche de la souris peut supprimer les films dont nous n'avons pas besoin.
&nbsp;

&nbsp;

&nbsp;
Après cela, nous nous sommes rendus ensemble sur le site web https://developers.themoviedb.org/3/getting-started/introduction. C'est ce site web que nous devons utiliser pour notre thème, nous avons vérifié l'API de ce site web, et après avoir regardé les instructions du site web, nous avons eu une Après avoir regardé les instructions, nous avons discuté et finalement décidé que nous devions utiliser la grande section de films (Movies)de ce site.
&nbsp;

&nbsp;

Dans ce site, nous obtiendrons les détails de nos films et leurs identifiants. Les identifiants des films permettront d'obtenir facilement des informations sur les films. Mais pour utiliser ce site, nous avons besoin de la clé API, donc nous nous sommes inscrits sur ce site et le site nous a donné la clé API.



## Conception des contours

<center>Interface 1 Interface utilisateur principale</center>
Cette interface est globalement la même que celle que nous avons conçue auparavant, à l'exception de quelques modifications. En haut à gauche de l'interface principale se trouve le nom de notre programme (Cinématique) et à côté se trouvent nos deux boutons avec (ComboBox), le bouton Ajouter pour ajouter les films que nous possédons, le bouton Supprimer pour supprimer nos films et (ComboBox) que nous utilisons pour trier les films de notre cinémathèque. Nous avons pensé qu'il y avait trop peu de fonctionnalités pouvant être ajoutées au bouton droit de la souris, et nous avons donc décidé d'utiliser le bouton à sa place. En bas à gauche de cette interface principale se trouve notre bibliothèque de films, qui affichera les films que nous avons ajoutés nous-mêmes, lorsque nous pensons au nom du film ci-dessus, le côté droit de notre interface affichera l'affiche du film avec les détails du film, et en haut à droite de l'interface se trouvent le bouton de recherche et la barre de recherche, après avoir entré le nom du film dans la barre de recherche et cliqué sur le bouton de recherche, nous obtiendrons les résultats de la recherche de films. .

&nbsp;


<center>Interface 2 Interface de recherche de film par l'utilisateur</center>
Lorsque nous cliquons sur le bouton de recherche de film, nous entrons dans cette interface - interface de recherche, dans cette interface, le haut de notre interface est les films les plus pertinents que nous obtenons par la recherche de nom de film, lorsque nous cliquons sur le nom du film, l'affiche de ce film et les informations sommaires du film seront affichées en dessous du nom du film. En bas de l'écran de recherche se trouve le bouton d'ajout, lorsque nous cliquons sur ce bouton, nous entrons dans l'écran d'ajout.


&nbsp;


&nbsp;

<center>Interface 3 L'interface d'ajout de film par l'utilisateur</center>
Après avoir cliqué sur le bouton Ajouter dans l'interface principale, nous accédons à cette page, dans laquelle nous pouvons saisir le nom du film et choisir le type de film, lorsque nous cliquons sur Ajouter, il sera ajouté à notre bibliothèque.

&nbsp;


&nbsp;

<center>Interface 4 L'interface d'ajout de films après la recherche de films par les utilisateurs.</center>
Après avoir cliqué sur le bouton Ajouter dans l'interface Rechercher des films, nous entrerons dans cette page, cette page affichera le nom du film et l'identifiant du film, nous pouvons également choisir le type de film à ajouter à notre bibliothèque. Si le film de la liste de recherche existe déjà dans la bibliothèque, l'utilisateur sera invité à indiquer dans le titre qu'il existe déjà dans la bibliothèque.




## Conception détaillée
Après cela, nous avons discuté des cinq versions de notre sujet, lorsque nous avons fait la proposition initiale du programme pour mettre en œuvre ces cinq versions
&nbsp;

&nbsp;


Version 1
Dans la version 1, nous avons créé une bibliothèque locale de films dans le programme, afin de pouvoir stocker les films que nous avons ajoutés nous-mêmes. Nous allons utiliser le format《 csv》 pour stocker nos films sauvegardés.
&nbsp;

&nbsp;


Version 2
Nous utiliserons la clé API que nous avons obtenue, nous utiliserons la méthode du site web pour nous connecter à la bibliothèque de films sur le site web, après quoi nous obtiendrons l'identifiant du film et le stockerons dans notre propre bibliothèque de films, après quoi nous utiliserons la méthode du site web pour obtenir les détails du film par l'identifiant du film, puis nous afficherons les détails dans notre bibliothèque de films.
&nbsp;

&nbsp;


Version 4
Dans la version 4, nous utiliserons la même méthode que dans les versions 2 et 3, nous obtiendrons la date de sortie du film et le type de film par l'identifiant du film, puis nous l'afficherons dans notre bibliothèque de la même manière.
&nbsp;

&nbsp;


Version 5
Après avoir consulté nos sources, nous sommes toujours dans le flou.
## Réaliser
## Conclure