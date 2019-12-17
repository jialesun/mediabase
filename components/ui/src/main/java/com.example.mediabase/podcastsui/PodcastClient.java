package com.example.mediabase.podcastsui;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class PodcastClient {

    private static ParameterizedTypeReference<List<PodcastUI>> podcastListType = new ParameterizedTypeReference<List<PodcastUI>>() {
    };
    private RestOperations restOperations;
    private String podcastsURL;


    public PodcastClient(String podcastsURL, RestOperations restOperations) {
        this.restOperations = restOperations;
        this.podcastsURL = podcastsURL;
    }

    public void create(PodcastUI podcast) {
        restOperations.postForEntity(podcastsURL, podcast, PodcastUI.class);
    }

    public List<PodcastUI> getAll() {
        return restOperations.exchange(podcastsURL, HttpMethod.GET, null, podcastListType).getBody();
    }

    public void delete(Long id) {
        String deleteURL = new StringBuilder(podcastsURL).append("/").append(id).toString();
        restOperations.delete(deleteURL);
    }

    public int count(String field, String key) {
        String URI = UriComponentsBuilder.fromHttpUrl(podcastsURL + "/count")
                .queryParam("field", field)
                .queryParam("key", key)
                .build().toUriString();
        return restOperations.getForEntity(URI, Integer.class).getBody();
    }

    public int countAll() {
        return restOperations.getForEntity(podcastsURL + "/count", Integer.class).getBody();
    }

    public List<PodcastUI> findAll(int offset, int size) {
        String URI = UriComponentsBuilder.fromUriString(podcastsURL)
                .queryParam("start", offset)
                .queryParam("pageSize", size)
                .toUriString();
        return restOperations.exchange(URI, HttpMethod.GET, null, podcastListType).getBody();
    }

    public List<PodcastUI> findRange(String field, String key, int offset, int size) {
        String URI = UriComponentsBuilder.fromUriString(podcastsURL)
                .queryParam("field", field)
                .queryParam("key", key)
                .queryParam("start", offset)
                .queryParam("pageSize", size)
                .toUriString();
        return restOperations.exchange(URI, HttpMethod.GET, null, podcastListType).getBody();

    }

}
