package dev.shetel.kushik.repository;

import dev.shetel.kushik.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByName(String name);
    List<Tag> findByIsPrimary(boolean isPrimary);
}
