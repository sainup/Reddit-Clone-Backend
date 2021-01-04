package com.sain.redditclone.redditclone.repository;

import com.sain.redditclone.redditclone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Optional<Subreddit> findByName(String subredditName);
}
