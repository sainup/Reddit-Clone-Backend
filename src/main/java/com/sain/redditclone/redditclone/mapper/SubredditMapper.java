package com.sain.redditclone.redditclone.mapper;

import com.sain.redditclone.redditclone.dto.SubredditDto;
import com.sain.redditclone.redditclone.model.Post;
import com.sain.redditclone.redditclone.model.Subreddit;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts",expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts){


        System.out.println("NUMBER OF POSTS IN SUBREDDIT : " +  numberOfPosts.size());
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts",ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
