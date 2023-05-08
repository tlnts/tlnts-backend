package ru.tlnts.feed.api.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tlnts.common.api.ApiResponse;
import ru.tlnts.common.api.ApiStatusCode;
import ru.tlnts.feed.model.api.CreateCommentsRequest;
import ru.tlnts.feed.storage.comments.CommentsService;
import ru.tlnts.feed.storage.comments.model.CommentModel;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author vasev-dm
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(
        path = "api/v1/comments",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
)
public class CommentsV1Controller {

    private final CommentsService commentsService;

    @PostMapping
    public ApiResponse<CommentModel> createComment(@RequestBody CreateCommentsRequest request) {
        log.debug("Received create comment request '{}'", request);
        var response = commentsService.createComment(request);
        log.debug("Returned create comment response '{}'", response);
        return ApiResponse.success(ApiStatusCode.OK, response);
    }
}
