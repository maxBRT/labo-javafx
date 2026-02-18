# Gestionnaire de Films

Application JavaFX pour gérer une bibliothèque personnelle de films et séries, avec intégration d'une API externe pour la découverte de contenus populaires.

---

## Fonctionnalités

- Parcourir et gérer sa bibliothèque personnelle de films et séries
- Ajouter, modifier et supprimer des contenus avec leurs métadonnées (synopsis, réalisateur, année, affiche, notes personnelles)
- Rechercher et filtrer par titre, genre et type (Film / Série)
- Suivre le statut de visionnage : *À voir*, *En cours*, *Vu*
- Attribuer une note de 0 à 5
- Découvrir les films populaires via l'API TMDB et les ajouter directement à sa bibliothèque

---
## Structure du projet

```
films/src/main/java/com/github/maxbrt/films/
├── model/            # Entités JPA et DTOs pour l'API
├── repository/       # Couche d'accès aux données (AbstractRepository, ContenuRepository, GenreRepository)
├── service/          # Client HTTP et service TMDB (ApiClient, ApiService)
├── controllers/      # Contrôleurs JavaFX (Main, List, Form, Discover)
└── components/       # Composants UI réutilisables (ContenuCard)

films/src/main/resources/
├── main-view.fxml    # Layout principal (TabPane)
├── list-view.fxml    # Onglet bibliothèque
├── form-view.fxml    # Formulaire d'ajout / modification
├── discover-view.fxml # Onglet découverte
└── hibernate.cfg.xml # Configuration Hibernate
```

---

## Technologies utilisées

| Technologie | Version | Rôle |
|---|---|---|
| **Java** | 25 | Langage principal |
| **JavaFX** | 21.0.6 | Framework UI |
| **Hibernate ORM** | 6.6.5 | Mapping objet-relationnel |
| **PostgreSQL** | 42.7.3 (JDBC) | Base de données (hébergée sur Neon.tech) |
| **TMDB API** | - | Source de données films populaires |
| **Jackson** | 2.18.2 | Sérialisation / désérialisation JSON |
| **AtlantaFX** | 2.0.1 | Thème moderne (CupertinoDark) |
| **dotenv-java** | 3.2.0 | Gestion des variables d'environnement |
| **Maven** | - | Gestion de build et dépendances |

---

## Décisions techniques

### Threads virtuels (Java 21+)

Pour toutes les opérations bloquantes (requêtes base de données, appels API) j'ai utiliser les virtual threads (`Thread.ofVirtual().start(...)`). Les mises à jour de l'UI renvoyées sur le thread JavaFX avec `Platform.runLater(...)`. J'ai été surpris par la simpliciter des virtual thread, très moderne et user friendly. Le `Platform.runLater(...)` est aussi très utile je serais curieux de savoir comment c'est implémente mais l'abstraction est vraiment imprésionnante.

### Repository générique

Bon je pense que c'est sur cette partie que je doit des explications. Initialement, j'utilisais JDBC avec SQL pour mes requêtes. J'avais un client pour la connexion et un service qui gérait les requêtes. Pour le CRUD et le mapping des lignes vers mes modèles, j'en étais déjà à environ 500 lignes de code.

Sachant que je pouvais optimiser cela et voulant approfondir l'écosystème Java (mon objectif étant de devenir développeur backend en grande entreprise), j'ai recherché les standards de gestion de données et j'ai découvert [JPA](https://jakarta.ee/). Jakarta Persistence est la spécification standard pour les ORM en Java. En d'autres mots, les ORM ciblent ce format pour traduire les données en objets. C'est vraiment intéressant parceque, avec mon implémentation actuelle, je pourrais changer d'ORM sans modifier mes modèles, tant qu'il respecte cette norme.

J'ai donc implémenté l'ORM Hibernate. Cela m'a permis de créer un `AbstractRepository` générique. Maintenant, il me suffit de créer un repository spécifique pour chaque modèle en étendant cette classe de base pour obtenir immédiatement toutes les opérations CRUD. Même si j'ai seulement deux modèles pour l'instant, l'application est devenue beaucoup plus extensible.

### Intégration API (TMDB)

Après avoir relu l'énoncé, je me suis rendu compte que j'ai encore fait à ma tête... Ma tête dur est à la fois ma plus grande force et mon plus grand défaut! Pour satisfaire l'énoncé, j'ai donc fait preuve d'un peu de créativité.

Pour la classe `ApiClient`, j'ai créé un client HTTP qui expose une méthode permettant d'envoyer facilement des requêtes authentifiées avec une clé API. J'ai ensuite abstrait ce client via la classe `ApiService`. Elle expose une méthode `getPopularMovies()` qui traduit la réponse de l'API en modèles utilisables par l'application.

Cette intégration m'a permis d'explorer la communication HTTP en Java et l'utilisation du package Jackson pour la désérialisation du JSON.

Je comprends que ce n'était pas la consigne initiale, mais j'espère que cette approche démontrant ma compréhension des services et de l'encapsulation me vaudra quelques points !

### Composant réutilisable `ContenuCard`

Probalement la partie de l'application qui à vue le plus de refactoring lol! Au début, j'avais une methode `buildCards()`qui construisait toutes les cartes de contenu, avec tous les bouttons et les action sur la carte. C`est vite devenu ingérable donc je l'ai refactoriser en classe qui hérite de `VBox`. Ça à beaucoup alèger mon controlleur, je passais des callback au constructeur pour les actions des buttons. Quand j'ai décider d'impémenter la vue discover qui elle aussi avais besoin de carte mais pas tout à fais la même j'ai eu un bon back and forth avec Gemini et j'ai compris un des gros avantages de travailler avec un structure d'arbre (FXML): la composition 🤯. Donc finalement ma carte accepte une Node pour la partie du bas avec des actions. Elle ne sais pas ce que la node fait ou ce qu'il y à dans la node, c'est le controlleur qui s'occupe de tout ca. J'ai particulièrement aimé ce design!


### Communication entre contrôleurs

Cette partie a été la plus grande source de "spaghettis" dans mon code. À la base, les contrôleurs dépendaient les uns des autres (dépendance circulaire) : ils se passaient des données et appelaient des méthodes entre eux... Bref, c'était pas beau !

C'est pourquoi j'ai créé le `MainController`. Il sert d'aiguillage pour la communication entre les contrôleurs et prend en charge certaines actions liées à la navigation entre les onglets.

---

## Prérequis et lancement

### Variables d'environnement

copier le fichier `.env` fournie à la racine du projet
copier le fichier `hibernate.cfg.xml` fournie dans le dossier `src/main/resources`.

### Lancer avec les données de démonstration

```bash
mvn clean compile javafx:run -Djavafx.args="--seed"
```

> Le seed crée des catégories de base pour utiliser l'application, ensuite l'api TMDB en ajoute progressivement.

### Lancer sans le seed

```bash
mvn clean compile javafx:run
```

## L'intelligence artificielle a été utilisée dans ce projet pour :
- Déboguer le code.
- Conseils sur les meilleures pratiques.
- Proposer des suggestions de refactorisation.
- Assister à la résolution de problèmes techniques.
- Générer les données de test (seed) pour la base de données.
- Corriger les fautes d'orthographe dans la documentation.


