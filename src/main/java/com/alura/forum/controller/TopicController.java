package com.alura.forum.controller;

import com.alura.forum.dto.TopicDTO;
import com.alura.forum.model.Topic;
import com.alura.forum.service.TopicService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        return ResponseEntity.ok(topicService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Topic> createTopic(
            @Valid @RequestBody TopicDTO topicDTO,
            @AuthenticationPrincipal com.alura.forum.model.User currentUser) {
        Topic created = topicService.create(topicDTO, currentUser);
        return ResponseEntity
                .created(URI.create("/api/topics/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(
            @PathVariable Long id,
            @Valid @RequestBody TopicDTO topicDTO,
            @AuthenticationPrincipal com.alura.forum.model.User currentUser) {
        return ResponseEntity.ok(topicService.update(id, topicDTO, currentUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(
            @PathVariable Long id,
            @AuthenticationPrincipal com.alura.forum.model.User currentUser) {
        topicService.delete(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}