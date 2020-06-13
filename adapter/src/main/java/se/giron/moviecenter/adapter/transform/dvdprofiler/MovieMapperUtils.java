package se.giron.moviecenter.adapter.transform.dvdprofiler;

import io.micrometer.core.instrument.util.StringUtils;
import se.giron.moviecenter.dvdprofiler.*;
import se.giron.moviecenter.model.entity.*;
import se.giron.moviecenter.model.enums.RoleEnum;
import se.giron.moviecenter.model.resource.*;

import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieMapperUtils {

	public static MovieResource movie2resource(DVD movieTransfer) {
		MovieResource movieResource = new MovieResource()
				.setTitle(movieTransfer.getTitle())
                .setOriginalTitle(movieTransfer.getOriginalTitle())
				.setDescription(movieTransfer.getOverview())
				.setRuntime(map2Runtime(movieTransfer.getRunningTime()))
				.setReleaseDate(movieTransfer.getReleased() != null ? movieTransfer.getReleased().toGregorianCalendar().getTime() : null)
				.setCountry(map2Country(movieTransfer.getCountryOfOrigin()))
				.setAgeRestriction(movieTransfer.getRatingAge() > 0 ? "Från " + movieTransfer.getRatingAge() + " år" : null)
				.setStudios(map2Studios(movieTransfer.getStudios()))

				.setMovieFormatInfo(map2MovieFormatInfo(movieTransfer))
				.setMoviePersonalInfo(map2MoviePersonalInfo(movieTransfer));

        mapGenres(movieResource, movieTransfer.getGenres());

		mapCastAndCrew(movieResource, movieTransfer);

		return movieResource;
	}

    private static void mapGenres(MovieResource movieResource, DVD.Genres mGenres) {
        if (mGenres == null || mGenres.getGenre() == null || mGenres.getGenre().isEmpty()) {
            return;
        }

        for (int i = 0 ; i < mGenres.getGenre().size() ; i++) {
            String genreCode = mGenres.getGenre().get(i).toUpperCase();

            if (StringUtils.isNotBlank(genreCode)) {
                String mappedGenreCode = genreCode;

                // Map code.
                if ("SUSPENSE/THRILLER".equalsIgnoreCase(genreCode)) {
                    mappedGenreCode = "THRILLER";
                } else if ("CHILDREN'S".equalsIgnoreCase(genreCode)) {
                    mappedGenreCode = "CHILDREN";
                } else if ("MUSIC".equalsIgnoreCase(genreCode)) {
                    mappedGenreCode = "MUSICAL";
                } else if ("MARTIAL ARTS".equalsIgnoreCase(genreCode)) {
                    mappedGenreCode = "MARTIAL-ARTS";
                }

                isGenreSupported(mappedGenreCode);

                Genre genre = new Genre().setCode(mappedGenreCode);

                if (i == 0) {
                    // Main genre (choose first in list).
                    movieResource.setMainGenre(genre);

                } else {
                    movieResource.getAdditionalGenres().add(genre);
                }
            }
        }
    }

    private static boolean isGenreSupported(String genreCode) {
        if (StringUtils.isBlank(genreCode)) {
            System.out.println("Unsupported genre: <empty value>");
            return false;
        }
        switch (genreCode) {
            case "ACTION":
            case "ANIMATION":
            case "COMEDY":
            case "CLASSIC":
            case "CRIME":
            case "DOCUMENTARY":
            case "DRAMA":
            case "DISASTER":
            case "FAMILY":
            case "FANTASY":
            case "SPORTS":
            case "HORROR":
            case "ROMANCE":
            case "THRILLER":
            case "WAR":
            case "WESTERN":
            case "TELEVISION":
            case "ADVENTURE":
            case "CHILDREN":
            case "SCIENCE-FICTION":
            case "MUSICAL":
            case "MARTIAL-ARTS":
                return true;
            default:
                System.out.println("Unsupported genre: " + genreCode);
                return false;
        }
    }

    private static Time map2Runtime(int runningTimeMinutes) {
        if (runningTimeMinutes <= 0) {
            return null;
        }

        int hours = Math.floorDiv(runningTimeMinutes, 60);
        int minutes = Math.floorMod(runningTimeMinutes, 60);

        return new Time(hours, minutes, 0);
    }

    private static String map2Country(String locality) {
        if (locality == null) {
            return null;
        }
        if ("United States".equalsIgnoreCase(locality)) {
            return "USA";
        } else {
            return locality;
        }
    }

    private static Set<Studio> map2Studios(DVD.Studios mStudios) {
        Set<Studio> studios = new HashSet<>();

        if (mStudios != null && mStudios.getStudio() != null && !mStudios.getStudio().isEmpty()) {
            studios.addAll(
                    mStudios.getStudio().stream()
                            .map(mSt -> new Studio().setName(mSt))
                            .collect(Collectors.toSet()));
        }
        return studios;
    }

    private static void mapCastAndCrew(MovieResource movieResource, DVD movieTransfer) {
        // Actors
        List<CastAndCrewResource> actors = map2Actors(movieTransfer.getActors());
        if (actors != null && !actors.isEmpty()) {
            movieResource.getActors().addAll(actors);
        }

        // Credits (Crew)
        map2Credits(movieResource, movieTransfer.getCredits());
    }

    private static List<CastAndCrewResource> map2Actors(ActorsType actorContainer) {
        if (actorContainer == null || actorContainer.getActorOrDivider() == null || actorContainer.getActorOrDivider().isEmpty()) {
            return null;
        }

        return actorContainer.getActorOrDivider().stream()
                .filter(aod -> aod instanceof ActorType)
                .map(a -> map2CastAndCrew((ActorType) a))
                .collect(Collectors.toList());
    }

    private static void map2Credits(MovieResource movieResource, CreditsType creditsContainer) {
        if (creditsContainer == null || creditsContainer.getCreditOrDivider() == null || creditsContainer.getCreditOrDivider().isEmpty()) {
            return;
        }

        creditsContainer.getCreditOrDivider().stream()
                .filter(cod -> cod instanceof CreditType)
                .forEach(credit -> {
            RoleEnum role = mapCreditType2Role((CreditType) credit);

            switch (role) {
                case DIRECTOR:
                    movieResource.getDirectors().add(map2CastAndCrew((CreditType) credit, role));
                    break;
                case PRODUCER:
                    movieResource.getProducers().add(map2CastAndCrew((CreditType) credit, role));
                    break;
                case MUSIC:
                    movieResource.getMusic().add(map2CastAndCrew((CreditType) credit, role));
                    break;
                case WRITER:
                    movieResource.getWriters().add(map2CastAndCrew((CreditType) credit, role));
                    break;
                case EDITOR:
                    movieResource.getEditors().add(map2CastAndCrew((CreditType) credit, role));
                    break;
                case CINEMATOGRAPHY:
                    movieResource.getCinematography().add(map2CastAndCrew((CreditType) credit, role));
                    break;
                case SOUND:
                    movieResource.getSound().add(map2CastAndCrew((CreditType) credit, role));
                    break;
                case ART:
                    movieResource.getArt().add(map2CastAndCrew((CreditType) credit, role));
                    break;
                case MISC:
                    movieResource.getOtherRoles().add(map2CastAndCrew((CreditType) credit, role));
                    break;
            }
        });
    }

    /**
     * Mapping (RoleEnum: CreditType->CreditSubtype):
     *
     * DIRECTOR: Direction->Director
     * PRODUCER: Production->Producer
     * MUSIC: Music(->Composer)
     * WRITER: Writing->Story By
     * CASTING: - (Stödjs ej i DVD Profiler)
     * EDITOR: Film Editing
     * CINEMATOGRAPHY: Cinematography
     * SOUND: Sound
     * ART: Art
     * MISC: (Alla övriga)
     *
     * @param credit
     * @return
     */
    private static RoleEnum mapCreditType2Role(CreditType credit) {
        if ("Direction".equalsIgnoreCase(credit.getCreditType())
                && "Director".equalsIgnoreCase(credit.getCreditSubtype())) {
            return RoleEnum.DIRECTOR;
        }
        if ("Production".equalsIgnoreCase(credit.getCreditType())
                && "Producer".equalsIgnoreCase(credit.getCreditSubtype())) {
            return RoleEnum.PRODUCER;
        }
        if ("Music".equalsIgnoreCase(credit.getCreditType())) {
            return RoleEnum.MUSIC;
        }
        if ("Writing".equalsIgnoreCase(credit.getCreditType())
                && ("Story By".equalsIgnoreCase(credit.getCreditSubtype())
                    || "Original Material By".equalsIgnoreCase(credit.getCreditSubtype()))) {
            return RoleEnum.WRITER;
        }
        if ("Film Editing".equalsIgnoreCase(credit.getCreditType())) {
            return RoleEnum.EDITOR;
        }
        if ("Cinematography".equalsIgnoreCase(credit.getCreditType())) {
            return RoleEnum.CINEMATOGRAPHY;
        }
        if ("Sound".equalsIgnoreCase(credit.getCreditType())) {
            return RoleEnum.SOUND;
        }
        if ("Art".equalsIgnoreCase(credit.getCreditType())) {
            return RoleEnum.ART;
        } else {
            return RoleEnum.MISC;
        }
    }

    private static CastAndCrewResource map2CastAndCrew(ActorType actor) {
        if (actor == null) {
            return null;
        }
        return new CastAndCrewResource()
                .setPersonRole(mapActor2PersonRoleResource(actor, RoleEnum.ACTOR))
                .setCharacterName(actor.getRole());
    }

    private static CastAndCrewResource map2CastAndCrew(CreditType credit, RoleEnum role) {
        if (credit == null) {
            return null;
        }
        return new CastAndCrewResource()
                .setPersonRole(mapCredit2PersonRoleResource(credit, role));
    }

    private static PersonRoleResource mapActor2PersonRoleResource(ActorType actor, RoleEnum role) {
        return new PersonRoleResource()
                .setPerson(mapActor2PersonResource(actor))
                .setRole(role != null ? new Role().setCode(role.name()) : null);
    }

    private static PersonRoleResource mapCredit2PersonRoleResource(CreditType credit, RoleEnum role) {
        return new PersonRoleResource()
                .setPerson(mapCredit2PersonResource(credit))
                .setRole(role != null ? new Role().setCode(role.name()) : null);
    }

    private static PersonResource mapActor2PersonResource(ActorType actor) {
        String firstNameStr = actor.getFirstName() != null ? actor.getFirstName() + " " : "";
        String middleNameStr = actor.getMiddleName() != null ? actor.getMiddleName() + " " : "";
        String lastNameStr = actor.getLastName() != null ? actor.getLastName() : "";

        return new PersonResource()
                .setName(firstNameStr + middleNameStr + lastNameStr);
    }

    private static PersonResource mapCredit2PersonResource(CreditType credit) {
        String firstNameStr = credit.getFirstName() != null ? credit.getFirstName() + " " : "";
        String middleNameStr = credit.getMiddleName() != null ? credit.getMiddleName() + " " : "";
        String lastNameStr = credit.getLastName() != null ? credit.getLastName() : "";

        return new PersonResource()
                .setName(firstNameStr + middleNameStr + lastNameStr);
    }

    private static MovieFormatInfoResource map2MovieFormatInfo(DVD movieTransfer) {
        if (movieTransfer == null) {
            return new MovieFormatInfoResource();
        }
        return new MovieFormatInfoResource()
                //.setCover(entity2CoverResource(info.getCover()))  // TODO: Implement?
                .setFormat(map2MediaFormat(movieTransfer.getMediaTypes()))
                .setRegion(map2Region(movieTransfer.getRegions()))
                .setUpcId(movieTransfer.getIDBase())
                .setDiscs(map2Discs(movieTransfer.getDiscs()))
                .setPictureFormat(String.valueOf(movieTransfer.getFormat().getFormatAspectRatio()))
                .setSystem(movieTransfer.getFormat().getFormatVideoStandard())
                .setAudioLanguages(map2AudioLanguages(movieTransfer.getAudio()))
                .setSubtitles(map2Subtitles(movieTransfer.getSubtitles()));
    }

    private static MoviePersonalInfoResource map2MoviePersonalInfo(DVD movieTransfer) {
        if (movieTransfer == null) {
            return new MoviePersonalInfoResource();
        }
        MoviePersonalInfoResource resource = new MoviePersonalInfoResource()
                .setGrade(map2Grade(movieTransfer.getReview()))
                .setNotes(movieTransfer.getNotes());

        if (StringUtils.isNotBlank(movieTransfer.getCollectionNumber())) {
            resource.setArchiveNumber(Integer.parseInt(movieTransfer.getCollectionNumber()));
        }

        return mapObtainInfo(resource, movieTransfer.getPurchaseInfo());
    }

    private static Format map2MediaFormat(DVD.MediaTypes mediaTypes) {
        if (Boolean.parseBoolean(mediaTypes.getBluRay())) {
            return new Format().setCode("BLURAY");
        }
        if (Boolean.parseBoolean(mediaTypes.getDVD())) {
            return new Format().setCode("DVD");
        }
        if (Boolean.parseBoolean(mediaTypes.getHDDVD())) {
            return new Format().setCode("HDDVD");
        }
        if (Boolean.parseBoolean(mediaTypes.getUltraHD())) {
            return new Format().setCode("FKUHD");
        }
        return null;
    }

    private static Integer map2Region(DVD.Regions regions) {
        // If region 2 is one of them, then choose it. Otherwise, pick the first one.
        String region = "0";

        if (regions != null && regions.getRegion() != null && !regions.getRegion().isEmpty()) {
            for (String iRegion : regions.getRegion()) {
                if (iRegion.equalsIgnoreCase("A") || iRegion.equalsIgnoreCase("B")) {
                    region = "2";
                } else if (iRegion.equals("2") || region.equals("0")) {
                    region = iRegion;
                }
            }
        }
        return Integer.valueOf(region);
    }

    private static Integer map2Discs(DVD.Discs discInfo) {
        int nDiscs = 0;

        if (discInfo != null && discInfo.getDisc() != null && !discInfo.getDisc().isEmpty()) {
            nDiscs = discInfo.getDisc().size();
        }
        return nDiscs;
    }

    private static List<Language> map2AudioLanguages(DVD.Audio audio) {
        if (audio == null || audio.getAudioTrack() == null || audio.getAudioTrack().isEmpty()) {
            return null;
        }

        return audio.getAudioTrack().stream()
                .filter(a -> isSupportedLang(a.getAudioContent()))
                .map(a -> new Language().setName(a.getAudioContent()).setNameSwedish(lang2Swe(a.getAudioContent())))
                .collect(Collectors.toList());
    }

    private static List<Language> map2Subtitles(DVD.Subtitles subtitles) {
        if (subtitles == null || subtitles.getSubtitle() == null || subtitles.getSubtitle().isEmpty()) {
            return null;
        }

        return subtitles.getSubtitle().stream()
                .filter(MovieMapperUtils::isSupportedLang)
                .map(s -> new Language().setName(s).setNameSwedish(lang2Swe(s)))
                .collect(Collectors.toList());
    }

    private static boolean isSupportedLang(String lang) {
        return StringUtils.isNotBlank(lang)
                && !"Other".equalsIgnoreCase(lang)
                && !"Commentary".equalsIgnoreCase(lang)
                && !"Audio Descriptive".equalsIgnoreCase(lang)
                && !"Trivia".equalsIgnoreCase(lang)
                && !"Music Only".equalsIgnoreCase(lang);
    }

    /**
     * Translates the most commonly occured languages from English to Swedish.
     */
    private static String lang2Swe(String langEng) {
        if (StringUtils.isBlank(langEng)) {
            return null;
        }

        switch (langEng) {
            case "English":
                return "Engelska";
            case "Swedish":
                return "Svenska";
            case "German":
                return "Tyska";
            case "French":
                return "Franska";
            case "Spanish":
                return "Spanska";
            case "Portuguese":
                return "Portugisiska";
            case "Norwegian":
                return "Norska";
            case "Danish":
                return "Danska";
            case "Finnish":
                return "Finska";
            case "Icelandic":
                return "Isländska";
            case "Dutch":
                return "Holländska";
            case "Flemish":
                return "Flamländska";
            case "Italian":
                return "Italienska";
            case "Greek":
                return "Grekiska";
            case "Farsi":
                return "Farsi";
            case "Turkish":
                return "Turkiska";
            case "Estonian":
                return "Estniska";
            case "Latvian":
                return "Lettiska";
            case "Lithuanian":
                return "Litauiska";
            case "Hindi":
                return "Hindi";
            case "Vietnamese":
                return "Vietnamesiska";
            case "Arabic":
                return "Arabiska";
            case "Hebrew":
                return "Hebreiska";
            case "Japanese":
                return "Japanska";
            case "Chinese":
                return "Kinesiska";
            case "Mandarin":
                return "Mandarin";
            case "Thai":
                return "Thailändska";
            case "Polish":
                return "Polska";
            case "Czech":
                return "Tjeckiska";
            case "Hungarian":
                return "Ungerska";
            case "Romanian":
                return "Rumänska";
            case "Slovakian":
                return "Slovakiska";
            case "Slovenian":
                return "Slovenska";
            case "Croatian":
                return "Kroatiska";
            case "Bulgarian":
                return "Bulgariska";
            case "Catalonian":
                return "Katalanska";
            case "Korean":
                return "Koreanska";
            case "Serbian":
                return "Serbiska";
            case "Russian":
                return "Ryska";
            default:
                System.out.println("Unsupported language: " + langEng);
                return "";
        }
    }

    /**
     * Review format: <Review Film="5" Video="0" Audio="0" Extras="0"/>
     * Input steps: 1-9
     * Output steps: 1-5 (ie. 1, 1.5, ..., 4, 4.5, 5)
     *
     * @param review
     * @return
     */
    private static Double map2Grade(DVD.Review review) {
        if (review == null || review.getFilm() == null) {
            return 0D;
        }
        switch (review.getFilm()) {
            case 1:
                return 1D;
            case 2:
                return 1.5;
            case 3:
                return 2D;
            case 4:
                return 2.5;
            case 5:
                return 3D;
            case 6:
                return 3.5;
            case 7:
                return 4D;
            case 8:
                return 4.5;
            case 9:
                return 5D;
            default:
                return 0D;
        }
    }

    private static MoviePersonalInfoResource mapObtainInfo(MoviePersonalInfoResource resource, DVD.PurchaseInfo purchaseInfo) {
        if (purchaseInfo == null) {
            return resource;
        }
        Double obtainPrice = (purchaseInfo.getPurchasePrice() != null && purchaseInfo.getPurchasePrice().getValue() != null)
                ? purchaseInfo.getPurchasePrice().getValue().doubleValue() : null;

        return resource
                .setObtainDate(purchaseInfo.getPurchaseDate().toGregorianCalendar().getTime())
                .setObtainPlace(purchaseInfo.getPurchasePlace())
                .setObtainPrice(obtainPrice)
                .setCurrency(purchaseInfo.getPurchasePrice() != null ? purchaseInfo.getPurchasePrice().getDenominationType() : null);
    }
}
