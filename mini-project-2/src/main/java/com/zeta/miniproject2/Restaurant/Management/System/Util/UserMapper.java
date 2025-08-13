package com.zeta.miniproject2.Restaurant.Management.System.Util;

import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.UserDTO;
import com.zeta.miniproject2.Restaurant.Management.System.Model.Entities.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .userId(userDTO.getUserId())
                .name(userDTO.getName())
                .role(userDTO.getRole())
                .build();
    }
}
