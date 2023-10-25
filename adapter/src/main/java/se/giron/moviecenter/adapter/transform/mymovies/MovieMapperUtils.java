package se.giron.moviecenter.adapter.transform.mymovies;

import io.micrometer.core.instrument.util.StringUtils;
import se.giron.moviecenter.model.entity.*;
import se.giron.moviecenter.model.enums.RoleEnum;
import se.giron.moviecenter.model.resource.*;
import se.giron.moviecenter.mymovies.*;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class MovieMapperUtils {

    private static final Map<Integer, String> ageRestrictionMap = new HashMap<>();
    private static final Map<String, String> formatMap = new HashMap<>();
    private static final Map<String, String> languageMap = new HashMap<>();
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa", Locale.ENGLISH);

    static {
        ageRestrictionMap.put(0, "Ej klassificerad");
        ageRestrictionMap.put(1, "Barntillåten");
        ageRestrictionMap.put(2, "Från 4 år");
        ageRestrictionMap.put(3, "Från 7 år");
        ageRestrictionMap.put(4, "Från 11 år");
        ageRestrictionMap.put(5, "Från 11 år");
        ageRestrictionMap.put(6, "Från 15 år");
        ageRestrictionMap.put(7, "Barnförbjuden");
        ageRestrictionMap.put(8, "Barnförbjuden");

        formatMap.put("Blu-ray", "BluRay");

        languageMap.put("Arabic", "Arabiska");
        languageMap.put("BrazilianPortuguese", "Brasiliansk Portugisiska");
        languageMap.put("Bulgarian", "Bulgariska");
        languageMap.put("Catalan", "Katalanska");
        languageMap.put("Catalonian", "Katalanska");
        languageMap.put("Cantonese", "Kantonesiska");
        languageMap.put("Chinese", "Kinesiska");
        languageMap.put("Croatian", "Kroatiska");
        languageMap.put("Czech", "Tjeckiska");
        languageMap.put("Danish", "Danska");
        languageMap.put("Dutch", "Holländska");
        languageMap.put("English", "Engelska");
        languageMap.put("Esperanto", "Esperanto");
        languageMap.put("Estonian", "Estniska");
        languageMap.put("Farsi", "Farsi");
        languageMap.put("Finnish", "Finska");
        languageMap.put("Flemish", "Flamländska");
        languageMap.put("French", "Franska");
        languageMap.put("German", "Tyska");
        languageMap.put("Greek", "Grekiska");
        languageMap.put("Hebrew", "Hebreiska");
        languageMap.put("Hebrew (he)", "Hebreiska");
        languageMap.put("Hindi", "Hindi");
        languageMap.put("Hungarian", "Ungerska");
        languageMap.put("Icelandic", "Isländska");
        languageMap.put("Indonesian", "Indonesiska");
        languageMap.put("Italian", "Italienska");
        languageMap.put("Japanese", "Japanska");
        languageMap.put("Korean", "Koreanska");
        languageMap.put("Latvian", "Lettiska");
        languageMap.put("Lithuanian", "Litauiska");
        languageMap.put("Malay", "Malaysiska");
        languageMap.put("Mandarin", "Mandarin");
        languageMap.put("Norwegian", "Norska");
        languageMap.put("Polish", "Polska");
        languageMap.put("Portuguese", "Portugisiska");
        languageMap.put("Romanian", "Rumänska");
        languageMap.put("Russian", "Ryska");
        languageMap.put("Serbian", "Serbiska");
        languageMap.put("Slovakian", "Slovakiska");
        languageMap.put("Slovene", "Slovenska");
        languageMap.put("Slovenian", "Slovenska");
        languageMap.put("Spanish", "Spanska");
        languageMap.put("Swedish", "Svenska");
        languageMap.put("Tagalog", "Tagalog");
        languageMap.put("Thai", "Thailändska");
        languageMap.put("Turkish", "Turkiska");
        languageMap.put("Ukrainian", "Ukrainska");
        languageMap.put("Vietnamese", "Vietnamesiska");
        languageMap.put("Music", "Musik");
    }

    public static MovieResource movie2resource(TitleType movieTransfer) {
        MovieResource movieResource = new MovieResource()
                .setTitle(movieTransfer.getLocalTitle())
                .setOriginalTitle(movieTransfer.getOriginalTitle())
                .setDescription(movieTransfer.getDescription())
                .setRuntime(map2Runtime(movieTransfer.getRunningTime()))
                .setReleaseDate(mapSimpleDate(movieTransfer.getReleaseDate()))
                .setCountry(map2Country(movieTransfer.getCountry()))
                .setAgeRestriction(map2AgeRestriction(movieTransfer.getParentalRating().getValue()))
                .setStudios(map2Studios(movieTransfer.getStudios()))

                .setMovieFormatInfo(map2MovieFormatInfo(movieTransfer))
                .setMoviePersonalInfo(map2MoviePersonalInfo(movieTransfer));

        mapGenres(movieResource, movieTransfer.getGenres());

        mapCastAndCrew(movieResource, movieTransfer);

        return movieResource;
    }

    private static Time map2Runtime(String strRunningTimeMinutes) {
        if (strRunningTimeMinutes == null || strRunningTimeMinutes.trim().isEmpty()) {
            return null;
        }

        int runningTimeMinutes = Integer.parseInt(strRunningTimeMinutes);

        int hours = Math.floorDiv(runningTimeMinutes, 60);
        int minutes = Math.floorMod(runningTimeMinutes, 60);

        return new Time(hours, minutes, 0);
    }

    /**
     * Maps a date string of format "dd-MM-yyyy" to a Date object.
     */
    private static Date mapSimpleDate(String strDate) {
        if (strDate == null || StringUtils.isBlank(strDate)) {
            return null;
        }
        try {
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Maps a string of format "dd/MM/yyyy hh:mm:ss aa" to a Date object.
     */
    private static Date mapDateTime(String strDate) {
        if (strDate == null || StringUtils.isBlank(strDate)) {
            return null;
        }
        try {
            return dateFormat.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
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

    private static String map2AgeRestriction(int ratingValue) {
        return ageRestrictionMap.get(ratingValue);
    }

    private static Set<Studio> map2Studios(StudiosType mStudios) {
        Set<Studio> studios = new HashSet<>();

        if (mStudios != null && mStudios.getStudio() != null && !mStudios.getStudio().isEmpty()) {
            studios.addAll(
                    mStudios.getStudio().stream()
                            .map(mSt -> new Studio().setName(mSt))
                            .collect(Collectors.toSet()));
        }
        return studios;
    }

    private static MovieFormatInfoResource map2MovieFormatInfo(TitleType movieTransfer) {
        if (movieTransfer == null) {
            return new MovieFormatInfoResource();
        }
        return new MovieFormatInfoResource()
                .setCover(map2Cover(movieTransfer.getCovers()))  // This info doesn't exist in the DVDProfiler export.
                .setFormat(map2MediaFormat(movieTransfer.getType()))
                .setRegion(map2Region(movieTransfer.getRegionCodes()))
                .setUpcId(movieTransfer.getBarcode())
                .setDiscs(map2Discs(movieTransfer.getDiscs()))
                .setPictureFormat(movieTransfer.getAspectRatio())
                .setSystem(movieTransfer.getVideoStandard())
                .setAudioLanguages(map2AudioLanguages(movieTransfer.getAudioTracks())) // TODO: Other audio info too?
                .setSubtitles(map2Subtitles(movieTransfer.getSubtitles()));
    }

    private static MoviePersonalInfoResource map2MoviePersonalInfo(TitleType movieTransfer) {
        if (movieTransfer == null || movieTransfer.getPersonal() == null) {
            return new MoviePersonalInfoResource();
        }

        PersonalType personalInfo = movieTransfer.getPersonal();

        MoviePersonalInfoResource resource = new MoviePersonalInfoResource()
                .setGrade(map2Grade(personalInfo.getRating()))  // TODO: No rating done in MyMovies. Get from DVD Profiler instead.
                .setNotes(personalInfo.getNotes());

        if (personalInfo.getCollectionNumber() > 0) {
            resource.setArchiveNumber(personalInfo.getCollectionNumber());
        }

        return mapObtainInfo(resource, personalInfo);
    }

    private static CoverResource map2Cover(CoversType covers) {
        if (covers == null) {
            return null;
        }

        return new CoverResource()
                .setFgFileName(covers.getFront())
                .setBgFileName(covers.getBack());
    }

    private static Format map2MediaFormat(TypeType mediaType) {
        String formattedValue = formatMap.get(mediaType.getValue());
        String code = formattedValue != null ? formattedValue : mediaType.getValue();

        return new Format().setCode(code);
    }

    private static Integer map2Region(RegionCodesType regions) {
        // If region 2 is one of them, then choose it. Otherwise, pick the first one.
        String region = "0";

        if (regions != null && regions.getRegionCode() != null && !regions.getRegionCode().isEmpty()) {
            for (String iRegion : regions.getRegionCode()) {
                if (iRegion.equalsIgnoreCase("A") || iRegion.equalsIgnoreCase("B")) {
                    region = "2";
                } else if (iRegion.equals("2") || region.equals("0")) {
                    region = iRegion;
                }
            }
        }

        if (region.equals("NoRegionCode")) {
            region = "0";
        }

        return Integer.valueOf(region);
    }

    private static Integer map2Discs(DiscsType discs) {
        int nDiscs = 0;

        if (discs != null && discs.getDisc() != null && !discs.getDisc().isEmpty()) {
            nDiscs = discs.getDisc().size();
        }
        return nDiscs;
    }

    private static List<Language> map2AudioLanguages(AudioTracksType audio) {
        if (audio == null || audio.getAudioTrack() == null || audio.getAudioTrack().isEmpty()) {
            return null;
        }

        return audio.getAudioTrack().stream()
                .filter(a -> isSupportedLang(a.getLanguage()))
                .map(a -> new Language().setName(a.getLanguage()).setNameSwedish(lang2Swe(a.getLanguage())))
                .collect(Collectors.toList());
    }

    private static List<Language> map2Subtitles(SubtitlesType subtitles) {
        if (subtitles == null || subtitles.getSubtitle() == null || subtitles.getSubtitle().isEmpty()) {
            return null;
        }

        return subtitles.getSubtitle().stream()
                .filter(s -> isSupportedLang(s.getLanguage()))
                .map(s -> new Language().setName(s.getLanguage()).setNameSwedish(lang2Swe(s.getLanguage())))
                .collect(Collectors.toList());
    }

    private static boolean isSupportedLang(String lang) {
        boolean isNotUnsupportedLangString =
                StringUtils.isNotBlank(lang)
                && !"Other".equalsIgnoreCase(lang)
                && !"Commentary".equalsIgnoreCase(lang)
                && !"Audio Descriptive".equalsIgnoreCase(lang)
                && !"Trivia".equalsIgnoreCase(lang)
                && !"Music Only".equalsIgnoreCase(lang)
                && !"Music".equalsIgnoreCase(lang);

        // Ignore not yet supported languages.
        if (isNotUnsupportedLangString && languageMap.get(lang) == null) {
            System.out.println("Unsupported language (ignored): " + lang);
            return false;
        }
        return isNotUnsupportedLangString;
    }

    private static String lang2Swe(String langEng) {
        if (langEng == null || StringUtils.isBlank(langEng)) {
            return null;
        }

        String mappedLang = languageMap.get(langEng.trim());

        if (mappedLang != null) {
            return mappedLang;
        }
        System.out.println("Unsupported language: " + langEng);
        return "";
    }

    private static Double map2Grade(Integer rating) {
        if (rating == null || rating < 0) {
            return null;
        }
        switch (rating) {
            case 1:
                return 0.5D;
            case 2:
                return 1D;
            case 3:
                return 1.5D;
            case 4:
                return 2D;
            case 5:
                return 2.5D;
            case 6:
                return 3D;
            case 7:
                return 3.5D;
            case 8:
                return 4D;
            case 9:
                return 4.5D;
            case 10:
                return 5D;
            default:
                return null;
        }
    }

    private static MoviePersonalInfoResource mapObtainInfo(MoviePersonalInfoResource resource, PersonalType personalInfo) {
        if (personalInfo == null) {
            return resource;
        }
        Double obtainPrice = (personalInfo.getPurchasePrice() != null && personalInfo.getPurchasePrice().doubleValue() > 0D)
                ? personalInfo.getPurchasePrice().doubleValue() : null;

        return resource
                .setObtainDate(mapDateTime(personalInfo.getPurchased()))
                .setObtainPlace(personalInfo.getPurchasePlace())
                .setObtainPrice(obtainPrice)
                .setCurrency(personalInfo.getPurchaseCurrency());
    }

    private static void mapGenres(MovieResource movieResource, GenresType mGenres) {
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

                if (isGenreSupported(mappedGenreCode)) {
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
    }

    private static boolean isGenreSupported(String genreCode) {
        if (StringUtils.isBlank(genreCode)) {
            System.out.println("Unsupported genre (ignored): <empty value>");
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
            case "MYSTERY":
            case "ANIME":
                return true;
            default:
                System.out.println("Unsupported genre (ignored): " + genreCode);
                return false;
        }
    }

    private static void mapCastAndCrew(MovieResource movieResource, TitleType movieTransfer) {
        PersonsType persons = movieTransfer.getPersons();

        if (persons == null || persons.getPerson() == null || persons.getPerson().isEmpty()) {
            return;
        }

        // Actors
        List<CastAndCrewResource> actors = persons.getPerson().stream()
                .filter(p -> map2Role(p) == RoleEnum.ACTOR)
                .map(MovieMapperUtils::map2CastAndCrew).collect(Collectors.toList());
        if (!actors.isEmpty()) {
            movieResource.getActors().addAll(actors);
        }

        // Crew
        List<PersonType> crew = persons.getPerson().stream()
                .filter(p -> map2Role(p) != RoleEnum.ACTOR).collect(Collectors.toList());
        map2Crew(movieResource, crew);
    }

    // Map Movie to MovieResource

    private static void map2Crew(MovieResource movieResource, List<PersonType> crew) {
        if (crew.isEmpty()) {
            return;
        }

        crew.forEach(person -> {
            RoleEnum role = map2Role(person);

            switch (role) {
                case DIRECTOR:
                    movieResource.getDirectors().add(map2CastAndCrew(person));
                    break;
                case PRODUCER:
                    movieResource.getProducers().add(map2CastAndCrew(person));
                    break;
                case MUSIC:
                    movieResource.getMusic().add(map2CastAndCrew(person));
                    break;
                case WRITER:
                    movieResource.getWriters().add(map2CastAndCrew(person));
                    break;
                case EDITOR:
                    movieResource.getEditors().add(map2CastAndCrew(person));
                    break;
                case CINEMATOGRAPHY:
                    movieResource.getCinematography().add(map2CastAndCrew(person));
                    break;
                case SOUND:
                    movieResource.getSound().add(map2CastAndCrew(person));
                    break;
                case ART:
                    movieResource.getArt().add(map2CastAndCrew(person));
                    break;
                case MISC:
                    movieResource.getOtherRoles().add(map2CastAndCrew(person));
                    break;
            }
        });
    }

    private static CastAndCrewResource map2CastAndCrew(PersonType person) {
        if (person == null) {
            return null;
        }
        return new CastAndCrewResource()
                .setPersonRole(map2PersonRoleResource(person))
                .setCharacterName(person.getRole());
    }

    private static PersonRoleResource map2PersonRoleResource(PersonType person) {
        return new PersonRoleResource()
                .setPerson(new PersonResource().setName(person.getName()))
                .setRole(person.getRoleType() != null ? new Role().setCode(map2Role(person).name()) : null);
    }

    private static RoleEnum map2Role(PersonType person) {
        if ("Actor".equalsIgnoreCase(person.getRoleType())) {
            return RoleEnum.ACTOR;
        }
        if ("Director".equalsIgnoreCase(person.getRoleType())) {
            return RoleEnum.DIRECTOR;
        }
        if ("Producer".equalsIgnoreCase(person.getRoleType())) {
            return RoleEnum.PRODUCER;
        }
        if ("Music (Crew)".equalsIgnoreCase(person.getRoleType())) {
            return RoleEnum.MUSIC;
        }
        if ("Writer".equalsIgnoreCase(person.getRoleType())) {
            return RoleEnum.WRITER;
        }
        if ("Editor".equalsIgnoreCase(person.getRoleType())) {
            return RoleEnum.EDITOR;
        }
        if ("Cinematography".equalsIgnoreCase(person.getRoleType())) {
            return RoleEnum.CINEMATOGRAPHY;
        }
        if ("Sound".equalsIgnoreCase(person.getRoleType())) {
            return RoleEnum.SOUND;
        }
        if ("Art".equalsIgnoreCase(person.getRoleType())) {
            return RoleEnum.ART;
        } else {
            return RoleEnum.MISC;
        }
    }
}
