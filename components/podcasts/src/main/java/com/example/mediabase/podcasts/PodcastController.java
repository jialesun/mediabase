package com.example.mediabase.podcasts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/podcasts")
public class PodcastController {

    private PodcastRepository podcastsRepository;

    public PodcastController(PodcastRepository podcastRepository) {
        this.podcastsRepository = podcastsRepository;
    }

    @PostMapping
    public ResponseEntity<Podcast> create(@RequestBody Podcast podcast) {

        podcastsRepository.save(podcast);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Podcast> delete(@PathVariable Long id) {
        Optional<Podcast> doomed = podcastsRepository.findById(id);
        if (doomed != null) podcastsRepository.delete(doomed.get());
        HttpStatus status = (doomed != null) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }

    @GetMapping()
    public String allPodcasts(Map<String, Object> model) {

        return "";
    }

}