package com.example.mediabase.podcasts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/podcasts")
public class PodcastController {

    private PodcastRepository podcastRepository;

    public PodcastController(PodcastRepository podcastRepository) {
        this.podcastRepository = podcastRepository;
    }

    @PostMapping
    public ResponseEntity<Podcast> create(@RequestBody Podcast podcast) {

        podcastRepository.save(podcast);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Podcast> delete(@PathVariable Long id) {
        Optional<Podcast> doomed = podcastRepository.findById(id);
        if (doomed != null) podcastRepository.delete(doomed.get());
        HttpStatus status = (doomed != null) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }

    @GetMapping()
    public List<Podcast> getAll(){
        return (List<Podcast>) podcastRepository.findAll();
    }

}