package com.ouronet.ouronet.services;

import com.ouronet.ouronet.dto.UserDto;
import com.ouronet.ouronet.models.User;

import java.util.List;

public final class AdapterUser {
    private AdapterUser() {}

    public static UserDto toDto(User user) {
        if (user == null) return null;

        UserDto dto = new UserDto();
        dto.setId(user.getId().toString());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setVipState(user.getVipState());
        return dto;
    }

    public static List<UserDto> toDto(List<User> users) {
        if (users == null) return List.of();

        return users.stream()
                .map(AdapterUser::toDto)
                .toList(); // Java 16+
    }
}

