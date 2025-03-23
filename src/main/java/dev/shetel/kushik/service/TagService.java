package dev.shetel.kushik.service;

import dev.shetel.kushik.dto.request.CreateTagRequest;
import dev.shetel.kushik.model.Tag;
import dev.shetel.kushik.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional
    public List<Tag> createTags(List<CreateTagRequest> requests) {
        List<Tag> existingTags = tagRepository.findByNameIn(
                requests.stream()
                        .map(CreateTagRequest::getName)
                        .collect(Collectors.toSet())
        );

        List<String> existingTagNames = existingTags.stream()
                .map(Tag::getName)
                .toList();

        List<Tag> newTags = requests.stream()
                .filter(tag -> !existingTagNames.contains(tag.getName()))
                .map(tag -> Tag.builder()
                        .name(tag.getName())
                        .isPrimary(tag.isPrimary())
                        .build())
                .toList();

        List<Tag> savedTags = new ArrayList<>();
        if(!newTags.isEmpty()) {
            savedTags = tagRepository.saveAll(newTags);
        }

        return savedTags;
    }

    public void removeTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getPrimaryTags() {
        return tagRepository.findByIsPrimary(true);
    }

    public List<Tag> getCharacteristicTags() {
        return tagRepository.findByIsPrimary(false);
    }

    public Set<Tag> getTagByIds(Set<Long> ids) {
        List<Tag> foundTags = tagRepository.findAllById(ids);
        if(foundTags.size() != ids.size()) {
            throw new EntityNotFoundException("Not all specified tag IDs exist");
        }
        return new HashSet<>(foundTags);
    }
}
