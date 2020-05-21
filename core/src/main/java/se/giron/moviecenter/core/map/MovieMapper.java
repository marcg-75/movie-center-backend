package se.giron.moviecenter.core.map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.giron.moviecenter.core.repository.LanguageRepository;
import se.giron.moviecenter.core.repository.StudioRepository;
import se.giron.moviecenter.model.entity.*;
import se.giron.moviecenter.model.enums.RoleEnum;
import se.giron.moviecenter.model.enums.SystemEnum;
import se.giron.moviecenter.model.resource.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public MovieResource entity2resource(Movie movie) {
        MovieResource resource = new MovieResource()
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
                .setMovieFormatInfo(entity2MovieFormatInfoResource(movie.getMovieFormatInfo()))
                .setMoviePersonalInfo(entity2PersonalInfoResource(movie.getMoviePersonalInfo()));

        addCastAndCrewToResource(resource, movie.getCastAndCrew());

        return resource;
    }

    private void addCastAndCrewToResource(MovieResource resource, Set<CastAndCrew> allCac) {
        List<CastAndCrewResource> allCacResourcesSorted = entities2CastAndCrewResources(allCac);

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

    public Movie resource2entity(MovieResource movieResource, Movie movie) {
        movie.setTitle(movieResource.getTitle())
                .setMainGenre(movieResource.getMainGenre())
                .setDescription(movieResource.getDescription())
                .setAdditionalGenres(movieResource.getAdditionalGenres() != null ? movieResource.getAdditionalGenres().stream().collect(Collectors.toSet()) : null)
                .setRuntime(movieResource.getRuntime())
                .setReleaseDate(movieResource.getReleaseDate())
                .setCountry(movieResource.getCountry())
                .setAgeRestriction(movieResource.getAgeRestriction())
                //.setStudios(movieResource.getStudios())
                // TODO: Change to add + update additional genres, to prevent unnecessary db updates.

                .setMovieFormatInfo(resource2MovieFormatInfo(movieResource.getMovieFormatInfo(), movie))
                .setMoviePersonalInfo(resource2MoviePersonalInfo(movieResource.getMoviePersonalInfo(), movie));

        mergeCastAndCrewToEntity(movieResource, movie);

        movie.getStudios().clear();
        if (movieResource.getStudios() != null && !movieResource.getStudios().isEmpty()) {
            movie.getStudios().addAll(movieResource.getStudios().stream()
                    .map(this::resource2StudioEntity)
                    .collect(Collectors.toSet()));
        }

        return movie;
    }

    private void mergeCastAndCrewToEntity(MovieResource resource, Movie movie) {
        movie.getCastAndCrew().clear();

        resources2CastAndCrewEntities(resource.getActors(), movie.getCastAndCrew(), movie);
        resources2CastAndCrewEntities(resource.getDirectors(), movie.getCastAndCrew(), movie);
        resources2CastAndCrewEntities(resource.getProducers(), movie.getCastAndCrew(), movie);
        resources2CastAndCrewEntities(resource.getMusic(), movie.getCastAndCrew(), movie);
        resources2CastAndCrewEntities(resource.getWriters(), movie.getCastAndCrew(), movie);
        resources2CastAndCrewEntities(resource.getCasters(), movie.getCastAndCrew(), movie);
        resources2CastAndCrewEntities(resource.getEditors(), movie.getCastAndCrew(), movie);
        resources2CastAndCrewEntities(resource.getCinematography(), movie.getCastAndCrew(), movie);
        resources2CastAndCrewEntities(resource.getSound(), movie.getCastAndCrew(), movie);
        resources2CastAndCrewEntities(resource.getArt(), movie.getCastAndCrew(), movie);
        resources2CastAndCrewEntities(resource.getOtherRoles(), movie.getCastAndCrew(), movie);

        // Remove deleted cacs
        Set<CastAndCrew> deletedCacs =
                movie.getCastAndCrew().stream().filter(CastAndCrew::isDeleted).collect(Collectors.toSet());
        deletedCacs.stream().forEach(cac -> {
            if (cac.isDeleted()) {
                movie.getCastAndCrew().remove(cac);
            }
        });
    }

    private Studio resource2StudioEntity(Studio resource) {
        // Find existing language.
        Studio studio = null;

        if (resource.getId() != null) {
            Optional<Studio> oStudio = studioRepository.findById(resource.getId());
            studio = oStudio.get();
        } else if (StringUtils.isNotEmpty(resource.getName())) {
            List<Studio> studiosWithName = studioRepository.findByName(resource.getName());
            if (!studiosWithName.isEmpty()) {
                // Loopa och hitta med namn
//                Optional<Language> oLanguage =
//                        langsWithName.stream().filter(l -> l.getName().equals(resource.getName())).findFirst();
                //studio = oLanguage.isPresent() ? oLanguage.get() : null;
                studio = studiosWithName.get(0);
            }
        }
        return studio != null ? studio : resource;
    }

    public MovieInfoResource entity2infoResource(Movie movie) {
        return new MovieInfoResource()
                .setId(movie.getId())
                .setTitle(movie.getTitle())
                .setMainGenre(movie.getMainGenre())
                .setMoviePersonalInfo(entity2PersonalInfoResource(movie.getMoviePersonalInfo()));
    }

    private CastAndCrewResource entity2CastAndCrewResource(CastAndCrew cac) {
        return new CastAndCrewResource()
                .setId(cac.getId())
                .setMovieTitle(cac.getMovie().getTitle())
                .setPersonRole(personMapper.entity2PersonRoleResource(cac.getPersonRole()))
                .setCharacterName(cac.getCharacterName());
    }

    private List<CastAndCrewResource> entities2CastAndCrewResources(Set<CastAndCrew> cacs) {
        if (cacs == null || cacs.size() == 0) {
            return null;
        }

        return cacs.stream().map(this::entity2CastAndCrewResource)
                .sorted(new Comparator<CastAndCrewResource>() {
                    @Override
                    public int compare(CastAndCrewResource a, CastAndCrewResource b) {
                        return a.getPersonRole().getPerson().getName()
                                .compareTo(b.getPersonRole().getPerson().getName());
                    }
                })
                .collect(Collectors.toList());
    }

    private CastAndCrew resource2CastAndCrewEntity(CastAndCrewResource resource, Movie movie) {
        return new CastAndCrew()
                .setId(resource.getId())
                .setMovie(movie)
                .setPersonRole(personMapper.resource2PersonRoleEntity(resource.getPersonRole()))
                .setCharacterName(resource.getCharacterName())
                .setDeleted(resource.isDeleted());
    }

    private Set<CastAndCrew> resources2CastAndCrewEntities(List<CastAndCrewResource> resources, final Set<CastAndCrew> cacs, Movie movie) {
        if (resources == null) {
            return cacs;
        }

        // Given resources are always all the valid cacs.
        //cacs.clear();

        cacs.addAll(resources.stream()
                .map(mvp -> resource2CastAndCrewEntity(mvp, movie))
                .collect(Collectors.toSet()));


//        return resources.stream()
//                .map(mvp -> resource2CastAndCrewEntity(mvp, movie))
//                .collect(Collectors.toSet());

        // All new.
//        if (cacs.isEmpty()) {
//            cacs.addAll(resources.stream()
//                    .map(mvp -> resource2CastAndCrewEntity(mvp, movie))
//                    .collect(Collectors.toSet()));
//        }
//
//        // Update existing cacs.
//        for (CastAndCrew cac : cacs) {
//            Long id = cac.getId();
//            Optional<CastAndCrewResource> cacr = resources.stream().filter(_cac -> id.equals(_cac.getId())).findFirst();
//            if (cacr.isPresent()) {
//                CastAndCrew newCac = resource2CastAndCrewEntity(cacr.get(), movie);
//                if (!newCac.equals(cac)) {
//                    cac.setPersonRole(newCac.getPersonRole());
//                    cac.setCharacterName(newCac.getCharacterName());
//                }
//            }
//        }
//
//        // Add new cacs.
//        cacs.addAll(
//                resources.stream().filter(pr -> pr.getId() == null || pr.getId() < 0)
//                        .map(mvp -> resource2CastAndCrewEntity(mvp, movie)).collect(Collectors.toSet()));
//
//        // TODO: Remove deleted cacs.
//        //cacs = cacs.stream().filter(_cac -> _cac.getId())
//        for (CastAndCrewResource cacr : resources) {
//
//        }


        return cacs;
    }

    private CoverResource entity2CoverResource(Cover cover) {
        return new CoverResource()
                .setForeground(cover.getForeground())
                .setBackground(cover.getBackground())
                .setForegroundUrl(cover.getForegroundUrl())
                .setBackgroundUrl(cover.getBackgroundUrl());
    }

    private Cover resource2Cover(CoverResource resource, MovieFormatInfo formatInfo) {
        Cover cover = formatInfo.getCover();

        // New cover
        if (cover == null) {
            cover = new Cover();
        }
        cover.setMovieFormatInfo(formatInfo);

        if (resource == null) {
            return cover;
        }

        // Update cover
        cover.setForeground(resource.getForeground())
                .setBackground(resource.getBackground())
                .setForegroundUrl(resource.getForegroundUrl())
                .setBackgroundUrl(resource.getBackgroundUrl());

        return cover;
    }

    private MovieFormatInfoResource entity2MovieFormatInfoResource(MovieFormatInfo info) {
        if (info == null) {
            return new MovieFormatInfoResource();
        }
        return new MovieFormatInfoResource()
                .setCover(entity2CoverResource(info.getCover()))
                .setFormat(info.getFormat())
                .setRegion(info.getRegion())
                .setUpcId(info.getUpcId())
                .setDiscs(info.getDiscs())
                .setPictureFormat(info.getPictureFormat())
                .setSystem(info.getSystem() != null ? info.getSystem().name() : null)
                .setAudioLanguages(info.getAudioLanguages() != null ? info.getAudioLanguages().stream().collect(Collectors.toList()) : null)
                .setSubtitles(info.getSubtitles() != null ? info.getSubtitles().stream().collect(Collectors.toList()) : null);
    }

    private MovieFormatInfo resource2MovieFormatInfo(MovieFormatInfoResource resource, Movie movie) {
        MovieFormatInfo info = movie.getMovieFormatInfo();

        // New info
        if (info == null) {
            info = new MovieFormatInfo();
        }
        info.setMovie(movie);

        if (resource == null) {
            return info;
        }

        info.getAudioLanguages().clear();
        info.getSubtitles().clear();

        // Update info
        info.setCover(resource2Cover(resource.getCover(), info))
                .setFormat(resource.getFormat())
                .setRegion(resource.getRegion())
                .setUpcId(resource.getUpcId())
                .setDiscs(resource.getDiscs())
                .setPictureFormat(resource.getPictureFormat())
                .setSystem(resource.getSystem() != null ? SystemEnum.valueOf(resource.getSystem()) : null);

        if (resource.getAudioLanguages() != null && !resource.getAudioLanguages().isEmpty()) {
            info.getAudioLanguages().addAll(resource.getAudioLanguages().stream()
                    .map(this::resource2LanguageEntity)
                    .collect(Collectors.toSet()));
        }

        if (resource.getSubtitles() != null && !resource.getSubtitles().isEmpty()) {
            info.getSubtitles().addAll(resource.getSubtitles().stream()
                    .map(this::resource2LanguageEntity)
                    .collect(Collectors.toSet()));
        }

//                .setAudioLanguages(resource.getAudioLanguages() != null ? resource.getAudioLanguages().stream().collect(Collectors.toSet()) : null)
//                .setSubtitles(resource.getSubtitles() != null ? resource.getSubtitles().stream().collect(Collectors.toSet()) : null);
        // TODO: Change to add + update languages, to prevent unnecessary db updates.
        return info;
    }

    private Language resource2LanguageEntity(Language resource) {
        // Find existing language.
        Language language = null;

        if (resource.getId() != null) {
            Optional<Language> oLanguage = languageRepository.findById(resource.getId());
            language = oLanguage.get();
        } else if (StringUtils.isNotBlank(resource.getName())) {
//            List<Language> langsWithName = languageRepository.findAll();
//            if (!langsWithName.isEmpty()) {
//                // Loopa och hitta med namn
//                Optional<Language> oLanguage =
//                        langsWithName.stream().filter(l -> l.getName().equals(resource.getName())).findFirst();
//                language = oLanguage.isPresent() ? oLanguage.get() : null;
//            }
            List<Language> langsWithName = languageRepository.findByName(resource.getName());
            if (!langsWithName.isEmpty()) {
                language = langsWithName.get(0);
            }
        }
        return language != null ? language : resource;
    }

    private MoviePersonalInfoResource entity2PersonalInfoResource(MoviePersonalInfo moviePersonalInfo) {
        if (moviePersonalInfo == null) {
            return new MoviePersonalInfoResource();
        }
        return new MoviePersonalInfoResource()
                .setGrade(moviePersonalInfo.getGrade() != null ? moviePersonalInfo.getGrade().doubleValue() : null)
                .setObtainDate(moviePersonalInfo.getObtainDate())
                .setObtainPlace(moviePersonalInfo.getObtainPlace())
                .setObtainPrice(moviePersonalInfo.getObtainPrice() != null ? moviePersonalInfo.getObtainPrice().doubleValue() : null)
                .setCurrency(moviePersonalInfo.getCurrency())
                .setNotes(moviePersonalInfo.getNotes());
    }

    private MoviePersonalInfo resource2MoviePersonalInfo(MoviePersonalInfoResource resource, Movie movie) {
        MoviePersonalInfo info = movie.getMoviePersonalInfo();

        // New info
        if (info == null) {
            info = new MoviePersonalInfo();
        }
        info.setMovie(movie);

        if (resource == null) {
            return info;
        }

        // Update info
        info.setGrade(resource.getGrade() != null ? new BigDecimal(resource.getGrade()) : null)
                .setObtainDate(resource.getObtainDate())
                .setObtainPlace(resource.getObtainPlace())
                .setObtainPrice(resource.getObtainPrice() != null ? new BigDecimal(resource.getObtainPrice()) : null)
                .setCurrency(resource.getCurrency())
                .setNotes(resource.getNotes());

        return info;
    }

    class SortCastCrew implements Comparator<CastAndCrewResource> {

        public int compare(CastAndCrewResource a, CastAndCrewResource b) {
            return a.getPersonRole().getPerson().getName()
                    .compareTo(b.getPersonRole().getPerson().getName());
        }
    }
}
