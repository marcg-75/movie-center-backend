package se.giron.moviecenter.core.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.giron.moviecenter.core.exception.MovieNotFoundException;
import se.giron.moviecenter.core.exception.ValidationException;
import se.giron.moviecenter.core.repository.GenreRepository;
import se.giron.moviecenter.core.repository.MovieFilterSpecification;
import se.giron.moviecenter.core.repository.MovieRepository;
import se.giron.moviecenter.core.repository.PersonRepository;
import se.giron.moviecenter.model.entity.Genre;
import se.giron.moviecenter.model.entity.Movie;
import se.giron.moviecenter.core.map.MovieMapper;
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

    @Autowired
    private MovieMapper movieMapper;

    @Transactional(readOnly = true)
    public List<MovieInfoResource> getAllMovies(MovieFilter filter, Sort sort) {
        return getFilteredMovies(filter, sort).stream().map(movieMapper::entity2infoResource).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<MovieInfoResource> getAllMovies(MovieFilter filter, Pageable page) {
        return getFilteredMovies(filter, page).map(movieMapper::entity2infoResource);
    }

    private List<Movie> getFilteredMovies(MovieFilter filter, Sort sort) {
        return movieRepository.findAll(new MovieFilterSpecification(filter), sort);
    }

    private Page<Movie> getFilteredMovies(MovieFilter filter, Pageable page) {
        return movieRepository.findAll(new MovieFilterSpecification(filter), page);
    }

    @Transactional(readOnly = true)
    public Optional<MovieResource> getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);

        if (movie.isPresent()) {
            MovieResource movieResource = movieMapper.entity2resource(movie.get());
            return Optional.ofNullable(movieResource);
        }
        return Optional.empty();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public MovieResource createMovie(MovieResource movieResource) {
        Movie movie = persist(movieResource, new Movie());

        MovieResource createdMovie = movieMapper.entity2resource(movie);
        return Optional.of(createdMovie).get();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public Optional<MovieResource> updateMovie(MovieResource movieResource) {
        Optional<Movie> oMovie = movieRepository.findById(movieResource.getId());

        if (oMovie.isPresent()) {
            Movie movie = oMovie.get();
            final Movie updatedMovie = persist(movieResource, movie);
            MovieResource movieResourceUpd = movieMapper.entity2resource(updatedMovie);
            return Optional.ofNullable(movieResourceUpd);
        }
        return Optional.empty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void deleteMovie(final Long id) {
        movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundException(id));

        movieRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void deleteAll() {
        movieRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public boolean existsMovieById(long movieId) {
        return movieRepository.existsById(movieId);
    }

    private Movie persist(MovieResource movieResource, Movie movie) {
        validateAndResolveReferences(movieResource);

        Movie movieToUpdate = movieMapper.resource2entity(movieResource, movie);
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
        }
    }
}
