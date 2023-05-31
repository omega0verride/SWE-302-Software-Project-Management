package com.redscooter.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.List;


@Repository
public class PostgreSQLFullTextSearchUtils {

    private static final String[] POSTGRESQL_WEIGHT_INDEXES = new String[]{"A", "B", "C", "D"};
    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Modifying
    public void createCustomUnAccent() {
        Query query = entityManager.createNativeQuery("CREATE OR REPLACE FUNCTION public.f_unaccent(text) RETURNS text LANGUAGE sql IMMUTABLE PARALLEL SAFE STRICT AS $func$ SELECT public.unaccent('public.unaccent', $1)  -- schema-qualify function and dictionary $func$;");
        query.executeUpdate();
    }


    // note https://stackoverflow.com/questions/12100638/error-when-creating-unaccent-extension-on-postgresql
    // sudo yum install postgres*contrib
    @Transactional
    @Modifying
    public void addUnAccentExtension() {
        Query query = entityManager.createNativeQuery("CREATE EXTENSION IF NOT EXISTS \"unaccent\"");
        query.executeUpdate();
    }

    /*
    * This function dynamically builds the following query:
        ALTER TABLE products ADD COLUMN search_vector tsvector GENERATED ALWAYS AS (
            setweight(to_tsvector('english', f_unaccent(coalesce(title, ''))), 'A')
            || setweight(to_tsvector('english', f_unaccent(coalesce(description, ''))), 'B')
          ) STORED;
    * */
    @Transactional
    @Modifying
    public void addSearchTokenizerListenerToTable(String tableName, String searchVectorColumnName, List<String> textColumnsToSearchSortedByPriority) {
        if (textColumnsToSearchSortedByPriority.size() == 0)
            throw new InvalidParameterException("You must specify at least 1 column.");
        if (textColumnsToSearchSortedByPriority.size() > 4)
            throw new InvalidParameterException("You cannot specify more than 4 columns in the same search vector. \"setweight returns a copy of the input vector in which every position has been labeled with the given weight, either A, B, C, or D.\"");

        StringBuilder queryString = new StringBuilder("ALTER TABLE ").append(tableName)
                .append(" ADD COLUMN IF NOT EXISTS ").append(searchVectorColumnName).append(" tsvector")
                .append("  GENERATED ALWAYS AS (")
                .append(" setweight(to_tsvector('english', f_unaccent(coalesce(").append(textColumnsToSearchSortedByPriority.get(0)).append(", ''))), '").append(POSTGRESQL_WEIGHT_INDEXES[0]).append("')");

        for (int i = 1; i < textColumnsToSearchSortedByPriority.size(); i++)
            queryString.append(" || setweight(to_tsvector('english', f_unaccent(coalesce(").append(textColumnsToSearchSortedByPriority.get(i)).append(", ''))), '").append(POSTGRESQL_WEIGHT_INDEXES[i]).append("')");
        queryString.append(") STORED;");
        Query query = entityManager.createNativeQuery(queryString.toString());
        query.executeUpdate();
    }
}
