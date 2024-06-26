package com.felipelourenco.workshopmongo.services;

import com.felipelourenco.workshopmongo.domain.Post;
import com.felipelourenco.workshopmongo.domain.User;
import com.felipelourenco.workshopmongo.dto.UserDTO;
import com.felipelourenco.workshopmongo.repository.PostRepository;
import com.felipelourenco.workshopmongo.repository.UserRepository;
import com.felipelourenco.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post findById(String id) {
        Post post = postRepository.findById(id).orElse(null);
        if(post == null) {
            throw new ObjectNotFoundException("User with id " + id + " not found");
        }

        return post;
    }

    public List<Post> findByTitle(String text){
        return postRepository.findByTitle(text);
    }

    public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
        maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
        return postRepository.fullSearch(text, minDate, maxDate);
    }
}
