package com.sain.redditclone.redditclone.service;

import com.sain.redditclone.redditclone.dto.PostRequest;
import com.sain.redditclone.redditclone.dto.PostResponse;
import com.sain.redditclone.redditclone.exception.PostNotFoundException;
import com.sain.redditclone.redditclone.exception.SpringRedditException;
import com.sain.redditclone.redditclone.exception.SubredditNotFoundException;
import com.sain.redditclone.redditclone.mapper.PostMapper;
import com.sain.redditclone.redditclone.model.Post;
import com.sain.redditclone.redditclone.model.Subreddit;
import com.sain.redditclone.redditclone.model.User;
import com.sain.redditclone.redditclone.repository.PostRepository;
import com.sain.redditclone.redditclone.repository.SubredditRepository;
import com.sain.redditclone.redditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final PostMapper postMapper;
    private final AuthService authService;
    private final UserRepository userRepository;

    public Post save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SpringRedditException("Subreddit not found with name : " + postRequest.getSubredditName()));

        User currentUser = authService.getCurrentUser();
        log.info("CURRENT USERRR ->>>>>>>>>>>>>>>>>>" +  currentUser.getUsername());
        log.info(("SUBREDDIT ->>>>>>>>>>>>>>>>>>" + subreddit.getName()));
        return postRepository.save(postMapper.map(postRequest, subreddit, currentUser));


    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        log.info("FROM GET RESPONSE USER AND SUBREDDIT " + post.getSubreddit().getName()+", " + post.getUser().getUsername());
        return postMapper.mapToDto(post);
    }


    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {


        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());

    }


    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException(id.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        List<Post> posts = postRepository.findByUser(user);

        return posts.stream()
                .map(postMapper::mapToDto)
                .collect(toList());

    }
}
