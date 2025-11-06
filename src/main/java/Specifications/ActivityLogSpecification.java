package com.example.login.Specifications;

import com.example.login.Models.ActivityLog;
import com.example.login.Models.ActivityLog.ActivityStatus;
import com.example.login.Models.ActivityLog.ActivityType;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogSpecification {

    public static Specification<ActivityLog> withFilters(
            String search,
            ActivityType type,
            ActivityStatus status,
            LocalDateTime startDate
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtre de recherche (userName, action, target)
            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";
                Predicate searchPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("userName")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("action")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("target")), searchPattern)
                );
                predicates.add(searchPredicate);
            }

            // Filtre par type d'activité
            if (type != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }

            // Filtre par statut
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            // Filtre par date de début
            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("timestamp"), startDate));
            }

            // Tri par timestamp décroissant
            query.orderBy(criteriaBuilder.desc(root.get("timestamp")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}