package com.sain.redditclone.redditclone.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.sain.redditclone.redditclone.dto.PostRequest;
import com.sain.redditclone.redditclone.dto.PostResponse;
import com.sain.redditclone.redditclone.model.Post;
import com.sain.redditclone.redditclone.model.Subreddit;
import com.sain.redditclone.redditclone.model.User;
import com.sain.redditclone.redditclone.repository.CommentRepository;
import com.sain.redditclone.redditclone.repository.VoteRepository;
import com.sain.redditclone.redditclone.service.AuthService;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")

public abstract class PostMapper {


    @Autowired
    private  CommentRepository commentRepository;
    @Autowired
    private  VoteRepository voteRepository;
    @Autowired
    private  AuthService authService;


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post){
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }
}
