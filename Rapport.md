<img src="https://raw.githubusercontent.com/Marshellson/Cinematheque/012237b44f73f83f6dd1a0b8080fd51150b10f6d/rapport_image/15232.svg" width="250px" />

# <center> Projet Cinémathèque </center>

Contributeurs: JIANG Yilun, KANG Zhuodong, WANG Haoyu

Site GitHub: [Marshellson/Cinematheque](https://github.com/Marshellson/Cinematheque)

<div style="page-break-after: always;"></div>

## Sommaire

- [Contexte du développement](#contexte-du-développement)

- [Analyse des exigences](#analyse-des-exigences)

- [Conception des contours](#conception-des-contours)

- [Conception détaillée](#conception-détaillée)

- [Réaliser](#réaliser)

- [Conclursion](#conclusion)

<div style="page-break-after: always;"></div>

## Contexte du développement

Dans les années 1830, le cinéma entame sa période de préparation technologique prénatale, appelée aussi période d'invention, avec l'invention de la machine de cinéma optique par Renault en 1888, l'invention du cinématographe par Edison en 1889, et en 1895, alors que l'image se joue lentement, les frères Lumière réussissent à inventer la première génération d'appareils de projection cinématographique, une grande invention dans laquelle le film peut être vu par de nombreuses personnes ensemble et qu'il peut offrir à chacun une expérience à la fois inégalée et étonnante.

Et avec le développement des temps, la technologie cinématographique progresse, les effets des films sont plus réalistes, comme la 3D, la présentation des films et les appareils qui les lisent augmentent, et les films sont sauvegardés de plus en plus de manières, comme le Blu-ray, le DVD, etc., ce qui permet à un plus grand nombre de personnes de regarder des films, et la variété des films est plus abondante, mais comme de plus en plus de films sont réalisés dans le monde,la diversité dérange les gens. Nous ne pouvons pas nous souvenir des films que nous aimons, des films que nous avons regardés, des films que nous possédons, nous ne pouvons pas classer et organiser ces films de manière efficace, et il n'y a aucun programme sur le marché actuellement qui puisse à la fois intégrer sa propre bibliothèque de films et enregistrer les films qu'il possède et ceux qu'il a regardés.

Donc nous pensons qu'il y a un besoin pour un tel programme sur le marché aujourd'hui, et la conception de la programmation devient de plus en plus avancée de nos jours, et la base de données de films devient de plus en plus robuste, et les conditions sont très matures, et dans ce temps, nous avons décidé de choisir ce sujet de projet, et nous allons essayer d'utiliser ce que nous avons appris pour l'améliorer pour atteindre le but de notre projet, et nous serons les premiers à utiliser ce programme.

Ce projet consiste à créer un logiciel permettant de gérer les films/séries vues par des utilisateurs. Le projet est évolutif, c'est à dire que différentes versions peuvent être proposées, allant de la plus simple à la plus compliquée, de façon à ce que tous puissent, à la fin du semestre, proposer un logiciel en état de fonctionnement.

<div style="page-break-after: always;"></div>

## Analyse des exigences

Après d'avoir décidé notre thème, nous avons eu une discussion animée sur notre sujet, et nous avons finalement déterminé nos besoins pour ce programme, et les fonctions du programme ont été résumées comme nécessitant les éléments suivants au total.

### 1 Gérer la bibliothèque de films

Gère les informations de base de tous les films de la bibliothèque, y compris l'ajout, la suppression, etc, et peut organiser les films en fonction de ces informations.

### 2 Gérer la base de données

Gérer les informations de base des films qui nous sont fournies par le site web, y compris les identifiants des films et la possibilité de sélectionner des films à ajouter à la bibliothèque de films. Ensuite, nous avons essayé de dessiner un aperçu de notre interface.

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/image0.jpg?raw=true" />

Cette image est l'interface principale que nous avons dessinée à l'époque, nous avons dessiné l'interface avec notre liste de films sur le côté gauche, les détails des films sur le côté droit, et le titre, le filtre et l'ajout de films sur le côté droit au-dessus, tandis que la fonction de recherche est utilisée sur le côté gauche au-dessus, et nous avons décidé à l'époque que le bouton gauche de la souris peut supprimer les films dont nous n'avons pas besoin.

Après cela, nous nous sommes rendus ensemble sur le site [TheMovieDB](https://developers.themoviedb.org/3). C'est ce site web que nous devons utiliser pour notre thème, nous avons vérifié l'API de ce site web, et après avoir regardé les instructions du site web, nous avons eu une Après avoir regardé les instructions, nous avons discuté et finalement décidé que nous devions utiliser la grande section de films (Movies)de ce site.

Dans ce site, nous obtiendrons les détails de nos films et leurs identifiants. Les identifiants des films permettront d'obtenir facilement des informations sur les films. Mais pour utiliser ce site, nous avons besoin de la clé API, donc nous nous sommes inscrits sur ce site et le site nous a donné la clé API.

<div style="page-break-after: always;"></div>

## Conception des contours

### Interface1: Interface utilisateur principale

Cette interface est globalement la même que celle que nous avons conçue auparavant, à l'exception de quelques modifications. En haut à gauche de l'interface principale se trouve le nom de notre programme (Cinématique) et à côté se trouvent nos deux boutons avec (ComboBox), le bouton (Ajouter) pour ajouter les films que nous possédons, le bouton (Supprimer) pour supprimer nos films et (ComboBox) que nous utilisons pour trier les films de notre cinémathèque. Nous avons pensé qu'il y avait trop peu de fonctionnalités pouvant être ajoutées au bouton droit de la souris, et nous avons donc décidé d'utiliser le bouton à sa place. En bas à gauche de cette interface principale se trouve notre bibliothèque de films(Library), qui affichera les films que nous avons ajoutés , lorsque nous pensons au nom du film ci-dessus, le côté droit de notre interface affichera l'affiche du film avec les détails du film, et en haut à droite de l'interface se trouvent le bouton de recherche et la barre de recherche, après avoir entré le nom du film dans la barre de recherche et cliqué sur le bouton de recherche, nous obtiendrons les résultats de la recherche de films.

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/Interface1.png?raw=true">

### Interface2: Interface de recherche de film par l'utilisateur

Lorsque nous cliquons sur le bouton de recherche de film, nous entrons dans cette interface - interface de recherche.Dans cette interface, le haut de notre interface est les films les plus pertinents que nous obtenons par la recherche de nom de film, lorsque nous cliquons sur le nom du film, l'affiche de ce film et les informations sommaires du film seront affichées en dessous du nom du film. En bas de l'interface de recherche se trouve le bouton d'ajouter, lorsque nous cliquons sur ce bouton, nous entrons dans l'interface d'ajouter.

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/Interface2.png?raw=true">

### Interface3: L'interface d'ajout de film par l'utilisateur

Après avoir cliqué sur le bouton (Ajouter) dans l'interface principale, nous accédons à cette page, dans laquelle nous pouvons saisir le nom du film et choisir le type de film, lorsque nous cliquons sur le bouton(Ajouter), il sera ajouté à notre bibliothèque(Library).

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/Interface3.png?raw=true">

### Interface4: L'interface d'ajout de films après la recherche de films par les utilisateurs

Après avoir cliqué sur le bouton (Ajouter)dans l'interface Rechercher des films, nous entrerons dans cette page, cette page affichera le nom du film et l'identifiant du film, nous pouvons également choisir le type de film à ajouter à notre bibliothèque(Library). Si le film de la liste de recherche existe déjà dans la bibliothèque(Library), l'utilisateur sera invité à indiquer dans le titre qu'il existe déjà dans la bibliothèque(Library).

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/Interface4.png?raw=true">

<div style="page-break-after: always;"></div>

## Conception détaillée

Après cela, nous avons discuté des cinq versions de notre sujet, lorsque nous avons fait la proposition initiale du programme pour mettre en œuvre ces cinq versions.

### Version 1

Dans la version 1, nous avons créé une bibliothèque locale de films(Library) dans le programme, afin de pouvoir stocker les films que nous avons ajoutés. Nous allons utiliser le format CSV pour stocker nos films sauvegardés.

### Version 2 et Version 3

Nous utiliserons la clé API que nous avons obtenue, nous utiliserons la méthode du site web pour nous connecter à la bibliothèque de films (Library)sur le site web, après quoi nous obtiendrons l'identifiant du film et le stockerons dans notre propre bibliothèque de films(Library), après quoi nous utiliserons la méthode du site web pour obtenir les détails du film par l'identifiant du film, puis nous afficherons les détails dans notre bibliothèque de films.

### Version 4

Dans la version 4, nous utiliserons la même méthode que dans les versions 2 et 3, nous obtiendrons la date de sortie du film et le type de film par l'identifiant du film, puis nous l'afficherons dans notre bibliothèque(Library) de la même manière.

### Version 5

Mais après avoir lu les livres d'apprentissage, nous n'avons pas réussi à trouver une solution.

Ensuite, voyons le graphe de dépendance relationnelle de notre programme:

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/UML.png?raw=true">

<div style="page-break-after: always;"></div>

## Réaliser

### Code Principale dans la Version 1

Cette section permet principalement aux utilisateurs d'entrer le film qu'ils veulent voir et de le stocker localement.

Nous voulons que les utilisateurs saisissent directement le film qu'ils veulent ajouter à la liste, nous avons éliminé la détection des films dupliqués afin que les utilisateurs puissent entrer le film qu'ils veulent voir plusieurs fois. Le film est stocké au format de document CSV, le fichier divise le film à l'aide d'un nouveau caractère de ligne `\n` et du symbole `,` divise l'information sur le film, y compris le nom du film, l'ID correspondant du film sur le site [TheMovieDB](https://developers.themoviedb.org/3), aussi le mode de stockage du film et l'année de sortie du film.

```java

// textFieldFilmNameAddFilmToTxt stocke le nom du film que l'utilisateur veut ajouter, comboBoxFilmModeAddFilmToTxt
// stocke le mode du film que l'utilisateur veut ajouter
// Check if the length of film name is 0, if then return error.

    if(textFieldFilmNameAddFilmToTxt.getText().length()==0){
        JOptionPane.showMessageDialog(null,"You must enter film name!","ERROR",JOptionPane.PLAIN_MESSAGE);
        return;
    }

        // Write Film information into txt file, and add film into list model to show on screen.
    try{

        FileWriter fw=new FileWriter(absoultePath,true);
        fw.write("\n"+textFieldFilmNameAddFilmToTxt.getText()+","+comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString()+","+0+","+0);
        fw.close();
        
        listModel.addElement(new ListModelElement(textFieldFilmNameAddFilmToTxt.getText(),comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString()));
        listFilmInList.add(new LesFilmsInList(textFieldFilmNameAddFilmToTxt.getText(),comboBoxFilmModeAddFilmToTxt.getSelectedItem().toString(),0,"0"));
        frameAddFilmToTxt.setVisible(false);

    }catch(IOException ioException){
        ioException.printStackTrace();
    }
```

### Code principale pour les versions 2, 3 et 4

Les versions 2, 3 et 4 utilisent principalement l'API [TheMovieDB](https://developers.themoviedb.org/3), cette API nous aide à obtenir toutes sortes d'informations sur le film, y compris le nom du film, l'ID du film, ainsi que les genres de film, l'année de sortie etc.

Nous utilisons [Retrofit](https://square.github.io/retrofit/) pour appliquer l'API http à notre projet, nous utilisons [`Call`](https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html) pour envoyer notre demande

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/Retrofit%20Call.png?raw=true">

Et nous pouvons obtenir les données retournées [`response`](https://square.github.io/retrofit/2.x/retrofit/retrofit2/Response.html) [`.body()`](https://square.github.io/retrofit/2.x/retrofit/retrofit2/Response.html#body--)

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/Retrofit%20Response.png?raw=true">

Nous recherchons le film en utilisant le nom du film entré par l'utilisateur, avec le API key et le text query pour faire les recherche.

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/SearchMovie%20TheMovieDB.png?raw=true">

```java
    @GET("3/search/movie")
    Call<SearchMovie> get_movie(
@Query("api_key") String apiKey,
@Query("query") String query
        );
```

```java
Call<SearchMovie> searchMovieCall=apiInterface.get_movie(utils.API_KEY,str);
searchMovieCall.enqueue(new Callback<SearchMovie>(){
@Override
// Invoked for a received HTTP response.
public void onResponse(Call<SearchMovie> call,Response<SearchMovie> response){
    SearchMovie searchMovie=response.body();
}

public void onFailure(Call<SearchMovie> call,Throwable throwable){
    throwable.getMessage();
}
```

Par exemple, nous rechercherons des informations sur le film Iron Man, et donc le query_string ici est "Iron man", nous envoyons la demande avec le lien suivant: `https://api.themoviedb.org/3/search/movie?api_key=89e5521b3e8381cf6adc8f4c8432e07d&language=en-US&query=Iron%20man&page=1&include_adult=false`

Le site [Postman](https://www.postman.com) nous permet de Debug facilement notre réponse (`response.body()`), nous envoyons le lien que nous avons obtenue à [Postman](https://www.postman.com) et obtenons les résultats suivants:

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/Postman%20Resultat%201.png?raw=true">

Donc nous pouvons facilement obtenir chaque l'ID du film que nous avons cherché.

Nous stockons ces ID dans une liste, et nous ajoutons un `ListSelectionListener` à notre liste afin que lorsque les utilisateurs changent leurs sélections, nous puissions obtenir leurs identifiants de film sélectionnés et rechercher des informations de film par rapport d'ID.

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/TheMovieDB%20Get%20Detail.png?raw=true">

De même, nous utilisons cette méthode pour obtenir les détails du film sur l'ID du film que nous stockons dans le fichier.

```java
listSearchRemote.addListSelectionListener(new ListSelectionListener(){
@Override
public void valueChanged(ListSelectionEvent e){

    // Get the value only when the left key is released.
    if(e.getValueIsAdjusting()){
        return
    };

    // Instantiate the movie info which selected.
    SearchResult searchResultSelect=searchResultArrayList.get(0);
    try{
        searchResultSelect=searchResultArrayList.get(listSearchRemote.getSelectedIndex());
    }catch(Exception exception){
        exception.printStackTrace();
    }

    int searchResultFilmId=searchResultSelect.iddefilm;
    String searchResultFilmNom=searchResultSelect.nomdefilm;

        // Use the movie id which we got to get movie info.
        Call<Movie.movie_detail>callMovieDetail=apiInterface.get_movie_by_id(searchResultFilmId,utils.API_KEY);

        callMovieDetail.enqueue(new Callback<Movie.movie_detail>(){
@Override
public void onResponse(Call<Movie.movie_detail>call,Response<Movie.movie_detail>response){
        Movie.movie_detail movieDetail=response.body();
        }catch(IOException ioException){
//  ioException.printStackTrace();
        jLabelPostImage.setText("This movie has no post image");
        }
@Override
public void onFailure(Call<Movie.movie_detail>call,Throwable throwable){
        JOptionPane.showMessageDialog(null,"You must enter film name!","ERROR",JOptionPane.PLAIN_MESSAGE);
        }
```

<div style="page-break-after: always;"></div>

## Conclusion

### Processus du projet

En effet, pour mener à bien ce projet, nous avons fait le diagramme de Gantt au début et attribué la tâche de chacun.

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/Diagramme%20de%20Gantt%202.png?raw=true">

Jiang est responsable de la programmation générale, Wang est le conseilleur qui donne des suggestions et des modifications, et Kang fait de la relecture finale et de la création du rapport. 

Et voici la Diagramme de Gante effectif:

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/Diagramme%20de%20Gantt%201.png?raw=true">

Après une période de travail, nous avons établi un premier design : 

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/image0.jpg?raw=true">

Ensuite, nous avons fait un total de cinq versions, de la version 1 à la version 5, au fur et à mesure que nous recherchions des informations et étudiions en ligne. La différence entre eux et l'aperçu est visible dans la figure suivante :

<img src="https://github.com/Marshellson/Cinematheque/blob/main/rapport_image/Interface1.png?raw=true">

Vous pouvez voir les changements apportés à l'interface ainsi qu'à l'IU, comme l'emplacement des affiches de films, la différence dans la façon dont les informations sur les films sont affichées et l'emplacement des informations sur le type de stockage des films.

Après avoir discuté les uns avec les autres, nous avons eu le sentiment que l'environnement de travail cordial et la communication de groupe efficace nous ont permis non seulement de ne pas nous gêner les uns les autres, mais aussi de compenser les faiblesses et les lacunes de chacun afin que notre projet puisse se dérouler plus facilement et atteindre cette vitesse. Et travailler avec des camarades qui sont plus avancés en programmation peut également être très bénéfique et améliorer nos compétences globales en programmation. Ce projet a également permis d'accroître notre expérience dans la création d'interfaces logicielles, ce qui nous permettra de nous orienter plus rapidement à l'avenir.