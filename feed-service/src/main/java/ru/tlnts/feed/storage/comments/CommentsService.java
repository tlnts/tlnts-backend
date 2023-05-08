package ru.tlnts.feed.storage.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tlnts.feed.model.api.CommentModel;
import ru.tlnts.feed.model.api.CreateCommentsRequest;
import ru.tlnts.feed.storage.comments.dao.CommentsDao;
import ru.tlnts.feed.storage.comments.entity.Comment;

/**
 * @author vasev-dm
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsDao commentsDao;

    @Transactional
    public CommentModel createComment(CreateCommentsRequest createCommentsRequest) {
        var comment = new Comment()
                .setAuthorId(createCommentsRequest.authorId())
                .setItemId(createCommentsRequest.itemId())
                .setText(createCommentsRequest.text());
        var savedComment = toCommentModel(commentsDao.save(comment));
        log.info("Successfully created comment '{}'", savedComment);
        return savedComment;
    }

    private CommentModel toCommentModel(Comment comment) {
        return CommentModel.builder()
                .id(comment.getId())
                .authorId(comment.getAuthorId())
                .itemId(comment.getItemId())
                .text(comment.getText())
                .build();
    }
}
