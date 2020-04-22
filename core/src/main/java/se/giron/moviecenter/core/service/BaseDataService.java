package se.giron.moviecenter.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.giron.moviecenter.core.repository.FormatRepository;
import se.giron.moviecenter.core.repository.GenreRepository;
import se.giron.moviecenter.core.repository.RoleRepository;
import se.giron.moviecenter.core.repository.StudioRepository;
import se.giron.moviecenter.model.entity.Format;
import se.giron.moviecenter.model.entity.Genre;
import se.giron.moviecenter.model.entity.Role;
import se.giron.moviecenter.model.entity.Studio;

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
}
