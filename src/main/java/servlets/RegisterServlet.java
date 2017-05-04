package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * Created by narey on 01.05.2017.
 */

@WebServlet("/register")
public class RegisterServlet  extends HttpServlet {

    DataBase db = new DataBase();

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Установка кодировки для принятия параметров
        req.setCharacterEncoding("UTF-8");
        int answer = 0;

        answer = checkAction(req);

        System.out.println(answer);

        if (answer == 1) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String confirm = req.getParameter("confirm");
            if (!(username.equals("") || password.equals("") || confirm.equals(""))) {
                if (password.equals(confirm)) {
                    db.connectToDataBase();
                    if (!db.isRegistered(username)) {
                        db.insertIntoUsers(username, password);
                    } else {
                        req.setAttribute("err", "Такое имя уже зарегистрировано");
                        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
                    }
                    db.closeDataBase();

                } else {
                    req.setAttribute("err", "Пароль не подтвержден");
                    getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("err", "Заполните все поля");
                getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
            }
            getServletContext().getRequestDispatcher("/enter.jsp").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }

    }

    // Здесь мы проверям какое действие нам надо сделать – и возвращаем ответ
    private int checkAction(HttpServletRequest req) {
        if (req.getParameter("regButton") != null) {
            return 1;
        }
        return 0;
    }

    // Переопределим стандартные методы
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
