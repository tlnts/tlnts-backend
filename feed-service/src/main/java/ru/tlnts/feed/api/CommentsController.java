package ru.tlnts.feed.api;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tlnts.feed.model.api.CommentModel;
import ru.tlnts.feed.model.api.CreateCommentsRequest;
import ru.tlnts.feed.model.feign.CommentsApi;
import ru.tlnts.feed.storage.comments.CommentsServiceSupport;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author vasev-dm
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(
        path = "api/comments",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
public class CommentsController
        implements CommentsApi
{
    private final CommentsServiceSupport commentsService;

    @Override
    @PostMapping
    public CommentModel createComment(@Valid @RequestBody CreateCommentsRequest request) {
        log.debug("Received create comment request '{}'", request);
        var response = commentsService.createCommentAndSendNotification(request);
        log.debug("Returned create comment response '{}'", response);
        return response;
    }
}
