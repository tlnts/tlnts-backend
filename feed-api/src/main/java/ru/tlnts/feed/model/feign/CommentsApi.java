package ru.tlnts.feed.model.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tlnts.feed.model.api.CommentModel;
import ru.tlnts.feed.model.api.CreateCommentsRequest;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

/**
 * @author vasev-dm
 */
@FeignClient(name = "comments-api", url = "${feed.api.base-url}")
public interface CommentsApi {

    @PostMapping(path = "api/comments", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    CommentModel createComment(@RequestBody CreateCommentsRequest request);
}
