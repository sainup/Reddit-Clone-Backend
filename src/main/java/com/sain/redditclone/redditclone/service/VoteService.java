package com.sain.redditclone.redditclone.service;

import com.sain.redditclone.redditclone.dto.VoteDto;
import com.sain.redditclone.redditclone.exception.PostNotFoundException;
import com.sain.redditclone.redditclone.exception.SpringRedditException;
import com.sain.redditclone.redditclone.model.Post;
import com.sain.redditclone.redditclone.model.Vote;
import com.sain.redditclone.redditclone.repository.PostRepository;
import com.sain.redditclone.redditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.sain.redditclone.redditclone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID : " + voteDto.getPostId().toString()));

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVotedIdDesc(post, authService.getCurrentUser());

        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
            +voteDto.getVoteType() + "'d for this post");
        }

        if(UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        }else{
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVote(voteDto,post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {

        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
