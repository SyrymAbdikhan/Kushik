package dev.shetel.kushik.service;

import dev.shetel.kushik.dto.request.CreateTagRequest;
import dev.shetel.kushik.mapper.TagMapper;
import dev.shetel.kushik.model.Tag;
import dev.shetel.kushik.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public Tag createTag(CreateTagRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Tag already exists");
        }

        Tag tag = tagMapper.toEntity(request);
        return tagRepository.save(tag);
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

    public List<Tag> getTagByIds(Set<Long> ids) {
        List<Tag> foundTags = tagRepository.findAllById(ids);
        if(foundTags.size() != ids.size()) {
            throw new EntityNotFoundException("Not all specified tag IDs exist");
        }
        return foundTags;
    }
}
