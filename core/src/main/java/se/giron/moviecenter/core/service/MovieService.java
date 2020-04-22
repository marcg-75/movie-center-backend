package se.giron.moviecenter.core.service;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.giron.moviecenter.core.exception.MovieNotFoundException;
import se.giron.moviecenter.core.exception.ValidationException;
import se.giron.moviecenter.core.repository.GenreRepository;
import se.giron.moviecenter.core.repository.MovieFilterSpecification;
import se.giron.moviecenter.core.repository.MovieRepository;
import se.giron.moviecenter.model.entity.Genre;
import se.giron.moviecenter.model.entity.Movie;
import se.giron.moviecenter.model.map.MovieMapper;
import se.giron.moviecenter.model.resource.MovieInfoResource;
import se.giron.moviecenter.model.resource.MovieResource;
import se.giron.moviecenter.model.resource.filter.MovieFilter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public List<MovieInfoResource> getAllMovies(MovieFilter filter) {
        return getFilteredMovies(filter).stream().map(MovieMapper::entity2infoResource).collect(Collectors.toList());
    }

    private List<Movie> getFilteredMovies(MovieFilter filter) {
        return movieRepository.findAll(new MovieFilterSpecification(filter));
    }

    @Transactional(readOnly = true)
    public Optional<MovieResource> getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);

        if (movie.isPresent()) {
            MovieResource movieResource = MovieMapper.entity2resource(movie.get());
            return Optional.ofNullable(movieResource);
        }
        return Optional.empty();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public MovieResource createMovie(MovieResource movieResource) {
        Movie movie = persist(movieResource, new Movie());

        MovieResource createdMovie = MovieMapper.entity2resource(movie);
        return Optional.of(createdMovie).get();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Optional<MovieResource> updateMovie(MovieResource movieResource) {
        Optional<Movie> movie = movieRepository.findById(movieResource.getId());

        if (movie.isPresent()) {
            final Movie updatedMovie = persist(movieResource, movie.get());
            MovieResource movieResourceUpd = MovieMapper.entity2resource(updatedMovie);
            return Optional.ofNullable(movieResourceUpd);
        }
        return Optional.empty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void deleteMovie(final Long id) {
        movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));

        movieRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsMovieById(long movieId) {
        return movieRepository.existsById(movieId);
    }

    private Movie persist(MovieResource movieResource, Movie movie) {
        validateAndResolveReferences(movieResource);

        Movie movieToUpdate = MovieMapper.resource2entity(movieResource, movie);
        return movieRepository.save(movieToUpdate);
    }

    protected void validateAndResolveReferences(MovieResource movieResource) {
        if (StringUtils.isEmpty(movieResource.getTitle())) {
            throw new ValidationException("movie.title.notnull");
        }
        validateAndResolveMainGenre(movieResource);
    }

    private void validateAndResolveMainGenre(MovieResource movieResource) {
        if (movieResource.getMainGenre() == null || StringUtils.isEmpty(movieResource.getMainGenre().getCode())) {
            throw new ValidationException("movie.genre.notnull");
        }

        Optional<Genre> genre = genreRepository.findById(movieResource.getMainGenre().getCode());

        if (genre.isPresent()) {
            movieResource.setMainGenre(genre.get());
        } else {
            throw new ValidationException("movie.genre.unknown", movieResource.getMainGenre().getCode());
            // TODO: Add new genre if unknown?
        }
    }
}
