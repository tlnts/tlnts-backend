package ru.tlnts.feed.storage.comments.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author vasev-dm
 */
@Data
@Builder
public class CommentModel {

    private final Long id;
    private final String authorId;
    private final String itemId;
    private final String text;
}
