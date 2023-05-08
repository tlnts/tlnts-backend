package ru.tlnts.feed.storage.comments.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author vasev-dm
 */
@Data
@Accessors(chain = true)
@Entity(name = "comments")
@Table(schema = "public", name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @Column(name = "author_id", nullable = false)
    private String authorId;

    @Column(name = "item_id", nullable = false)
    private String itemId;

    @Column(name = "text", nullable = false)
    private String text;
}
