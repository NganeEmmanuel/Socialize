package com.socialize.exception.exceptions;

public class NoChildCommentFoundException extends RuntimeException{

    public NoChildCommentFoundException(Long postID,Integer parentCommentId) {
        super("No comments found for Post with ID: " +postID +"  and parent comment with id: "+ parentCommentId);
    }

}
