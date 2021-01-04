package com.sain.redditclone.redditclone.repository;

import com.sain.redditclone.redditclone.model.Comment;
import com.sain.redditclone.redditclone.model.Post;
import com.sain.redditclone.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findAllByUser(User user);

}
