CREATE TABLE movie_member (
    id          VARCHAR(30) PRIMARY KEY,
    password    VARCHAR(255) NOT NULL,
    name        VARCHAR(50) NOT NULL,
    enabled     TINYINT(1)   DEFAULT 1,
    rolename    VARCHAR(30)  DEFAULT 'ROLE_USER'
);

CREATE TABLE movie_info (
    movie_num   INT AUTO_INCREMENT PRIMARY KEY,
    id          VARCHAR(30) NOT NULL,
    title       VARCHAR(1000) NOT NULL,
    director    VARCHAR(100) NOT NULL,
    release_year INT DEFAULT 0,
    description TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_movie_info_member FOREIGN KEY (id) REFERENCES movie_member(id)
);

CREATE TABLE movie_rating (
    rating_num  INT AUTO_INCREMENT PRIMARY KEY,
    movie_num   INT NOT NULL,
    id          VARCHAR(30) NOT NULL,
    score       INT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_movie_rating_movie FOREIGN KEY (movie_num) REFERENCES movie_info(movie_num),
    CONSTRAINT fk_movie_rating_member FOREIGN KEY (id) REFERENCES movie_member(id),
    CONSTRAINT uk_movie_rating UNIQUE (movie_num, id)
);
