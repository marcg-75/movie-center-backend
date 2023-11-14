package se.giron.moviecenter.core.map.submapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.giron.moviecenter.core.repository.StudioRepository;
import se.giron.moviecenter.model.entity.Studio;

import java.util.List;
import java.util.Optional;

@Component
public class StudioMapper {

    @Autowired
    private StudioRepository studioRepository;

    public Studio resource2Entity(Studio resource) {
        // Find existing language.
        Studio studio = null;

        if (resource.getId() != null) {
            Optional<Studio> oStudio = studioRepository.findById(resource.getId());

            if (oStudio.isPresent()) {
                studio = oStudio.get();
            }
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
}
