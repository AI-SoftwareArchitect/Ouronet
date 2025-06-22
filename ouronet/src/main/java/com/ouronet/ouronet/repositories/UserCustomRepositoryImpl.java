package com.ouronet.ouronet.repositories;

import com.ouronet.ouronet.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

public class UserCustomRepositoryImpl implements UserCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void updateUserById(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void updateListOfUsers(List<User> users) {
        for (User user : users) {
            entityManager.merge(user);
        }
    }

}
