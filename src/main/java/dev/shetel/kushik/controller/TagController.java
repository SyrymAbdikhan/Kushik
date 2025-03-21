package dev.shetel.kushik.controller;

import dev.shetel.kushik.dto.CreateTagRequest;
import dev.shetel.kushik.dto.TagDto;
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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TagDto> createTag(@Valid @RequestBody CreateTagRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tagService.createTag(request));
    }

    @DeleteMapping("/{tagId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTag(@PathVariable Long tagId) {
        tagService.removeTag(tagId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/primary")
    public ResponseEntity<List<TagDto>> getPrimaryTags() {
        return ResponseEntity.ok(tagService.getPrimaryTags());
    }

    @GetMapping("/characteristics")
    public ResponseEntity<List<TagDto>> getCharacteristicTags() {
        return ResponseEntity.ok(tagService.getCharacteristicTags());
    }
}