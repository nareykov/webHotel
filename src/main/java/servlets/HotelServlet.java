package servlets;

import classes.ReserveRecord;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

                    ArrayList<ReserveRecord> records = makeReserveRecordsList(db.getUserRecords(username));
                    req.setAttribute("records", records);

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
            //Заброировать номер
            getServletContext().getRequestDispatcher("/reserve.jsp").forward(req, resp);
        } else if (answer == 4) {
            //Выйти
            session.invalidate();
            req.setAttribute("err", "");
            getServletContext().getRequestDispatcher("/enter.jsp").forward(req, resp);
        } else if (answer == 5) {
            //Подтверждение брони
            db.connectToDataBase();
            Date from = Date.valueOf(req.getParameter("from"));
            Date to = Date.valueOf(req.getParameter("to"));
            String size = req.getParameter("size");
            String currUser = (String) session.getAttribute("user");
            db.insertIntoRecords(2, currUser, from, to);
            ArrayList<ReserveRecord> records = makeReserveRecordsList(db.getUserRecords(String.valueOf(session.getAttribute("user"))));
            db.closeDataBase();
            req.setAttribute("records", records);
            getServletContext().getRequestDispatcher("/home.jsp").forward(req, resp);
        } else{
            if (session.getAttribute("user") != null) {
                db.connectToDataBase();
                ArrayList<ReserveRecord> records = makeReserveRecordsList(db.getUserRecords(String.valueOf(session.getAttribute("user"))));
                db.closeDataBase();
                req.setAttribute("records", records);
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
        if (req.getParameter("reserveButton") != null) {
            return 5;
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

    private ArrayList<ReserveRecord> makeReserveRecordsList(ResultSet rs) {
        ArrayList<ReserveRecord> records = new ArrayList<ReserveRecord>();
        try {
            while (rs.next())
            {
                ReserveRecord record = new ReserveRecord();
                record.setNumber(rs.getString("number"));
                record.setDateFrom(rs.getString("DateFrom"));
                record.setDateTo(rs.getString("DateTo"));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}
