package com.redscooter.API.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByVisibleTrue();

    Optional<Category> findCategoryById(Long id);

    Optional<Category> findCategoryByNameLowerCased(String nameLowerCased);

    Optional<Category> findCategoryByName(String name);

    boolean existsByNameLowerCased(String nameLowerCased);

    boolean existsByName(String name);
}
