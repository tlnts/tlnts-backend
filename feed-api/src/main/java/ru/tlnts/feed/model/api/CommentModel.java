package ru.tlnts.feed.model.api;

import lombok.Builder;
import lombok.Data;

/**
 * @author vasev-dm
 */
@Data
@Builder
public class CommentModel {

    private final Integer id;
    private final String authorId;
    private final String itemId;
    private final String text;
}
