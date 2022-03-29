package ru.liga.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.liga.domain.User;
import ru.liga.dto.ApplicationDTO;
import ru.liga.dto.UserDTO;
import ru.liga.service.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }
//
//    @GetMapping("/{currentUser}/application")
//    public ApplicationDTO getApplicationForUser(@PathVariable User currentUser) {
//        return userService.getApplicationForUser(currentUser);
//    }
//
//    @GetMapping("/{currentUser}/likes")
//    public Set<ApplicationDTO> getLikesForUser(@PathVariable User currentUser) {
//        return userService.getLikesForUser(currentUser);
//    }
//
//    @GetMapping("/{currentUser}/likers")
//    public Set<ApplicationDTO> getLikersForUser(@PathVariable User currentUser) {
//        return userService.getLikersForUser(currentUser);
//    }
//
    @PostMapping
    @RequestMapping("/")
    public void addUser(@RequestBody User user) {
        userService.add(user);
        //userService.addUser(userDTO);
    }
//
//    @PutMapping
//    @RequestMapping("/{currentUser}/like/{user}")
//    public void likeUser(@PathVariable User currentUser,
//                         @PathVariable User user) {
//        userService.likeUser(user, currentUser);
//    }
//
//    @PutMapping
//    @RequestMapping("/{currentUser}/dislike/{user}")
//    public void dislikeUser(@PathVariable User currentUser,
//                            @PathVariable User user) {
//        userService.dislikeUser(user, currentUser);
//    }
//
//    @GetMapping
//    @RequestMapping("/{currentUser}/looking/")
//    public List<UserDTO> getUsersByLookingFor(@PathVariable User currentUser) {
//        return userService.getUsersByLookingFor(currentUser);
//    }
//
//    @GetMapping
//    @RequestMapping("/{currentUser}/looking/applications")
//    public List<ApplicationDTO> getApplicationsByLookingFor(@PathVariable User currentUser) {
//        return userService.getApplicationsByLookingFor(currentUser);
//    }
}
