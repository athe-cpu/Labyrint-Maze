package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.RatingAndComments;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingAndCommentsJPA implements RatingAndCommentsService {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addRC(RatingAndComments ratingAndComments) {
        entityManager.persist(ratingAndComments);
    }
    @Override
    public List<RatingAndComments> getTopScores(String game) {
        return (List<RatingAndComments>) entityManager.createQuery("select s from RatingAndComments s where s.game = :maze")
                .setParameter("maze",game)
                .setMaxResults(15)
                .getResultList();
    }
    @Override
    public int getAverage(String game) {
        List<Double> rating =  entityManager.createQuery("select AVG(s.rate) as rate from RatingAndComments s  where s.game = :maze")
                .setParameter("maze",game)
               .getResultList();
        if(rating.get(0)!=null) {
            return (int) (double) Math.round(rating.get(0));
        }
        return 0;
    }
    @Override
    public void updateByName(int id ) {
       RatingAndComments g = entityManager.find(RatingAndComments.class,id);
       entityManager.remove(g);
    }
    @Override
    public void reset() {
        entityManager.createNativeQuery("DELETE from rating_and_comments").executeUpdate();
    }
}
