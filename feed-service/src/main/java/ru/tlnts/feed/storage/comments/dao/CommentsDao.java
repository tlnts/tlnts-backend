package ru.tlnts.feed.storage.comments.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tlnts.feed.storage.comments.entity.Comment;

/**
 * @author vasev-dm
 */
public interface CommentsDao extends JpaRepository<Comment, Integer> { }
