package com.sain.redditclone.redditclone.exception;

public class SubredditNotFoundException extends RuntimeException {
    public SubredditNotFoundException(String message){
        super(message);
    }
}
