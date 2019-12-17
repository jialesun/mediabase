package com.example.mediabase.podcasts;

import com.example.mediabase.podcastsui.PodcastUI;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PodcastRepository extends CrudRepository<PodcastUI, Long> {

    Optional<PodcastUI> findById(Long id);
}
