package dev.shetel.kushik.mapper;

import dev.shetel.kushik.dto.request.CreateTagRequest;
import dev.shetel.kushik.dto.response.TagDto;
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
