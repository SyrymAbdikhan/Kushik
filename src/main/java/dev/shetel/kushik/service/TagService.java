package dev.shetel.kushik.service;

import dev.shetel.kushik.dto.CreateTagRequest;
import dev.shetel.kushik.dto.TagDto;
import dev.shetel.kushik.mapper.TagMapper;
import dev.shetel.kushik.model.Tag;
import dev.shetel.kushik.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagDto createTag(CreateTagRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Tag already exists");
        }

        Tag tag = tagMapper.toEntity(request);
        Tag savedTag = tagRepository.save(tag);
        return tagMapper.toDto(savedTag);
    }

    public void removeTag(Long tagId) {
        tagRepository.deleteById(tagId);
    }

    public List<TagDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toDto)
                .toList();
    }

    public List<TagDto> getPrimaryTags() {
        return tagRepository.findByIsPrimary(true)
                .stream()
                .map(tagMapper::toDto)
                .toList();
    }

    public List<TagDto> getCharacteristicTags() {
        return tagRepository.findByIsPrimary(false)
                .stream()
                .map(tagMapper::toDto)
                .toList();
    }
}
