package se.giron.moviecenter.core.map.submapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.giron.moviecenter.core.repository.LanguageRepository;
import se.giron.moviecenter.model.entity.Cover;
import se.giron.moviecenter.model.entity.Language;
import se.giron.moviecenter.model.entity.Movie;
import se.giron.moviecenter.model.entity.MovieFormatInfo;
import se.giron.moviecenter.model.resource.CoverResource;
import se.giron.moviecenter.model.resource.MovieFormatInfoResource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovieFormatInfoMapper {

    @Autowired
    private LanguageRepository languageRepository;

    public MovieFormatInfoResource entity2Resource(MovieFormatInfo info) {
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
                .setSystem(info.getSystem() != null ? info.getSystem() : null)
                .setAudioLanguages(info.getAudioLanguages() != null ? info.getAudioLanguages().stream().collect(Collectors.toList()) : null)
                .setSubtitles(info.getSubtitles() != null ? info.getSubtitles().stream().collect(Collectors.toList()) : null);
    }

    public MovieFormatInfo resource2Entity(MovieFormatInfoResource resource, Movie movie) {
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
                .setSystem(resource.getSystem() != null ? resource.getSystem() : null);

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
        return info;
    }

    private CoverResource entity2CoverResource(Cover cover) {
        return new CoverResource()
                .setFgFileName(cover.getFgFileName())
                .setBgFileName(cover.getBgFileName())
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
        cover.setFgFileName(resource.getFgFileName())
                .setBgFileName(resource.getBgFileName())
                .setForegroundUrl(resource.getForegroundUrl())
                .setBackgroundUrl(resource.getBackgroundUrl());

        return cover;
    }

    private Language resource2LanguageEntity(Language resource) {
        // Find existing language.
        Language language = null;

        if (resource.getId() != null) {
            Optional<Language> oLanguage = languageRepository.findById(resource.getId());

            if (oLanguage.isPresent()) {
                language = oLanguage.get();
            }
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
}
