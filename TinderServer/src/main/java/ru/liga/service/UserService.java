package ru.liga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.liga.domain.User;
import ru.liga.repo.UserRepository;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
    }

//
//    public void likeUser(User target, User source) {
//        target.getWhoLikedMe().add(source);
//        userRepository.save(target);
//    }
//
//    public void dislikeUser(User target, User source) {
//        target.getWhoLikedMe().remove(source);
//        userRepository.save(target);
//    }
//
    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }
//
//    public void addUser(UserDTO userDTO) {
//        User user = User.getUserFromUserDTO(userDTO);
//        userRepository.save(user);
//    }
//
//    public Set<ApplicationDTO> getLikesForUser(User user) {
//        UserDTO dtoFromUser = UserDTO.getDTOFromUser(user);
//        return dtoFromUser.getWhomILiked();
//    }
//
//    public List<UserDTO> getUsersByLookingFor(User currentUser) {
//        List<User> users;
//        SexType userSex = currentUser.getApplicationId().getSexType();
//        SexType lookingFor = currentUser.getApplicationId().getLookingFor();
//        if (lookingFor == SexType.BOTH) {
//            users = userRepository.findUsersByApplicationId_SexTypeAndApplicationId_LookingFor(SexType.MALE, userSex);
//            users.addAll(userRepository.findUsersByApplicationId_SexTypeAndApplicationId_LookingFor(SexType.MALE, SexType.BOTH));
//            users.addAll(userRepository.findUsersByApplicationId_SexTypeAndApplicationId_LookingFor(SexType.FEMALE, userSex));
//            users.addAll(userRepository.findUsersByApplicationId_SexTypeAndApplicationId_LookingFor(SexType.FEMALE, SexType.BOTH));
//        } else {
//            users = userRepository.findUsersByApplicationId_SexTypeAndApplicationId_LookingFor(lookingFor, userSex);
//            users.addAll(userRepository.findUsersByApplicationId_SexTypeAndApplicationId_LookingFor(lookingFor, SexType.BOTH));
//        }
//        users.remove(currentUser);
//        return users.stream()
//                .map(UserDTO::getDTOFromUser)
//                .collect(Collectors.toList());
//    }
//
//    public Set<ApplicationDTO> getLikersForUser(User currentUser) {
//        UserDTO dtoFromUser = UserDTO.getDTOFromUser(currentUser);
//        return dtoFromUser.getWhoLikedMe();
//    }
//
//    public ApplicationDTO getApplicationForUser(User currentUser) {
//        return ApplicationDTO.getApplicationDTOFromApplication(currentUser.getApplicationId());
//    }
//
//    public List<ApplicationDTO> getApplicationsByLookingFor(User currentUser) {
//        return getUsersByLookingFor(currentUser).stream()
//                .map(UserDTO::getApplicationDTO)
//                .collect(Collectors.toList());
//    }
}
