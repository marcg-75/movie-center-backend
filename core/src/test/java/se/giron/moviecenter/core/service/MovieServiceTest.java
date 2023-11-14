package se.giron.moviecenter.core.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import se.giron.moviecenter.core.exception.ValidationException;
import se.giron.moviecenter.core.repository.GenreRepository;
import se.giron.moviecenter.core.repository.MovieRepository;
import se.giron.moviecenter.model.entity.Genre;
import se.giron.moviecenter.model.resource.MovieGenreResource;
import se.giron.moviecenter.model.resource.MovieResource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class MovieServiceTest {

    private static final String MOVIE_TITLE = "test-movie-title";
    private static final String MOVIE_DESCRIPTION = "test-movie-description";
    public static final String GENRE_CODE = "test-genre-code";

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private GenreRepository genreRepository;

    private Validator validator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        when(genreRepository.findById(eq(GENRE_CODE))).thenReturn(Optional.of(new Genre()));
    }


    @Test
    public void shouldReturnEmptyOnGetIFMovieDoesNotExist() {
        when(movieRepository.findById(7L)).thenReturn(Optional.empty());
        Optional<MovieResource> movieResource = movieService.getMovieById(7L);
        assertFalse(movieResource.isPresent());
    }

    /* Annotation validations (might ne moved to model) */

    @Test
    public void shouldValidateResourceOk() {
        Set<ConstraintViolation<MovieResource>> violations = validator.validate(createMovieResource());
        assertEquals(0, violations.size());
    }

    @Test
    public void shouldFailValidationIfTitleIsNull() {
        Set<ConstraintViolation<MovieResource>> violations = validator.validate(createMovieResource().setTitle(null));
        assertEquals(1, violations.size());
    }

    @Test
    public void shouldFailValidationIfDescriptionIsNull() {
        Set<ConstraintViolation<MovieResource>> violations = validator.validate(createMovieResource().setDescription(null));
        assertEquals(1, violations.size());
    }

//    @Test
//    public void shouldFailValidationIfMainGenreCodeIsNull() {
//        MovieResource movieResource = createMovieResource();
//        movieResource.getMainGenre().setCode(null);
//        Set<ConstraintViolation<MovieResource>> violations = validator.validate(movieResource);
//        assertEquals(1, violations.size());
//    }

    @Test(expected = ValidationException.class)
    public void shouldFailValidationIfEmptyTitle() {
        MovieResource movieResource = createMovieResource().setTitle("");
        movieService.validateReferences(movieResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldFailValidationIfNoMainGenre() {
        MovieResource movieResource = createMovieResource().setGenres(new ArrayList<>());
        movieService.validateReferences(movieResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldFailValidationIfEmptyMainGenreCode() {
        MovieResource movieResource = createMovieResource();
        Genre genre = new Genre().setCode("");
        MovieGenreResource mgr = new MovieGenreResource().setGenre(genre).setMovieTitle(movieResource.getTitle());

        movieResource.getGenres().clear();
        movieResource.getGenres().add(mgr);

        movieService.validateReferences(movieResource);
    }

    @Test(expected = ValidationException.class)
    public void shouldFailValidationIfMainGenreUnknown() {
        String genreCode = "genre-code-unknown";
        MovieResource movieResource = createMovieResource();
        Genre genre = new Genre().setCode(genreCode);
        MovieGenreResource mgr = new MovieGenreResource().setGenre(genre).setMovieTitle(movieResource.getTitle());

        movieResource.getGenres().clear();
        movieResource.getGenres().add(mgr);

        when(genreRepository.findById(eq(genreCode))).thenReturn(Optional.empty());
        movieService.validateReferences(movieResource);
    }

    private MovieResource createMovieResource() {
        MovieResource movieResource = new MovieResource()
                .setTitle(MOVIE_TITLE)
                .setDescription(MOVIE_DESCRIPTION);

        Genre genre = new Genre().setCode(GENRE_CODE);
        MovieGenreResource mgr = new MovieGenreResource().setGenre(genre).setMovieTitle(movieResource.getTitle());

        movieResource.getGenres().add(mgr);

        return movieResource;
    }
}