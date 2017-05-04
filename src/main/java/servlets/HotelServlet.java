package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by narey on 01.05.2017.
 */
@WebServlet("/hotel")
public class HotelServlet extends HttpServlet {

    DataBase db = new DataBase();

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Установка кодировки для принятия параметров
        req.setCharacterEncoding("UTF-8");
        int answer = 0;
        HttpSession session = req.getSession();

        answer = checkAction(req);

        System.out.println(answer);

        if (answer == 1) {
            //Регистрация пользователя
            req.setAttribute("err", "");
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        } else if (answer == 2) {
            //Вход пользователя
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            if (username.equals("admin") && password.equals("admin")) {
                req.setAttribute("err", "admin");
                getServletContext().getRequestDispatcher("/enter.jsp").forward(req, resp);
            } else if (!(username.equals("") || password.equals(""))) {
                db.connectToDataBase();
                if (db.enter(username, password)) {
                    session.setAttribute("user", username);
                    getServletContext().getRequestDispatcher("/home.jsp").forward(req, resp);
                } else {
                    req.setAttribute("err", "Incorrect login or password");
                    getServletContext().getRequestDispatcher("/enter.jsp").forward(req, resp);
                }
                db.closeDataBase();
            } else {
                req.setAttribute("err", "Empty field");
                getServletContext().getRequestDispatcher("/enter.jsp").forward(req, resp);
            }

        } else if (answer == 3) {
            getServletContext().getRequestDispatcher("/reserve.jsp").forward(req, resp);
        } else if (answer == 4) {
            session.invalidate();
            getServletContext().getRequestDispatcher("/enter.jsp").forward(req, resp);
        } else{
            if (session.getAttribute("user") != null) {
                getServletContext().getRequestDispatcher("/home.jsp").forward(req, resp);
            }
            req.setAttribute("err", "");
            getServletContext().getRequestDispatcher("/enter.jsp").forward(req, resp);
        }
    }

    // Здесь мы проверям какое действие нам надо сделать – и возвращаем ответ
    private int checkAction(HttpServletRequest req) {

        if (req.getParameter("regButton") != null) {
            return 1;
        }
        if (req.getParameter("entButton") != null) {
            return 2;
        }
        if (req.getParameter("reserveJsp") != null) {
            return 3;
        }
        if (req.getParameter("exitButton") != null) {
            return 4;
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
