package com.example.mediabase;

import com.example.mediabase.movies.MoviesBean;
import com.example.mediabase.movies.MoviesInitialList;
import com.example.mediabase.podcasts.Podcast;
import com.example.mediabase.podcasts.PodcastRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class RootController {
    private MoviesBean moviesBean;
    private PodcastRepository podcastRepository;
    private MoviesInitialList moviesInitialList;

    public RootController(MoviesBean moviesBean, PodcastRepository podcastRepository, MoviesInitialList moviesInitialList) {
        this.moviesBean = moviesBean;
        this.podcastRepository = podcastRepository;
        this.moviesInitialList = moviesInitialList;
    }

    @GetMapping("/")
    public String rootPath() {
        return "index";
    }

    @GetMapping("/setup")
    public String setupDatabase(Map<String, Object> model) {

        moviesInitialList.asList().forEach(moviesBean::addMovie);
        model.put("com/example/mediabase/movies", moviesBean.getMovies());

        podcastRepository.save(new Podcast("Wait Wait...Don't Tell Me!",
                "NPR's weekly current events quiz.",
                "https://www.npr.org/programs/wait-wait-dont-tell-me/"));
        podcastRepository.save(new Podcast("TED Radio Hour",
                "Guy Raz explores the emotions, insights, and discoveries that make us human.",
                "https://www.npr.org/programs/ted-radio-hour/"));
        podcastRepository.save(new Podcast("Fresh Air",
                "Hosted by Terry Gross, this show features intimate conversations with today's biggest luminaries.",
                "https://www.npr.org/programs/fresh-air/"));
        podcastRepository.save(new Podcast("NPR Politics Podcast",
                "The NPR Politics Podcast is where NPR's political reporters talk to you like they talk to each other.",
                "https://www.npr.org/sections/politics/"));

        model.put("podcasts", podcastRepository.findAll());

        return "setup";
    }

}
