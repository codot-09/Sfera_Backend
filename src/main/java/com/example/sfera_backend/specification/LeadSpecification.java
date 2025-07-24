package com.example.sfera_backend.specification;

import com.example.sfera_backend.entity.Lead;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class LeadSpecification {

    public static Specification<Lead> build(String field,
                                            LocalDate start,
                                            LocalDate end,
                                            Boolean confirmed) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            boolean confirmedValue = confirmed == null || confirmed;
            predicates = cb.and(predicates, cb.equal(root.get("confirmed"), confirmedValue));

            if (field != null && !field.isBlank()) {
                String pattern = "%" + field.toLowerCase() + "%";
                predicates = cb.and(predicates, cb.or(
                        cb.like(cb.lower(root.get("fullName")), pattern),
                        cb.like(cb.lower(root.get("phone")), pattern)
                ));
            }

            if (start != null && end != null) {
                predicates = cb.and(predicates,
                        cb.between(root.get("createdDate"), start.atStartOfDay(), end.atTime(23, 59, 59))
                );
            } else if (start != null) {
                predicates = cb.and(predicates,
                        cb.greaterThanOrEqualTo(root.get("createdDate"), start.atStartOfDay())
                );
            } else if (end != null) {
                predicates = cb.and(predicates,
                        cb.lessThanOrEqualTo(root.get("createdDate"), end.atTime(23, 59, 59))
                );
            }

            return predicates;
        };
    }
}
