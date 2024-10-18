package com.nezdanchik.pp3_1_5;

import com.nezdanchik.pp3_1_5.controller.SimpleController;
import com.nezdanchik.pp3_1_5.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final SimpleController simpleController;

    @Autowired
    public Application(SimpleController simpleController) {
        this.simpleController = simpleController;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<User> allUsers = simpleController.getAllUsers();
        System.out.println(allUsers);
        User userForSave = new User(3L, "James", "Brown", (byte) 1);
        simpleController.saveUser(userForSave);
        simpleController.updateUserToThomasShelban(userForSave);
        simpleController.deleteUser(userForSave.getId());
    }

}
