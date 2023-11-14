package se.giron.moviecenter.core.map.submapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.giron.moviecenter.core.map.PersonMapper;
import se.giron.moviecenter.core.repository.CastAndCrewRepository;
import se.giron.moviecenter.model.entity.CastAndCrew;
import se.giron.moviecenter.model.entity.Genre;
import se.giron.moviecenter.model.entity.Movie;
import se.giron.moviecenter.model.entity.MovieGenre;
import se.giron.moviecenter.model.resource.CastAndCrewResource;
import se.giron.moviecenter.model.resource.MovieGenreResource;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CastAndCrewMapper {

    @Autowired
    private CastAndCrewRepository castAndCrewRepository;

    @Autowired
    private PersonMapper personMapper;

    public List<CastAndCrewResource> entities2Resources(Set<CastAndCrew> cacs) {
        if (cacs == null || cacs.isEmpty()) {
            return null;
        }

        return cacs.stream().map(this::entity2Resource)
                .sorted(Comparator.comparing(a -> a.getPersonRole().getPerson().getName()))
                .collect(Collectors.toList());
    }

    private CastAndCrewResource entity2Resource(CastAndCrew cac) {
        return new CastAndCrewResource()
                .setId(cac.getId())
                .setMovieTitle(cac.getMovie().getTitle())
                .setPersonRole(personMapper.entity2PersonRoleResource(cac.getPersonRole()))
                .setCharacterName(cac.getCharacterName());
    }

    public Set<CastAndCrew> resources2Entities(List<CastAndCrewResource> resources, final Set<CastAndCrew> cacs, Movie movie) {
        if (resources == null) {
            return cacs;
        }

        // Given resources are always all the valid cacs.
        cacs.addAll(resources.stream()
                .map(mvp -> resource2Entity(mvp, movie))
                .collect(Collectors.toSet()));

        return cacs;
    }

//    private CastAndCrew resource2Entity(CastAndCrewResource resource, Movie movie) {
//        return new CastAndCrew()
//                .setId(resource.getId())
//                .setMovie(movie)
//                .setPersonRole(personMapper.resource2PersonRoleEntity(resource.getPersonRole()))
//                .setCharacterName(resource.getCharacterName())
//                .setDeleted(resource.isDeleted());
//    }

    public CastAndCrew resource2Entity(CastAndCrewResource resource, Movie movie) {
        CastAndCrew cac;

        if (resource.getId() == null) {
            cac = new CastAndCrew();
        } else {
            // Find existing castAndCrew.
            Optional<CastAndCrew> oCastAndCrew = castAndCrewRepository.findById(resource.getId());
            cac = oCastAndCrew.orElseGet(CastAndCrew::new);
        }

        return cac
                .setId(resource.getId())
                .setMovie(movie)
                .setPersonRole(personMapper.resource2PersonRoleEntity(resource.getPersonRole()))
                .setCharacterName(resource.getCharacterName())
                .setDeleted(resource.isDeleted());
    }
}
