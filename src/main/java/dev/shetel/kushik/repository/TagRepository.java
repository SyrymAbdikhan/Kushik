package dev.shetel.kushik.repository;

import dev.shetel.kushik.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByIsPrimary(boolean isPrimary);
    List<Tag> findByNameIn(Set<String> tagNames);
}
