package dev.shetel.kushik.mapper;

import dev.shetel.kushik.dto.CreateTagRequest;
import dev.shetel.kushik.dto.TagDto;
import dev.shetel.kushik.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public TagDto toDto(Tag tag) {
        return new TagDto(
                tag.getTagId(),
                tag.getName(),
                tag.getIsPrimary()
        );
    }

    public Tag toEntity(CreateTagRequest request) {
        return Tag.builder()
                .name(request.getName())
                .isPrimary(request.isPrimary())
                .build();
    }
}
