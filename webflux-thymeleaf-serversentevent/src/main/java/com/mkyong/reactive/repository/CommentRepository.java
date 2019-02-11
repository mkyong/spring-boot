package com.mkyong.reactive.repository;

import com.mkyong.reactive.model.Comment;
import reactor.core.publisher.Flux;

public interface CommentRepository {

    Flux<Comment> findAll();

}
