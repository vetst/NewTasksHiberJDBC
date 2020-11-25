package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        User user = new User();
        try {
            List users;
            userService.createUsersTable();
            user.setName("Олег");
            userService.saveUser(user.getName(), "Петров", (byte) 23);
            System.out.println("User с именем " + user.getName() + " добавлен в базу");
            user.setName("Иван");
            userService.saveUser(user.getName(), "Иванов", (byte) 24);
            System.out.println("User с именем " + user.getName() + " добавлен в базу");
            user.setName("Сергей");
            userService.saveUser(user.getName(), "Сергеев", (byte) 25);
            System.out.println("User с именем " + user.getName() + " добавлен в базу");
            user.setName("Игорь");
            userService.saveUser(user.getName(), "Игорев", (byte) 26);
            System.out.println("User с именем " + user.getName() + " добавлен в базу");
            //Удалить пользователя с id 3
            userService.removeUserById(3);
            users = userService.getAllUsers();
            User dbUser;
            for (Object obj : users) {
                dbUser = (User) obj;
                System.out.println(dbUser.toString());
            }
//          userService.cleanUsersTable();
//          userService.dropUsersTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
