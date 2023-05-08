package ru.tlnts.feed.storage.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.tlnts.feed.kafka.NotificationsKafkaProducer;
import ru.tlnts.feed.model.api.CommentModel;
import ru.tlnts.feed.model.api.CreateCommentsRequest;
import ru.tlnts.feed.model.kafka.CommentNotificationMessage;

/**
 * @author vasev-dm
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentsServiceSupport {

    private final CommentsService commentsService;
    private final NotificationsKafkaProducer notificationsKafkaProducer;
    @Qualifier("notificationsCommentsKafkaProducer")
    private final KafkaTemplate<String, CommentNotificationMessage> kafkaTemplate;

    public CommentModel createCommentAndSendNotification(CreateCommentsRequest request) {
        CommentModel comment = commentsService.createComment(request);
        sendNotification(comment);
        return comment;
    }

    private void sendNotification(CommentModel comment) {
        var message = toCommentNotificationMessage(comment);
        notificationsKafkaProducer.sendCommentsNotifications(message, kafkaTemplate);
        log.info("Successfully send comment notification '{}'", message);
    }

    private CommentNotificationMessage toCommentNotificationMessage(CommentModel commentModel) {
        return CommentNotificationMessage.builder()
                .authorId(commentModel.getAuthorId())
                .itemId(commentModel.getItemId())
                .text(commentModel.getText())
                .build();
    }
}
