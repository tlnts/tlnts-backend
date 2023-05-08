package ru.tlnts.feed.model.api;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author vasev-dm
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CreateCommentsRequest(
        @NotBlank
        @JsonProperty("authorId")
        String authorId,
        @NotBlank
        @JsonProperty("itemId")
        String itemId,
        @NotBlank
        @JsonProperty("text")
        String text)
{ }
