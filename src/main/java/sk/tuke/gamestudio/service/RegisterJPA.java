package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.RegistrationLoginEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RegisterJPA implements RegisterService{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(RegistrationLoginEntity registrationLoginEntity) {
        entityManager.persist(registrationLoginEntity);
    }

    @Override
    public List<RegistrationLoginEntity> getUsers(String game) {
        return (List<RegistrationLoginEntity>) entityManager.createQuery("select s from RegistrationLoginEntity s where s.game = :maze")
                .setParameter("maze",game)
                .getResultList();
    }
}
