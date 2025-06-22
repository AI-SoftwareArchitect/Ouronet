package com.ouronet.ouronet.usecase;

import com.ouronet.ouronet.dto.UserDto;
import com.ouronet.ouronet.models.User;
import com.ouronet.ouronet.repositories.UserRepository;
import com.ouronet.ouronet.services.AdapterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminControllerUseCase {

    private final UserRepository userRepository;

    public AdminControllerUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserDto> getAllUsers(int page,int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(AdapterUser::toDto);
    }




}
