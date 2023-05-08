package ru.tlnts.feed.storage.comments;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.tlnts.common.exception.TlntsRuntimeException;
import ru.tlnts.feed.configuration.properties.NotificationsKafkaProperties;
import ru.tlnts.feed.model.api.CreateCommentsRequest;
import ru.tlnts.feed.model.kafka.CommentNotificationMessage;
import ru.tlnts.feed.storage.comments.dao.CommentsDao;
import ru.tlnts.feed.storage.comments.entity.Comment;
import ru.tlnts.feed.storage.comments.model.CommentModel;

/**
 * @author vasev-dm
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsDao commentsDao;
    private final NotificationsKafkaProperties properties;
    @Qualifier("notificationsCommentsKafkaProducer")
    private final KafkaTemplate<String, CommentNotificationMessage> kafkaTemplate;

    public CommentModel createComment(CreateCommentsRequest createCommentsRequest) {
        try {
            var comment = new Comment()
                    .setAuthorId(createCommentsRequest.authorId())
                    .setItemId(createCommentsRequest.itemId())
                    .setText(createCommentsRequest.text());
            var savedComment = toCommentModel(commentsDao.save(comment));
            log.info("Successfully created comment '{}'", savedComment);
            sendCommentNotification(savedComment);
            return savedComment;
        } catch (Exception e) {
            log.error("Failed to create comment", e);
            throw new TlntsRuntimeException(e.getMessage());
        }
    }

    @Async
    public void sendCommentNotification(CommentModel comment) {
        String messageKey = UUID.randomUUID().toString();
        var notificationModel = toCommentNotificationMessage(comment);
        kafkaTemplate.send(properties.getNotificationsTopic(), messageKey, notificationModel);
        log.info("Successfully send comment notification '{}'", notificationModel);
    }

    private CommentModel toCommentModel(Comment comment) {
        return CommentModel.builder()
                .id(comment.getId().longValue())
                .authorId(comment.getAuthorId())
                .itemId(comment.getItemId())
                .text(comment.getText())
                .build();
    }

    private CommentNotificationMessage toCommentNotificationMessage(CommentModel commentModel) {
        return CommentNotificationMessage.builder()
                .authorId(commentModel.getAuthorId())
                .itemId(commentModel.getItemId())
                .text(commentModel.getText())
                .build();
    }
}
