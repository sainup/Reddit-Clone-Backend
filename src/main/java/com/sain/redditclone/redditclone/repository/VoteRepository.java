package com.sain.redditclone.redditclone.repository;

import com.sain.redditclone.redditclone.model.Post;
import com.sain.redditclone.redditclone.model.User;
import com.sain.redditclone.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Long> {

    Optional<Vote> findTopByPostAndUserOrderByVotedIdDesc(Post post, User currentUser);
}
