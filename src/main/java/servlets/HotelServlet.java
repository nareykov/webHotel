package servlets;

import classes.Record;
import classes.ReserveRecord;
import classes.Room;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Сервлет обрабатывающий все действия пользователя
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
            if (session.getAttribute("language") == null || session.getAttribute("language").equals("RU")) {
                getServletContext().getRequestDispatcher("/registerRU.jsp").forward(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/registerEN.jsp").forward(req, resp);
            }
            //getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        } else if (answer == 2) {
            //Вход пользователя
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            if (username.equals("admin") && password.equals("admin")) {
                db.connectToDataBase();
                ArrayList<Record> records = makeRecordsList(db.getRecords());
                ArrayList<Room> rooms = makeRoomsList(db.getRooms());
                db.closeDataBase();
                req.setAttribute("records", records);
                req.setAttribute("rooms", rooms);
                if (session.getAttribute("language") == null || session.getAttribute("language").equals("RU")) {
                    getServletContext().getRequestDispatcher("/adminRU.jsp").forward(req, resp);
                } else {
                    getServletContext().getRequestDispatcher("/adminEN.jsp").forward(req, resp);
                }
                //getServletContext().getRequestDispatcher("/admin.jsp").forward(req, resp);
            } else if (!(username.equals("") || password.equals(""))) {
                db.connectToDataBase();
                if (db.enter(username, password)) {
                    session.setAttribute("user", username);

                    ArrayList<ReserveRecord> records = makeReserveRecordsList(db.getUserRecords(username));
                    req.setAttribute("records", records);

                    if (session.getAttribute("language") == null || session.getAttribute("language").equals("RU")) {
                        getServletContext().getRequestDispatcher("/homeRU.jsp").forward(req, resp);
                    } else {
                        getServletContext().getRequestDispatcher("/homeEN.jsp").forward(req, resp);
                    }
                    //getServletContext().getRequestDispatcher("/home.jsp").forward(req, resp);
                } else {
                    req.setAttribute("err", "Incorrect login or password");
                    if (session.getAttribute("language") == null || session.getAttribute("language").equals("RU")) {
                        getServletContext().getRequestDispatcher("/enterRU.jsp").forward(req, resp);
                    } else {
                        getServletContext().getRequestDispatcher("/enterEN.jsp").forward(req, resp);
                    }
                    //getServletContext().getRequestDispatcher("/enter.jsp").forward(req, resp);
                }
                db.closeDataBase();
            } else {
                req.setAttribute("err", "Empty field");
                if (session.getAttribute("language") == null || session.getAttribute("language").equals("RU")) {
                    getServletContext().getRequestDispatcher("/enterRU.jsp").forward(req, resp);
                } else {
                    getServletContext().getRequestDispatcher("/enterEN.jsp").forward(req, resp);
                }
                //getServletContext().getRequestDispatcher("/enter.jsp").forward(req, resp);
            }

        } else if (answer == 3) {
            //Заброировать номер
            if (session.getAttribute("language") == null || session.getAttribute("language").equals("RU")) {
                getServletContext().getRequestDispatcher("/reserveRU.jsp").forward(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/reserveEN.jsp").forward(req, resp);
            }
            //getServletContext().getRequestDispatcher("/reserve.jsp").forward(req, resp);
        } else if (answer == 4) {
            //Выйти
            session.invalidate();
            req.setAttribute("err", "");
            getServletContext().getRequestDispatcher("/enterRU.jsp").forward(req, resp);
            //getServletContext().getRequestDispatcher("/enter.jsp").forward(req, resp);
        } else if (answer == 5) {
            //Подтверждение брони
            db.connectToDataBase();
            Date from = Date.valueOf(req.getParameter("from"));
            Date to = Date.valueOf(req.getParameter("to"));
            String size = req.getParameter("size");
            String currUser = (String) session.getAttribute("user");
            int number = db.getFreeNumber(size, from, to);
            if (number >= 0) {
                db.insertIntoRecords(number, currUser, from, to);
            } else {
                System.out.println("No FRee");
            }
            ArrayList<ReserveRecord> records = makeReserveRecordsList(db.getUserRecords(String.valueOf(session.getAttribute("user"))));
            db.closeDataBase();
            req.setAttribute("records", records);
            if (session.getAttribute("language") == null || session.getAttribute("language").equals("RU")) {
                getServletContext().getRequestDispatcher("/homeRU.jsp").forward(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/homeEN.jsp").forward(req, resp);
            }
            //getServletContext().getRequestDispatcher("/home.jsp").forward(req, resp);
        } else if (answer == 6) {
            //Добавление нового номера
            String number = req.getParameter("number");
            String size = req.getParameter("size");
            if (!(number.equals("") || number == null)) {
                db.connectToDataBase();
                db.insertIntoRooms(number, size);
                db.closeDataBase();
            } else {
                System.out.println("Empty size");
            }
            db.connectToDataBase();
            ArrayList<Record> records = makeRecordsList(db.getRecords());
            ArrayList<Room> rooms = makeRoomsList(db.getRooms());
            db.closeDataBase();
            req.setAttribute("records", records);
            req.setAttribute("rooms", rooms);
            if (session.getAttribute("language") == null || session.getAttribute("language").equals("RU")) {
                getServletContext().getRequestDispatcher("/adminRU.jsp").forward(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/adminEN.jsp").forward(req, resp);
            }
            //getServletContext().getRequestDispatcher("/admin.jsp").forward(req, resp);
        } else if (answer == 7) {
            //Изменение языка
            session.setAttribute("language", req.getParameter("lang"));
            req.setAttribute("err", "");
            if (session.getAttribute("language") == null || session.getAttribute("language").equals("RU")) {
                getServletContext().getRequestDispatcher("/enterRU.jsp").forward(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/enterEN.jsp").forward(req, resp);
            }
        } else{
            if (session.getAttribute("user") != null) {
                db.connectToDataBase();
                ArrayList<ReserveRecord> records = makeReserveRecordsList(db.getUserRecords(String.valueOf(session.getAttribute("user"))));
                db.closeDataBase();
                req.setAttribute("records", records);
                if (session.getAttribute("language") == null || session.getAttribute("language").equals("RU")) {
                    getServletContext().getRequestDispatcher("/homeRU.jsp").forward(req, resp);
                } else {
                    getServletContext().getRequestDispatcher("/homeEN.jsp").forward(req, resp);
                }
                //getServletContext().getRequestDispatcher("/home.jsp").forward(req, resp);
            }
            req.setAttribute("err", "");
            if (session.getAttribute("language") == null || session.getAttribute("language").equals("RU")) {
                getServletContext().getRequestDispatcher("/enterRU.jsp").forward(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/enterEN.jsp").forward(req, resp);
            }
            //getServletContext().getRequestDispatcher("/enter.jsp").forward(req, resp);
        }
    }

    /**
     * Определяем тип действия
     * @param req запрос
     * @return тип
     */
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
        if (req.getParameter("newRoomButton") != null) {
            return 6;
        }
        if (req.getParameter("langButton") != null) {
            return 7;
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

    /**
     * Формирует список ReserveRecord
     * @param rs result set
     * @return список
     */
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

    /**
     * Формирует список Record
     * @param rs result set
     * @return список
     */
    private ArrayList<Record> makeRecordsList(ResultSet rs) {
        ArrayList<Record> records = new ArrayList<Record>();
        try {
            while (rs.next())
            {
                Record record = new Record();
                record.setNumber(rs.getString("Number"));
                record.setUser(rs.getString("User"));
                record.setFrom(rs.getString("DateFrom"));
                record.setTo(rs.getString("DateTo"));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    private ArrayList<Room> makeRoomsList(ResultSet rs) {
        ArrayList<Room> rooms = new ArrayList<Room>();
        try {
            while (rs.next())
            {
                Room room = new Room();
                room.setNumber(rs.getString("Number"));
                room.setSize(rs.getString("Size"));
                rooms.add(room);
                System.out.println(room.getNumber() + room.getSize());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }
}
