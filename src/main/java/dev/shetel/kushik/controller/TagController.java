package dev.shetel.kushik.controller;

import dev.shetel.kushik.dto.request.CreateTagRequest;
import dev.shetel.kushik.dto.response.TagDto;
import dev.shetel.kushik.mapper.TagMapper;
import dev.shetel.kushik.model.Tag;
import dev.shetel.kushik.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody CreateTagRequest request) {
        Tag tag = tagService.createTag(request);
        TagDto tagDto = tagMapper.toDto(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(tagDto);
    }

    @DeleteMapping("/{tagId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTag(@PathVariable Long tagId) {
        tagService.removeTag(tagId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        List<TagDto> tagDtos = tags.stream()
                .map(tagMapper::toDto).toList();
        return ResponseEntity.ok(tagDtos);
    }

    @GetMapping("/primary")
    public ResponseEntity<List<TagDto>> getPrimaryTags() {
        List<Tag> tags = tagService.getPrimaryTags();
        List<TagDto> tagDtos = tags.stream()
                .map(tagMapper::toDto).toList();
        return ResponseEntity.ok(tagDtos);
    }

    @GetMapping("/characteristics")
    public ResponseEntity<List<TagDto>> getCharacteristicTags() {
        List<Tag> tags = tagService.getCharacteristicTags();
        List<TagDto> tagDtos = tags.stream()
                .map(tagMapper::toDto).toList();
        return ResponseEntity.ok(tagDtos);
    }
}