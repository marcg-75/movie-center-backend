package se.giron.moviecenter.core.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import se.giron.moviecenter.model.entity.*;
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

        if (!StringUtils.isEmpty(filter.getGenre())) {
            final SetJoin<Movie, MovieGenre> genreJoin = root.join(Movie_.genres, JoinType.LEFT);
            final Predicate genrePredicate = criteriaBuilder.equal(genreJoin.get(MovieGenre_.genre), new Genre().setCode(filter.getGenre()));
            predicate = criteriaBuilder.and(predicate, genrePredicate);
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