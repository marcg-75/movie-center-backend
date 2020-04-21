package se.giron.moviecenter.core.repository;

import se.giron.moviecenter.core.utils.DateUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.function.Function;

import static org.springframework.util.StringUtils.isEmpty;

abstract public class AbstractFilterSpecification<T> {

    public static final String WORD_SEPARATORS = "\t ,;:-!?";

    protected Predicate multiwordFreeTextPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, String q) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (!isEmpty(q)) {
            StringTokenizer wordTokenizer = new StringTokenizer(q, WORD_SEPARATORS);
            while (wordTokenizer.hasMoreElements()) {
                String word = wordTokenizer.nextToken();

                // * search for all is not allowed in innodb https://bugs.mysql.com/bug.php?id=78485
                if (isEmpty(word) || "*".equals(word)) {
                    continue;
                }
                predicate = criteriaBuilder.and(predicate, freeTextPredicate(root, criteriaBuilder, wordToSearchWord.apply(word)));
            }
        }

        return predicate;
    }

    protected Predicate freeTextPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, String attribute, String word) {
        Expression<Double> matchExpression = criteriaBuilder.function("match1", Double.class,
                root.get(attribute),
                criteriaBuilder.literal(wordToSearchWord.apply(word)));

        return criteriaBuilder.greaterThan(matchExpression, 0.);
    }

    private Function<String, String> wordToSearchWord = word -> (word != null && !word.endsWith("\"") && !word.endsWith("'")) ? word + "*" : word;

    protected Predicate freeTextPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, String word) {
        return criteriaBuilder.conjunction();
    }

    protected Predicate likePredicate(Root<T> root, CriteriaBuilder criteriaBuilder, String attribute, String word) {
        return criteriaBuilder.like(criteriaBuilder.upper(root.get(attribute)), "%" + word + "%");
    }

    Date resolveDateFrom(Date dateFrom) {
        if (isEmpty(dateFrom)) {
            return DateUtils.getDateForThePast();
        }

        return dateFrom;
    }

    Date resolveDateTo(Date dateTo) {
        if (isEmpty(dateTo)) {
            return DateUtils.getDateForTheFuture();
        }

        return dateTo;
    }

    static Optional<Integer> parseInt(String toParse) {
        try {
            return Optional.of(Integer.parseInt(toParse));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}