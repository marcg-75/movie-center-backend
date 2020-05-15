package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import se.giron.moviecenter.model.entity.Format;
import se.giron.moviecenter.model.entity.Genre;
import se.giron.moviecenter.model.entity.Movie;
import se.giron.moviecenter.model.resource.filter.MovieFilter;

import javax.persistence.criteria.*;


public class MovieFilterSpecification extends AbstractFilterSpecification<Movie> implements Specification<Movie> {

    private MovieFilter filter;

    public MovieFilterSpecification(MovieFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Movie> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (!StringUtils.isEmpty(filter.getTitle())) {
            predicate = criteriaBuilder.and(predicate, likePredicate(root, criteriaBuilder, "title", filter.getTitle()));
        }

        if (!StringUtils.isEmpty(filter.getMainGenre())) {
            Predicate mainGenrePredicate = criteriaBuilder.equal(root.get("mainGenre"), new Genre().setCode(filter.getMainGenre()));
            predicate = criteriaBuilder.and(predicate, mainGenrePredicate);
        }

        if (!StringUtils.isEmpty(filter.getFormat())) {
            Predicate formatPredicate = criteriaBuilder.equal(root.<Movie>get("movieFormatInfo").<Format>get("format"), new Format().setCode(filter.getFormat()));
            predicate = criteriaBuilder.and(predicate, formatPredicate);
        }

        if (!StringUtils.isEmpty(filter.getGrade())) {
            Predicate gradePredicate = criteriaBuilder.equal(root.<Movie>get("moviePersonalInfo").<Integer>get("grade"), filter.getGrade());
            predicate = criteriaBuilder.and(predicate, gradePredicate);
        }

        if (!StringUtils.isEmpty(filter.getQ())) {
            predicate = criteriaBuilder.and(predicate, multiwordFreeTextPredicate(root, criteriaBuilder, filter.getQ()));
        }

        return predicate;
    }

    protected Predicate freeTextPredicate(Root<Movie> root, CriteriaBuilder criteriaBuilder, String word) {

        Expression<Double> matchExpression = criteriaBuilder.function("match1", Double.class, root.get("title"), root.get("description"), criteriaBuilder.literal(word));

        return criteriaBuilder.greaterThan(matchExpression, 0.);
    }
}