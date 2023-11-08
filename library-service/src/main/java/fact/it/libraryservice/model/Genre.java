package fact.it.libraryservice.model;

import java.util.Random;

public enum Genre {
    ROCK,
    POP,
    JAZZ,
    HIP_HOP,
    COUNTRY,
    CLASSICAL;

    private static final Random random = new Random();

    public static Genre randomGenre()  {
        Genre[] genres = values();
        return genres[random.nextInt(genres.length)];
    }
}
