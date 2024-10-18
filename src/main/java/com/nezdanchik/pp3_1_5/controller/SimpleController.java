package com.nezdanchik.pp3_1_5.controller;

import com.nezdanchik.pp3_1_5.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("app")
public class SimpleController {

    private final RestTemplate restTemplate;
    private final String URL = "http://94.198.50.185:7081/api/users";
    private String sessionId;

    @Autowired
    public SimpleController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                }
        );


        List<String> cookies = responseEntity.getHeaders().get("Set-Cookie");
        String rawCookie = cookies.get(0);
        sessionId = rawCookie.split(";")[0];
        System.out.println("Session ID: " + sessionId);

        List<User> allUsers = responseEntity.getBody();
        return allUsers;
    }

    public void saveUser(User userForSave) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId); // Изменено с "Set-Cookie" на "Cookie"
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> entity = new HttpEntity<>(userForSave, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                entity,
                String.class
        );
        System.out.println("Response: " + response.getBody());
    }


    public void updateUserToThomasShelban(User userForUpdate) {
        // Обновляем поля пользователя
        userForUpdate.setName("Thomas");
        userForUpdate.setLastName("Shelby");
        userForUpdate.setAge((byte) 2);

        // Устанавливаем заголовки
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создаем HTTP-entity с обновленным пользователем
        HttpEntity<User> entity = new HttpEntity<>(userForUpdate, headers);

        // Формируем URL с ID пользователя


        // Выполняем запрос PUT
        ResponseEntity<String> response = restTemplate.exchange(
                URL,
                HttpMethod.PUT,
                entity,
                String.class
        );

        // Выводим тело ответа
        System.out.println("Response body: " + response.getBody());
    }


    public void deleteUser(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        String deleteUrl = URL + "/" + userId;

        ResponseEntity<String> response = restTemplate.exchange(
                deleteUrl,
                HttpMethod.DELETE,
                entity,
                String.class
        );

        System.out.println("Delete Response: " + response.getBody());
    }

}
