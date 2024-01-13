package se.giron.moviecenter.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.giron.moviecenter.core.service.MovieService;
import se.giron.moviecenter.model.resource.MovieInfoResource;
import se.giron.moviecenter.model.resource.MovieResource;
import se.giron.moviecenter.model.resource.error.ErrorResponse;
import se.giron.moviecenter.model.resource.filter.MovieFilter;
import org.springframework.data.web.SortDefault;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "Movie", value = "Api for managing movies")
@RestController
@RequestMapping({"/movie"})
public class MovieController {

    public static final int DEFAULT_MAX_PAGE_SIZE = 25;

    @Autowired
    private MovieService movieService;

    @ApiOperation(value = "Fetch list of movies, optionally filtered by search criteria",
            notes = "Note that only an overview of each movie is returned, to see more details about a movie, select that specific movie using the GET method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MovieInfoResource.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/all", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public List<MovieInfoResource> getAllMovies(Sort sort, MovieFilter filter) {
        return movieService.getAllMovies(filter, sort);
    }

    @ApiOperation(value = "Fetch a paged list of movies, optionally filtered by search criteria",
            notes = "Note that only an overview of each movie is returned, to see more details about a movie, select that specific movie using the GET method")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MovieInfoResource.class, responseContainer = "Page"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/list", produces = {APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public Page<MovieInfoResource> getAllMovies(@PageableDefault(size = DEFAULT_MAX_PAGE_SIZE) Pageable page, MovieFilter filter) {
        return movieService.getAllMovies(filter, page);
    }

    @ApiOperation(value = "Fetch single movie by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MovieResource.class),
            @ApiResponse(code = 404, message = "Movie not found"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @GetMapping(value = "/{id}", produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<MovieResource> getMovieById(@PathVariable Long id) {
        Optional<MovieResource> movie = movieService.getMovieById(id);

        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Create new movie")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "OK", response = MovieResource.class),
            @ApiResponse(code = 403, message = "User not allowed to create new movie"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @PostMapping(consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<MovieResource> createMovie(@RequestBody @Valid MovieResource movie) {
        MovieResource createdAuthority = movieService.createMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthority);
    }

    @ApiOperation(value = "Update existing movie, identified by movie entity ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MovieResource.class),
            @ApiResponse(code = 401, message = "No movie with provided ID is found"),
            @ApiResponse(code = 403, message = "User is not allowed to update movie"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @PutMapping(value = "/{id}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<MovieResource> updateMovie(@PathVariable Long id, @RequestBody @Valid MovieResource movie) {
        if (movieService.existsMovieById(id)) {
            movie.setId(id);
            Optional<MovieResource> optionalAuthority = movieService.updateMovie(movie);
            return ResponseEntity.ok(optionalAuthority.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Delete movie, identified by movie entity ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Movie deleted"),
            @ApiResponse(code = 401, message = "No movie with provided ID is found"),
            @ApiResponse(code = 403, message = "User is not allowed to delete movie"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteMovie(@PathVariable Long id) {
        if (movieService.existsMovieById(id)) {
            movieService.deleteMovie(id);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Delete all movies in the database. Non-revokeable! Use only for reset purpose!")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Movies deleted"),
            @ApiResponse(code = 403, message = "User is not allowed to delete movies"),
            @ApiResponse(code = 500, message = "Undefined system error", response = ErrorResponse.class)
    })
    @DeleteMapping()
    public ResponseEntity<Void> deleteAll() {
        movieService.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
