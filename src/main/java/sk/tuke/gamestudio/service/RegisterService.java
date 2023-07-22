package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.RegistrationLoginEntity;

import java.util.List;

public interface RegisterService {
    void addUser(RegistrationLoginEntity registrationLoginEntity);

    List<RegistrationLoginEntity> getUsers(String game);
}
