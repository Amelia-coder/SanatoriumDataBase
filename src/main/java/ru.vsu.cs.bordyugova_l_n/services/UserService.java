//package ru.vsu.cs.bordyugova_l_n.services;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    public User registerUser(User user) {
//        // Check if user already exists
//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("User with this email already exists.");
//        }
//        // Encrypt the password and save the user
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }
//}
