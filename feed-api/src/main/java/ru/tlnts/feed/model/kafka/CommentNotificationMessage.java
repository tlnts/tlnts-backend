package ru.tlnts.feed.model.kafka;

import lombok.Builder;
import lombok.Data;

/**
 * @author vasev-dm
 */
@Data
@Builder
public class CommentNotificationMessage {

    private final String authorId;
    private final String itemId;
    private final String text;
}
