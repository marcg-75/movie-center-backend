package se.giron.moviecenter.core.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.giron.moviecenter.core.map.submapper.*;
import se.giron.moviecenter.model.entity.CastAndCrew;
import se.giron.moviecenter.model.entity.Movie;
import se.giron.moviecenter.model.enums.RoleEnum;
import se.giron.moviecenter.model.resource.CastAndCrewResource;
import se.giron.moviecenter.model.resource.MovieInfoResource;
import se.giron.moviecenter.model.resource.MovieResource;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private CastAndCrewMapper castAndCrewMapper;

    @Autowired
    private MovieFormatInfoMapper movieFormatInfoMapper;

    @Autowired
    private StudioMapper studioMapper;

    public MovieResource entity2resource(Movie movie) {
        MovieResource resource = new MovieResource()
                .setId(movie.getId())
                .setTitle(movie.getTitle())
                .setOriginalTitle(movie.getOriginalTitle())
                .setDescription(movie.getDescription())
                .setGenres(genreMapper.entities2Resources(movie.getGenres()))
                .setRuntime(movie.getRuntime())
                .setReleaseDate(movie.getReleaseDate())
                .setCountry(movie.getCountry())
                .setAgeRestriction(movie.getAgeRestriction())
                .setImdbId(movie.getImdbId())
                .setStudios(movie.getStudios())
                .setMovieFormatInfo(movieFormatInfoMapper.entity2Resource(movie.getMovieFormatInfo()))
                .setMoviePersonalInfo(MoviePersonalInfoMapper.entity2Resource(movie.getMoviePersonalInfo()));

        addCastAndCrewToResource(resource, movie.getCastAndCrew());

        return resource;
    }

    public Movie resource2entity(MovieResource movieResource, Movie movie) {
        movie.setTitle(movieResource.getTitle())
                .setOriginalTitle(movieResource.getOriginalTitle())
                .setDescription(movieResource.getDescription())
                .setRuntime(movieResource.getRuntime())
                .setReleaseDate(movieResource.getReleaseDate())
                .setCountry(movieResource.getCountry())
                .setAgeRestriction(movieResource.getAgeRestriction())
                .setImdbId(movieResource.getImdbId())

                .setMovieFormatInfo(movieFormatInfoMapper.resource2Entity(movieResource.getMovieFormatInfo(), movie))
                .setMoviePersonalInfo(MoviePersonalInfoMapper.resource2Entity(movieResource.getMoviePersonalInfo(), movie));

        // Genres
        movie.getGenres().clear();
        if (movieResource.getGenres() != null && !movieResource.getGenres().isEmpty()) {
            movie.getGenres().addAll(movieResource.getGenres().stream()
                    .map(mgr -> genreMapper.resource2Entity(mgr, movie))
                    .collect(Collectors.toSet()));

        }

        mergeCastAndCrewToEntity(movieResource, movie);

        // Studios
        movie.getStudios().clear();
        if (movieResource.getStudios() != null && !movieResource.getStudios().isEmpty()) {
            movie.getStudios().addAll(movieResource.getStudios().stream()
                    .map(studioMapper::resource2Entity)
                    .collect(Collectors.toSet()));
        }

        return movie;
    }

    private void addCastAndCrewToResource(MovieResource resource, Set<CastAndCrew> allCac) {
        List<CastAndCrewResource> allCacResourcesSorted = castAndCrewMapper.entities2Resources(allCac);

        if (allCacResourcesSorted == null) {
            return;
        }

        allCacResourcesSorted.stream().forEach(cac -> {
            switch (RoleEnum.valueOf(cac.getPersonRole().getRole().getCode())) {
                case ACTOR:
                    resource.getActors().add(cac);
                    break;
                case DIRECTOR:
                    resource.getDirectors().add(cac);
                    break;
                case PRODUCER:
                    resource.getProducers().add(cac);
                    break;
                case MUSIC:
                    resource.getMusic().add(cac);
                    break;
                case WRITER:
                    resource.getWriters().add(cac);
                    break;
                case CASTING:
                    resource.getCasters().add(cac);
                    break;
                case EDITOR:
                    resource.getEditors().add(cac);
                    break;
                case CINEMATOGRAPHY:
                    resource.getCinematography().add(cac);
                    break;
                case SOUND:
                    resource.getSound().add(cac);
                    break;
                case ART:
                    resource.getArt().add(cac);
                    break;
                case MISC:
                    resource.getOtherRoles().add(cac);
                    break;
            }
        });
    }

    private void mergeCastAndCrewToEntity(MovieResource resource, Movie movie) {
        movie.getCastAndCrew().clear();

        castAndCrewMapper.resources2Entities(resource.getActors(), movie.getCastAndCrew(), movie);
        castAndCrewMapper.resources2Entities(resource.getDirectors(), movie.getCastAndCrew(), movie);
        castAndCrewMapper.resources2Entities(resource.getProducers(), movie.getCastAndCrew(), movie);
        castAndCrewMapper.resources2Entities(resource.getMusic(), movie.getCastAndCrew(), movie);
        castAndCrewMapper.resources2Entities(resource.getWriters(), movie.getCastAndCrew(), movie);
        castAndCrewMapper.resources2Entities(resource.getCasters(), movie.getCastAndCrew(), movie);
        castAndCrewMapper.resources2Entities(resource.getEditors(), movie.getCastAndCrew(), movie);
        castAndCrewMapper.resources2Entities(resource.getCinematography(), movie.getCastAndCrew(), movie);
        castAndCrewMapper.resources2Entities(resource.getSound(), movie.getCastAndCrew(), movie);
        castAndCrewMapper.resources2Entities(resource.getArt(), movie.getCastAndCrew(), movie);
        castAndCrewMapper.resources2Entities(resource.getOtherRoles(), movie.getCastAndCrew(), movie);

        // Remove deleted cacs
        Set<CastAndCrew> deletedCacs =
                movie.getCastAndCrew().stream().filter(CastAndCrew::isDeleted).collect(Collectors.toSet());
        deletedCacs.stream().forEach(cac -> {
            if (cac.isDeleted()) {
                movie.getCastAndCrew().remove(cac);
            }
        });
    }

    public MovieInfoResource entity2infoResource(Movie movie) {
        return new MovieInfoResource()
                .setId(movie.getId())
                .setTitle(movie.getTitle())
                .setGenres(genreMapper.entities2Resources(movie.getGenres()))
                .setMoviePersonalInfo(MoviePersonalInfoMapper.entity2Resource(movie.getMoviePersonalInfo()));
    }
}
