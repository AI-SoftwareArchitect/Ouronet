package com.ouronet.ouronet.repositories;

import com.ouronet.ouronet.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, UserCustomRepository {
    User findByName(String name);
    User findByEmail(String email);
    List<User> findByVipState(String vipState);
    Page<User> findAll(Pageable pageable);
}