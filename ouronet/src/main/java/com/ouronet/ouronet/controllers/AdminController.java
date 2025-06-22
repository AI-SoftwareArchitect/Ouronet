package com.ouronet.ouronet.controllers;


import com.ouronet.ouronet.dto.UserDto;
import com.ouronet.ouronet.usecase.AdminControllerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminControllerUseCase adminControllerUseCase;

    public AdminController(AdminControllerUseCase adminControllerUseCase) {
        this.adminControllerUseCase = adminControllerUseCase;
    }

    @GetMapping
    public Page<UserDto> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return adminControllerUseCase.getAllUsers(page,size);
    }


}
