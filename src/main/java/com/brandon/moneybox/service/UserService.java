package com.brandon.moneybox.service;

import com.brandon.moneybox.entity.User;
import com.brandon.moneybox.model.*;
import com.brandon.moneybox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Response<String> registerUser(User user) {
        try {
            if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
                return Response.<String>builder().error("Username and password must not be blank").build();
            }

            Optional<User> existingUser = userRepository.findById(user.getUsername());
            if (existingUser.isPresent()) {
                return Response.<String>builder().error("Username already exists").build();
            }

            userRepository.save(user);
            return Response.<String>builder().data("Berhasil Register").build();
        } catch (Exception e) {
            return Response.<String>builder().error(e.getMessage()).build();
        }
    }

    public Response<String> updateUser(String token, UpdateUserRequest updateUserRequest) {
        try {
            String username = validateToken(token);
            if (username != null) {
                Optional<User> optionalUser = userRepository.findById(username);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    if (updateUserRequest.getName() != null) {
                        user.setName(updateUserRequest.getName());
                    }
                    if (updateUserRequest.getPassword() != null) {
                        user.setPassword(updateUserRequest.getPassword());
                    }

                    userRepository.save(user);
                    return Response.<String>builder().data("User updated successfully").build();
                } else {
                    return Response.<String>builder().error("User not found").build();
                }
            } else {
                return Response.<String>builder().error("Unauthorized").build();
            }
        } catch (Exception e) {
            return Response.<String>builder().error(e.getMessage()).build();
        }
    }

    public Response<TokenResponse> loginUser(LoginUserRequest loginRequest) {
        try {
            Optional<User> optionalUser = userRepository.findById(loginRequest.getUsername());

            if (optionalUser.isPresent() && loginRequest.getPassword().equals(optionalUser.get().getPassword())) {
                // Generate and save a token
                String tokenValue = UUID.randomUUID().toString();

                User user = optionalUser.get();
                user.setToken(tokenValue);
                // Set token expiration time as needed
                user.setTokenExpiredAt(System.currentTimeMillis() + (24 * 60 * 60 * 1000)); // 1 day expiration

                userRepository.save(user);

                // Return the token to the client
                return Response.<TokenResponse>builder()
                        .data(TokenResponse.builder()
                                .token(tokenValue)
                                .tokenExpiredAt(user.getTokenExpiredAt())
                                .build())
                        .build();
            } else {
                return Response.<TokenResponse>builder().error("Username or password wrong").build();
            }
        } catch (Exception e) {
            return Response.<TokenResponse>builder().error(e.getMessage()).build();
        }
    }

    public Response<String> logoutUser(String token) {
        try {
            String username = validateToken(token);
            if (username != null) {
                Optional<User> optionalUser = userRepository.findById(username);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();

                    // Clear the token and token expiration time
                    user.setToken(null);
                    user.setTokenExpiredAt(null);

                    userRepository.save(user);

                    return Response.<String>builder().data("Logout successful").build();
                } else {
                    return Response.<String>builder().error("User not found").build();
                }
            } else {
                return Response.<String>builder().error("Unauthorized").build();
            }
        } catch (Exception e) {
            return Response.<String>builder().error(e.getMessage()).build();
        }
    }

    public Response<UserResponse> getUser(String username) {
        try {
            Optional<User> optionalUser = userRepository.findById(username);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                UserResponse userInfo = UserResponse.builder()
                        .username(user.getUsername())
                        .name(user.getName())
                        .build();

                return Response.<UserResponse>builder().data(userInfo).build();
            } else {
                return Response.<UserResponse>builder().error("User not found").build();
            }
        } catch (Exception e) {
            return Response.<UserResponse>builder().error(e.getMessage()).build();
        }
    }

    public String validateToken(String token) {
        try {
            Optional<User> optionalUser = userRepository.findByToken(token);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // Periksa apakah token belum kedaluwarsa
                if (user.getTokenExpiredAt() != null && System.currentTimeMillis() < user.getTokenExpiredAt()) {
                    return user.getUsername();  // Mengembalikan username yang terkait dengan token
                } else {
                    // Token kedaluwarsa, perlu melakukan logout atau proses sesuai kebutuhan
                    return null;
                }
            } else {
                // Token tidak valid atau tidak ditemukan
                return null;
            }
        } catch (Exception e) {
            // Tangani kesalahan yang mungkin terjadi saat validasi token
            return null;
        }
    }

}
