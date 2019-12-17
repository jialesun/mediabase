package com.example.mediabase.movies;

import com.example.mediabase.moviesui.MovieUI;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import java.util.List;

@Repository
public class MoviesBean {

    @PersistenceContext
    private EntityManager entityManager;

    public MovieUI find(Long id) {
        return entityManager.find(MovieUI.class, id);
    }

    @Transactional
    public void addMovie(MovieUI movieUI) {
        entityManager.persist(movieUI);
    }

    @Transactional
    public void editMovie(MovieUI movieUI) {
        entityManager.merge(movieUI);
    }

    @Transactional
    public void deleteMovie(MovieUI movieUI) {
        entityManager.remove(movieUI);
    }

    @Transactional
    public void deleteMovieId(long id) {
        MovieUI movieUI = entityManager.find(MovieUI.class, id);
        deleteMovie(movieUI);
    }

    public List<MovieUI> getMovies() {
        CriteriaQuery<MovieUI> cq = entityManager.getCriteriaBuilder().createQuery(MovieUI.class);
        cq.select(cq.from(MovieUI.class));
        return entityManager.createQuery(cq).getResultList();
    }

    public List<MovieUI> findAll(int firstResult, int maxResults) {
        CriteriaQuery<MovieUI> cq = entityManager.getCriteriaBuilder().createQuery(MovieUI.class);
        cq.select(cq.from(MovieUI.class));
        TypedQuery<MovieUI> q = entityManager.createQuery(cq);
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
        return q.getResultList();
    }

    public int countAll() {
        CriteriaQuery<Long> cq = entityManager.getCriteriaBuilder().createQuery(Long.class);
        Root<MovieUI> rt = cq.from(MovieUI.class);
        cq.select(entityManager.getCriteriaBuilder().count(rt));
        TypedQuery<Long> q = entityManager.createQuery(cq);
        return (q.getSingleResult()).intValue();
    }

    public int count(String field, String searchTerm) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        Root<MovieUI> root = cq.from(MovieUI.class);
        EntityType<MovieUI> type = entityManager.getMetamodel().entity(MovieUI.class);

        Path<String> path = root.get(type.getDeclaredSingularAttribute(field, String.class));
        Predicate condition = qb.like(path, "%" + searchTerm + "%");

        cq.select(qb.count(root));
        cq.where(condition);

        return entityManager.createQuery(cq).getSingleResult().intValue();
    }

    public List<MovieUI> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MovieUI> cq = qb.createQuery(MovieUI.class);
        Root<MovieUI> root = cq.from(MovieUI.class);
        EntityType<MovieUI> type = entityManager.getMetamodel().entity(MovieUI.class);

        Path<String> path = root.get(type.getDeclaredSingularAttribute(field, String.class));
        Predicate condition = qb.like(path, "%" + searchTerm + "%");

        cq.where(condition);
        TypedQuery<MovieUI> q = entityManager.createQuery(cq);
        q.setMaxResults(maxResults);
        q.setFirstResult(firstResult);
        return q.getResultList();
    }

    @Transactional
    public void clean() {
        entityManager.createQuery("delete from Movie").executeUpdate();
    }
}