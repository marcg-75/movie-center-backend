package se.giron.moviecenter.core.map.submapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.giron.moviecenter.core.repository.GenreRepository;
import se.giron.moviecenter.core.repository.MovieGenreRepository;
import se.giron.moviecenter.model.entity.*;
import se.giron.moviecenter.model.resource.MovieGenreResource;
import se.giron.moviecenter.model.resource.PersonRoleResource;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenreMapper {

    @Autowired
    private MovieGenreRepository movieGenreRepository;

    @Autowired
    private GenreRepository genreRepository;

    public List<MovieGenreResource> entities2Resources(Set<MovieGenre> movieGenres) {
        if (movieGenres == null || movieGenres.isEmpty()) {
            return null;
        }

        return movieGenres.stream().map(this::entity2Resource).collect(Collectors.toList());
    }

    private MovieGenreResource entity2Resource(MovieGenre movieGenre) {
        return new MovieGenreResource()
                .setMovieTitle(movieGenre.getMovie().getTitle())
                .setGenre(movieGenre.getGenre())
                .setMainGenre(movieGenre.isMainGenre());
    }

    public MovieGenre resource2Entity(MovieGenreResource resource, Movie movie) {
        // Find existing movieGenre.
        List<MovieGenre> movieGenres = movieGenreRepository.findAllByMovieAndGenre(movie.getId(), resource.getGenre().getCode());

        if (movieGenres != null && !movieGenres.isEmpty()) {
            return movieGenres.get(0);
        }

        // Find existing genre (by ID or name)
        Genre genre = null;

        if (resource.getGenre().getCode() != null) {
            Optional<Genre> oGenre = genreRepository.findById(resource.getGenre().getCode());

            if (oGenre.isPresent()) {
                genre = oGenre.get();
            }
        }

        return new MovieGenre()
                .setMovie(movie)
                .setGenre(genre)
                .setMainGenre(resource.isMainGenre());
    }
}
