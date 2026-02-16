-- PG ne supporte pas IF NOT EXISTS sur les types lol!
DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'statut_enum') THEN
        CREATE TYPE statut_enum AS ENUM ('VU', 'A_VOIR', 'EN_COURS');
    END IF;
    
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'type_enum') THEN
        CREATE TYPE type_enum AS ENUM ('FILM', 'SERIE');
    END IF;
END $$;


-- Table des genres
CREATE TABLE IF NOT EXISTS genres (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50) NOT NULL UNIQUE
);

-- Table des contenus (films et series)
CREATE TABLE IF NOT EXISTS contenus (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(150) NOT NULL,
    type type_enum NOT NULL,
    annee_sortie INTEGER,
    realisateur VARCHAR(100),
    synopsis TEXT,
    image_url VARCHAR(255),
    genre_id INTEGER REFERENCES genres(id),
    statut statut_enum DEFAULT 'A_VOIR',
    note INTEGER DEFAULT 0 CHECK (note >= 0 AND note <= 5),
    watchlist BOOLEAN DEFAULT FALSE,
    date_ajout TIMESTAMP DEFAULT NOW()
);

-- Table de progression des series
CREATE TABLE IF NOT EXISTS progression_series (
    id SERIAL PRIMARY KEY,
    contenu_id INTEGER REFERENCES contenus(id) ON DELETE CASCADE,
    saisons_totales INTEGER DEFAULT 1,
    saisons_vues INTEGER DEFAULT 0,
    episodes_totaux INTEGER DEFAULT 1,
    episodes_vus INTEGER DEFAULT 0
);
