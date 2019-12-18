package com.example.mediabase;


import com.example.mediabase.moviesui.MovieClient;
import com.example.mediabase.moviesui.MoviesInitialList;
import com.example.mediabase.podcastsui.PodcastClient;
import com.example.mediabase.podcastsui.PodcastsInitialList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class RootController {
    private MovieClient movieClient;
    private PodcastClient podcastClient;
    //private PodcastRepository podcastRepository;
    private MoviesInitialList moviesInitialList;
    private PodcastsInitialList podcastsInitialList;

    public RootController(MovieClient movieClient, PodcastClient podcastClient, MoviesInitialList moviesInitialList, PodcastsInitialList podcastsInitialList) {
        this.movieClient = movieClient;
        this.podcastClient = podcastClient;
        this.moviesInitialList = moviesInitialList;
        this.podcastsInitialList = podcastsInitialList;
    }

    @GetMapping("/")
    public String rootPath() {
        return "index";
    }

    @GetMapping("/setup")
    public String setupDatabase(Map<String, Object> model) {

        moviesInitialList.asList().forEach(movieClient::create);
        model.put("com/example/mediabase/movies", movieClient.getAll());

        podcastsInitialList.asList().forEach(podcastClient::create);
        model.put("com/example/mediabase/podcasts", podcastClient.getAll());


        return "setup";
    }

}