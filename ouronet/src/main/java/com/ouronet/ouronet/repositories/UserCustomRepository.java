package com.ouronet.ouronet.repositories;
import com.ouronet.ouronet.models.User;

import java.util.List;

public interface UserCustomRepository {
    void updateUserById(User user);
    void updateListOfUsers(List<User> users);
}
