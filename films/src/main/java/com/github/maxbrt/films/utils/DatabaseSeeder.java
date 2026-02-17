package com.github.maxbrt.films.utils;

import com.github.maxbrt.films.model.Contenu;
import com.github.maxbrt.films.model.Genre;
import com.github.maxbrt.films.repository.ContenuRepository;
import com.github.maxbrt.films.repository.GenreRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DatabaseSeeder {

    public static void flush() {
        ContenuRepository contenuRepo = new ContenuRepository(HibernateUtil.getSessionFactory(), Contenu.class);
        GenreRepository genreRepo = new GenreRepository(HibernateUtil.getSessionFactory(), Genre.class);

        // Contenus first (FK on genre)
        contenuRepo.deleteAll();
        genreRepo.deleteAll();
        System.out.println("Base vidée.");
    }

    public static void seedGenres() {
        GenreRepository genreRepo = new GenreRepository(HibernateUtil.getSessionFactory(), Genre.class);
        List<String> genres = List.of(
                "Action",
                "Aventure",
                "Comédie",
                "Drame",
                "Horreur",
                "Science-fiction",
                "Thriller",
                "Animation",
                "Documentaire",
                "Fantastique",
                "Romance",
                "Policier");

        for (String nom : genres) {
            genreRepo.save(new Genre(nom));
        }

        System.out.println(genres.size() + " genres insérés.");
    }

    public static void seedContenus() {
        ContenuRepository contenuRepo = new ContenuRepository(HibernateUtil.getSessionFactory(), Contenu.class);
        GenreRepository genreRepo = new GenreRepository(HibernateUtil.getSessionFactory(), Genre.class);
        Map<String, Genre> genres = genreRepo.findAll().stream()
                .collect(Collectors.toMap(Genre::getNom, g -> g));

        // Poster images from TMDB (image.tmdb.org)
        contenuRepo.save(new Contenu(
                "Inception", "Film", 2010, "Christopher Nolan",
                "Un voleur expérimenté dans l'art de l'extraction de secrets enfouis dans le subconscient est chargé d'implanter une idée dans l'esprit d'un homme.",
                "https://image.tmdb.org/t/p/w500/xlaY2zyzMfkhk0HSC5VUwzoZPU1.jpg",
                "Vu", 5, false, genres.get("Science-fiction")));

        contenuRepo.save(new Contenu(
                "Interstellar", "Film", 2014, "Christopher Nolan",
                "Une équipe d'explorateurs voyage à travers un trou de ver dans l'espace pour assurer la survie de l'humanité.",
                "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
                "Vu", 5, false, genres.get("Science-fiction")));

        contenuRepo.save(new Contenu(
                "The Dark Knight", "Film", 2008, "Christopher Nolan",
                "Batman affronte le Joker, un criminel anarchiste qui plonge Gotham City dans le chaos.",
                "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                "Vu", 5, false, genres.get("Action")));

        contenuRepo.save(new Contenu(
                "Pulp Fiction", "Film", 1994, "Quentin Tarantino",
                "Les vies de deux tueurs à gages, d'un boxeur, d'un gangster et de sa femme s'entremêlent dans quatre histoires de violence et de rédemption.",
                "https://image.tmdb.org/t/p/w500/vQWk5YBFWF4bZaofAbv0tShwBvQ.jpg",
                "Vu", 5, false, genres.get("Policier")));

        contenuRepo.save(new Contenu(
                "Fight Club", "Film", 1999, "David Fincher",
                "Un employé de bureau insomniaque et un vendeur de savon fondent un club de combat clandestin qui évolue en quelque chose de bien plus grand.",
                "https://image.tmdb.org/t/p/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
                "Vu", 4, false, genres.get("Drame")));

        contenuRepo.save(new Contenu(
                "The Shawshank Redemption", "Film", 1994, "Frank Darabont",
                "Deux hommes emprisonnés se lient d'amitié au fil des années, trouvant du réconfort et une rédemption éventuelle à travers des actes de décence commune.",
                "https://image.tmdb.org/t/p/w500/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
                "Vu", 5, false, genres.get("Drame")));

        contenuRepo.save(new Contenu(
                "Matrix", "Film", 1999, "Lana et Lilly Wachowski",
                "Un pirate informatique découvre que la réalité telle qu'il la connaît est une simulation créée par des machines.",
                "https://image.tmdb.org/t/p/w500/p96dm7sCMn4VYAStA6siNz30G1r.jpg",
                "Vu", 4, false, genres.get("Science-fiction")));

        contenuRepo.save(new Contenu(
                "Le Parrain", "Film", 1972, "Francis Ford Coppola",
                "Le patriarche vieillissant d'une dynastie du crime organisé transfère le contrôle de son empire clandestin à son fils réticent.",
                "https://image.tmdb.org/t/p/w500/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
                "A voir", 0, true, genres.get("Drame")));

        contenuRepo.save(new Contenu(
                "Le Seigneur des Anneaux : La Communauté de l'Anneau", "Film", 2001, "Peter Jackson",
                "Un hobbit de la Comté et huit compagnons se lancent dans un voyage pour détruire l'Anneau Unique et le Seigneur des Ténèbres Sauron.",
                "https://image.tmdb.org/t/p/w500/6oom5QYQ2yQTMJIbnvbkBL9cHo6.jpg",
                "Vu", 5, false, genres.get("Fantastique")));

        contenuRepo.save(new Contenu(
                "Forrest Gump", "Film", 1994, "Robert Zemeckis",
                "Les présidences de Kennedy et Johnson, la guerre du Vietnam et d'autres événements historiques se déroulent à travers le regard d'un homme de l'Alabama au QI limité.",
                "https://image.tmdb.org/t/p/w500/saHP97rTPS5eLmrLQEcANmKrsFl.jpg",
                "Vu", 4, false, genres.get("Drame")));

        contenuRepo.save(new Contenu(
                "Star Wars : Un Nouvel Espoir", "Film", 1977, "George Lucas",
                "Luke Skywalker rejoint des forces rebelles pour sauver la princesse Leia de l'Empire maléfique et sauver la galaxie.",
                "https://image.tmdb.org/t/p/w500/6FfCtAuVAW8XJjZ7eWeLibRLWTw.jpg",
                "Vu", 4, false, genres.get("Aventure")));

        contenuRepo.save(new Contenu(
                "Toy Story", "Film", 1995, "John Lasseter",
                "Un cow-boy en jouet est profondément menacé et jaloux quand un nouveau jouet astronaute le supplante comme jouet préféré dans la chambre d'un garçon.",
                "https://image.tmdb.org/t/p/w500/uXDfjJbdP4ijW5hWSBrPrlKpxab.jpg",
                "Vu", 4, false, genres.get("Animation")));

        contenuRepo.save(new Contenu(
                "Le Silence des Agneaux", "Film", 1991, "Jonathan Demme",
                "Une jeune stagiaire du FBI doit recevoir l'aide d'un meurtrier cannibale emprisonné pour attraper un autre tueur en série.",
                "https://image.tmdb.org/t/p/w500/uS9m8OBk1A8eM9I042bx8XXpqAq.jpg",
                "A voir", 0, true, genres.get("Thriller")));

        contenuRepo.save(new Contenu(
                "Se7en", "Film", 1995, "David Fincher",
                "Deux détectives traquent un tueur en série qui utilise les sept péchés capitaux comme motifs de ses meurtres.",
                "https://image.tmdb.org/t/p/w500/191nKfP0ehp3uIvWqgPbFmI4lv9.jpg",
                "En cours", 4, false, genres.get("Thriller")));

        contenuRepo.save(new Contenu(
                "La Ligne verte", "Film", 1999, "Frank Darabont",
                "Les vies des gardiens dans le couloir de la mort d'une pénitentiaire sont bouleversées par l'arrivée d'un prisonnier noir doté de pouvoirs surnaturels.",
                "https://image.tmdb.org/t/p/w500/8VG8fDNiy50H4FedGwdSVUPoaJe.jpg",
                "A voir", 0, true, genres.get("Drame")));

        contenuRepo.save(new Contenu(
                "Orange mécanique", "Film", 1971, "Stanley Kubrick",
                "Dans une Angleterre futuriste, un jeune délinquant charismatique est soumis à une technique expérimentale de réhabilitation psychologique.",
                "https://image.tmdb.org/t/p/w500/4sHeTAp65WrSSuc05nRBKddhBxO.jpg",
                "A voir", 0, false, genres.get("Science-fiction")));

        System.out.println("16 films insérés.");
    }
}
