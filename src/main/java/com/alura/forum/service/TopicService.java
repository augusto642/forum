package com.alura.forum.service;

import com.alura.forum.dto.TopicDTO;
import com.alura.forum.model.Topic;
import com.alura.forum.repository.TopicRepository;
import com.alura.forum.exception.UnauthorizedException;
import com.alura.forum.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Topic findById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow();
    }

    @Transactional
    public Topic create(TopicDTO topicDTO, User currentUser) {
        Topic topic = new Topic();
        topic.setTitle(topicDTO.getTitle());
        topic.setContent(topicDTO.getContent());
        topic.setAuthor(currentUser);
        topic.setCreatedAt(LocalDateTime.now());
        return topicRepository.save(topic);
    }

    @Transactional
    public Topic update(Long id, TopicDTO topicDTO, User currentUser) {
        Topic topic = findById(id);

        if (!topic.getAuthor().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only update your own topics");
        }

        topic.setTitle(topicDTO.getTitle());
        topic.setContent(topicDTO.getContent());
        topic.setUpdatedAt(LocalDateTime.now());

        return topicRepository.save(topic);
    }

    @Transactional
    public void delete(Long id, User currentUser) {
        Topic topic = findById(id);

        if (!topic.getAuthor().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only delete your own topics");
        }

        topicRepository.delete(topic);
    }
}