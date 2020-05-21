package se.giron.moviecenter.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.giron.moviecenter.core.repository.*;
import se.giron.moviecenter.model.entity.*;

import java.util.List;

@Service
public class BaseDataService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FormatRepository formatRepository;

    @Autowired
    private StudioRepository studioRepository;

    @Autowired
    private LanguageRepository languageRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAllByOrderByNameAsc();
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAllByOrderByNameAsc();
    }

    public List<Format> getAllFormats() {
        return formatRepository.findAllByOrderByNameAsc();
    }

    public List<Studio> getAllStudios() {
        return studioRepository.findAllByOrderByNameAsc();
    }

    public List<Language> getAllLanguages() {
        return languageRepository.findAllByOrderByIdAsc();
    }
}
