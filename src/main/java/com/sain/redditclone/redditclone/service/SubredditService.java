package com.sain.redditclone.redditclone.service;

import com.sain.redditclone.redditclone.dto.SubredditDto;
import com.sain.redditclone.redditclone.exception.SpringRedditException;
import com.sain.redditclone.redditclone.mapper.SubredditMapper;
import com.sain.redditclone.redditclone.model.Subreddit;
import com.sain.redditclone.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {


    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
      Subreddit subreddit = subredditRepository.save( subredditMapper.mapDtoToSubreddit(subredditDto));
      subredditDto.setId(subreddit.getId());
      return subredditDto;
    }


    @Transactional
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(()-> new SpringRedditException("No subreddit found with id : " + id));
        return subredditMapper.mapSubredditToDto(subreddit);

    }
}
