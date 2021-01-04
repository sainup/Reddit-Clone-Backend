package com.sain.redditclone.redditclone.repository;

import com.sain.redditclone.redditclone.model.Post;
import com.sain.redditclone.redditclone.model.Subreddit;
import com.sain.redditclone.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
