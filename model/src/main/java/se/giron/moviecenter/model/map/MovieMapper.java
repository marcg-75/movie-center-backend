package se.giron.moviecenter.model.map;

import se.giron.moviecenter.model.entity.*;
import se.giron.moviecenter.model.enums.SystemEnum;
import se.giron.moviecenter.model.resource.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieMapper {

    public static MovieResource entity2resource(Movie movie) {
        return new MovieResource()
                .setId(movie.getId())
                .setTitle(movie.getTitle())
                .setMainGenre(movie.getMainGenre())
                .setDescription(movie.getDescription())
                .setAdditionalGenres(movie.getAdditionalGenres() != null ? movie.getAdditionalGenres().stream().collect(Collectors.toList()) : null)
                .setRuntime(movie.getRuntime())
                .setReleaseDate(movie.getReleaseDate())
                .setCountry(movie.getCountry())
                .setAgeRestriction(movie.getAgeRestriction())
                .setStudios(movie.getStudios())
                .setCastAndCrew(movie.getCastAndCrew())
                .setActors(entities2PersonResources(movie.getActors()))
                .setDirectors(entities2PersonResources(movie.getDirectors()))
                .setProducers(entities2PersonResources(movie.getProducers()))
                .setMusic(entities2PersonResources(movie.getMusic()))
                .setWriters(entities2PersonResources(movie.getWriters()))
                .setCasters(entities2PersonResources(movie.getCasters()))
                .setEditors(entities2PersonResources(movie.getEditors()))
                .setCinematography(entities2PersonResources(movie.getCinematography()))
                .setSound(entities2PersonResources(movie.getSound()))
                .setArt(entities2PersonResources(movie.getArt()))
                .setOtherRoles(entities2PersonResources(movie.getOtherRoles()))
                .setMovieFormatInfo(entity2MovieFormatInfoResource(movie.getMovieFormatInfo()))
                .setMoviePersonalInfo(entity2PersonalInfoResource(movie.getMoviePersonalInfo()));
    }

    public static Movie resource2entity(MovieResource movieResource, Movie movie) {
        movie.setTitle(movieResource.getTitle())
                .setMainGenre(movieResource.getMainGenre())
                .setDescription(movieResource.getDescription())
                .setAdditionalGenres(movieResource.getAdditionalGenres() != null ? movieResource.getAdditionalGenres().stream().collect(Collectors.toSet()) : null)
                .setRuntime(movieResource.getRuntime())
                .setReleaseDate(movieResource.getReleaseDate())
                .setCountry(movieResource.getCountry())
                .setAgeRestriction(movieResource.getAgeRestriction())
                .setStudios(movieResource.getStudios())
                // TODO: Change to add + update additional genres, to prevent unnecessary db updates.
                .setCastAndCrew(movieResource.getCastAndCrew())
                .setActors(resources2PersonEntities(movieResource.getActors(), movie.getActors()))
                .setDirectors(resources2PersonEntities(movieResource.getDirectors(), movie.getDirectors()))
                .setProducers(resources2PersonEntities(movieResource.getProducers(), movie.getProducers()))
                .setMusic(resources2PersonEntities(movieResource.getMusic(), movie.getMusic()))
                .setWriters(resources2PersonEntities(movieResource.getWriters(), movie.getWriters()))
                .setCasters(resources2PersonEntities(movieResource.getCasters(), movie.getCasters()))
                .setEditors(resources2PersonEntities(movieResource.getEditors(), movie.getEditors()))
                .setCinematography(resources2PersonEntities(movieResource.getCinematography(), movie.getCinematography()))
                .setSound(resources2PersonEntities(movieResource.getSound(), movie.getSound()))
                .setArt(resources2PersonEntities(movieResource.getArt(), movie.getArt()))
                .setOtherRoles(resources2PersonEntities(movieResource.getOtherRoles(), movie.getOtherRoles()))

                .setMovieFormatInfo(resource2MovieFormatInfo(movieResource.getMovieFormatInfo(), movie.getMovieFormatInfo()))
                .setMoviePersonalInfo(resource2MoviePersonalInfo(movieResource.getMoviePersonalInfo(), movie.getMoviePersonalInfo()));

        return movie;
    }

    public static MovieInfoResource entity2infoResource(Movie movie) {
        return new MovieInfoResource()
                .setId(movie.getId())
                .setTitle(movie.getTitle())
                .setMainGenre(movie.getMainGenre())
                .setMoviePersonalInfo(entity2PersonalInfoResource(movie.getMoviePersonalInfo()));
    }

    private static PersonResource entity2PersonResource(Person person) {
        return new PersonResource()
                .setId(person.getId())
                .setName(person.getName());
    }

    private static List<PersonResource> entities2PersonResources(Set<Person> persons) {
        if (persons == null || persons.size() == 0) {
            return null;
        }

        return persons.stream().map(MovieMapper::entity2PersonResource).collect(Collectors.toList());
    }

    private static Person resource2PersonEntity(PersonResource resource) {
        return new Person()
                .setId(resource.getId())
                .setName(resource.getName());
    }

    private static Set<Person> resources2PersonEntities(List<PersonResource> resources, final Set<Person> persons) {
        if (resources == null) {
            return persons;
        }

        // All new.
        if (persons.isEmpty()) {
            return resources.stream()
                    .map(MovieMapper::resource2PersonEntity)
                    .collect(Collectors.toSet());
        }

        // Update existing persons.
        for (Person person : persons) {
            Long id = person.getId();
            Optional<PersonResource> pr = resources.stream().filter(_person -> id.equals(_person.getId())).findFirst();
            if (pr.isPresent()) {
                Person newPerson = resource2PersonEntity(pr.get());
                if (!newPerson.equals(person)) {
                    person.setName(pr.get().getName());
                }
            }
        }

        // Add new persons.
        persons.addAll(
                resources.stream().filter(pr -> pr.getId() == null || pr.getId() < 0)
                        .map(MovieMapper::resource2PersonEntity).collect(Collectors.toList()));
        return persons;
    }

    private static CoverResource entity2CoverResource(Cover cover) {
        return new CoverResource()
                .setForeground(cover.getForeground())
                .setBackground(cover.getBackground())
                .setForegroundUrl(cover.getForegroundUrl())
                .setBackgroundUrl(cover.getBackgroundUrl());
    }

    private static Cover resource2Cover(CoverResource resource, Cover cover) {
        if (resource == null) {
            return cover;
        }

        // New cover
        if (cover == null) {
            cover = new Cover();
        }

        // Update cover
        cover.setForeground(resource.getForeground())
                .setBackground(resource.getBackground())
                .setForegroundUrl(resource.getForegroundUrl())
                .setBackgroundUrl(resource.getBackgroundUrl());

        return cover;
    }

    private static MovieFormatInfoResource entity2MovieFormatInfoResource(MovieFormatInfo info) {
        return new MovieFormatInfoResource()
                .setCover(entity2CoverResource(info.getCover()))
                .setFormat(info.getFormat())
                .setRegion(info.getRegion())
                .setUpcId(info.getUpcId())
                .setDiscs(info.getDiscs())
                .setPictureFormat(info.getPictureFormat())
                .setSystem(info.getSystem().name())
                .setAudioLanguages(info.getAudioLanguages() != null ? info.getAudioLanguages().stream().collect(Collectors.toList()) : null)
                .setSubtitles(info.getSubtitles() != null ? info.getSubtitles().stream().collect(Collectors.toList()) : null);
    }

    private static MovieFormatInfo resource2MovieFormatInfo(MovieFormatInfoResource resource, MovieFormatInfo info) {
        if (resource == null) {
            return info;
        }

        // New info
        if (info == null) {
            info = new MovieFormatInfo();
        }

        // Update info
        info.setCover(resource2Cover(resource.getCover(), info.getCover()))
                .setFormat(resource.getFormat())
                .setRegion(resource.getRegion())
                .setUpcId(resource.getUpcId())
                .setDiscs(resource.getDiscs())
                .setPictureFormat(resource.getPictureFormat())
                .setSystem(SystemEnum.valueOf(resource.getSystem()))
                .setAudioLanguages(resource.getAudioLanguages() != null ? resource.getAudioLanguages().stream().collect(Collectors.toSet()) : null)
                .setSubtitles(resource.getSubtitles() != null ? resource.getSubtitles().stream().collect(Collectors.toSet()) : null);
        // TODO: Change to add + update languages, to prevent unnecessary db updates.
        return info;
    }

    private static MoviePersonalInfoResource entity2PersonalInfoResource(MoviePersonalInfo moviePersonalInfo) {
        return new MoviePersonalInfoResource()
                .setGrade(moviePersonalInfo.getGrade())
                .setObtainDate(moviePersonalInfo.getObtainDate())
                .setObtainPlace(moviePersonalInfo.getObtainPlace())
                .setObtainPrice(moviePersonalInfo.getObtainPrice().doubleValue())
                .setCurrency(moviePersonalInfo.getCurrency())
                .setNotes(moviePersonalInfo.getNotes());
    }

    private static MoviePersonalInfo resource2MoviePersonalInfo(MoviePersonalInfoResource resource, MoviePersonalInfo info) {
        if (resource == null) {
            return info;
        }

        // New info
        if (info == null) {
            info = new MoviePersonalInfo();
        }

        // Update info
        info.setGrade(resource.getGrade())
                .setObtainDate(resource.getObtainDate())
                .setObtainPlace(resource.getObtainPlace())
                .setObtainPrice(new BigDecimal(resource.getObtainPrice()))
                .setCurrency(resource.getCurrency())
                .setNotes(resource.getNotes());

        return info;
    }
}
