package ru.liga;

import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import ru.liga.domain.SexType;
import ru.liga.dto.ApplicationDTO;
import ru.liga.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class Stub {

    private static List<UserDTO> initialUsers = new ArrayList<>();

    private static void buildUsers() {
        //ApplicationDTO applicationDTO = new ApplicationDTO("Войдуля", "проверочный дексрипшен", SexType.MALE, SexType.FEMALE);
//        UserDTO userDTO = new UserDTO("voidlol", "afohsdklfhsdkg", applicationDTO, null, null);
//        initialUsers.add(userDTO);
    }

    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println(encoder.encode("testpassword2"));
        System.out.println("$2a$10$H9GuLRXY9EaqeH95r7Z3Wu4WPfgwAJM7bTmxmU4L5p4MuAAXiszqm");
    }
}
