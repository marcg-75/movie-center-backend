package se.giron.moviecenter.core.map.submapper;

import se.giron.moviecenter.model.entity.Movie;
import se.giron.moviecenter.model.entity.MoviePersonalInfo;
import se.giron.moviecenter.model.resource.MoviePersonalInfoResource;

import java.math.BigDecimal;

public class MoviePersonalInfoMapper {

    public static MoviePersonalInfoResource entity2Resource(MoviePersonalInfo moviePersonalInfo) {
        if (moviePersonalInfo == null) {
            return new MoviePersonalInfoResource();
        }
        return new MoviePersonalInfoResource()
                .setArchiveNumber(moviePersonalInfo.getArchiveNumber())
                .setGrade(moviePersonalInfo.getGrade() != null ? moviePersonalInfo.getGrade().doubleValue() : null)
                .setObtainDate(moviePersonalInfo.getObtainDate())
                .setObtainPlace(moviePersonalInfo.getObtainPlace())
                .setObtainPrice(moviePersonalInfo.getObtainPrice() != null ? moviePersonalInfo.getObtainPrice().doubleValue() : null)
                .setCurrency(moviePersonalInfo.getCurrency())
                .setNotes(moviePersonalInfo.getNotes());
    }

    public static MoviePersonalInfo resource2Entity(MoviePersonalInfoResource resource, Movie movie) {
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
        info.setArchiveNumber(resource.getArchiveNumber())
                .setGrade(resource.getGrade() != null ? new BigDecimal(resource.getGrade()) : null)
                .setObtainDate(resource.getObtainDate())
                .setObtainPlace(resource.getObtainPlace())
                .setObtainPrice(resource.getObtainPrice() != null ? new BigDecimal(resource.getObtainPrice()) : null)
                .setCurrency(resource.getCurrency())
                .setNotes(resource.getNotes());

        return info;
    }
}
