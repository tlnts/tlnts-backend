package ru.tlnts.feed.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author vasev-dm
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateCommentsRequest(
        @JsonProperty("authorId")
        String authorId,
        @JsonProperty("itemId")
        String itemId,
        @JsonProperty("text")
        String text)
{ }
