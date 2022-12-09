package com.fastcampus.sns.controller;

import com.fastcampus.sns.controller.request.PostWriteRequest;
import com.fastcampus.sns.controller.response.Response;
import com.fastcampus.sns.controller.response.UserLoginResponse;
import com.fastcampus.sns.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;
    
    @PostMapping
    public Response<Void> create(@RequestBody PostWriteRequest request, Authentication authentication) {
        postService.create(authentication.getName(), request.getTitle(), request.getBody());
        return Response.success();
    }

}
