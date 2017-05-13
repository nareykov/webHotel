package servlets;
import org.apache.log4j.Logger;

import java.io.File;
import java.sql.*;

/**
 * Класс с методамидля работы с базой данных
 */
public class DataBase {
    private Connection c = null;

    private Statement stmt = null;

    private static final Logger log = Logger.getLogger(DataBase.class);

    public DataBase() {

    }

    /** Устанавливает соединение с базой данных.
     * Перед подключением к базе данных производим проверку на её существование.
     * В зависимости от результата производим открытие базы данных или её восстановление
     */
    public void connectToDataBase() {
        if(!new File("C:\\Users\\narey\\Documents\\IdeaProjects\\webHotel\\database.db").exists()){
            if (!this.restoreDataBase()) {
                log.error("Tables not created");
            }
        } else {
            this.openDataBase();
        }
    }

    /**
     * Востановление базы данных.
     * Создается файл базы данных и таблицы.
     * @return false - файл или таблица не создались, true - успех)
     */
    private boolean restoreDataBase() {
        if (this.openDataBase()) {
            if (!this.createUsers() || !this.createRooms() || !this.createRecords()) {
                return false;
            } else {
                return true;
            }
        } else {
            log.error("Restore database failed");
            return false;
        }
    }

    /**
     * Открытие базы данных или, создание и открытие.
     * @return false - возникло исключение при создании файла БД, true - в случае успеха
     */
    private boolean openDataBase() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\narey\\Documents\\IdeaProjects\\webHotel\\database.db");
        } catch ( Exception e ) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        log.info("Opened database successfully");
        return true;
    }

    /**
     * Создание таблицы пользователей.
     * @return true - таблица успешно создана, false - исключение
     */
    private boolean createUsers() {
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE Users " +
                    "(Username      TEXT           NOT NULL," +
                    " Pass          TEXT           NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        log.info("Table Users created successfully");
        return true;
    }

    /**
     * Создание таблицы файлов.
     * @return true - таблица успешно создана, false - исключение
     */
    private boolean createRooms() {
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE Rooms " +
                    "(Number   TEXT                NOT NULL," +
                    " Size     TEXT                NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        log.info("Table FileBase created successfully");
        return true;
    }

    private boolean createRecords() {
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE Records " +
                    "(Number   TEXT                NOT NULL," +
                    " User     TEXT                NOT NULL," +
                    " DateFrom TEXT                NOT NULL," +
                    " DateTo   TEXT                NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
        log.info("Table FileBase created successfully");
        return true;
    }


    /**
     * Закрывает базу данных.
     */
    public void closeDataBase() {
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.getClass().getName() + ": " + e.getMessage());
            System.exit(e.getErrorCode());
        }
        log.info("Database closed successfully");
    }

    /**
     * Записывает в базу данных данные о новом юзере
     * @param username имя юзера
     * @param pass пароль
     */
    public void insertIntoUsers(String username, String pass) {
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO Users (Username, Pass) " +
                    "VALUES ('" + username + "', '" +  pass + "');";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error(e.getClass().getName() + ": " + e.getMessage());
        }
        log.info("Recorded into Users successfully");
    }

    /**
     * Записывает новую комнату в базу данных
     * @param number номер комнаты
     * @param size размер
     */
    public void insertIntoRooms(String number, String size) {
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO Rooms (Number, Size) " +
                    "VALUES ('" + number + "', '" + size + "');";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        log.info("Recorded into Rooms successfully");
    }

    public void insertIntoRecords(int number, String user, Date from, Date to) {
        try {
            stmt = c.createStatement();
            String sql = "INSERT INTO Records (Number, User, DateFrom, DateTo) " +
                    "VALUES ('" + Integer.toString(number) + "', '" + user + "', '"
                    + from.toString() + "', '" + to.toString() + "');";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            e.printStackTrace();
            log.error(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        log.info("Recorded into Records successfully");
    }

    /**
     * Проверка логина и пароля пользователя при нажатии на Enter в окне входа.
     * @param username Мыло пользователя
     * @param pass Пароль
     * @return true - верный логин и пароль, false - неверные логин или пароль
     */
    public boolean enter(String username, String pass) {
        log.info(username + " enter");
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Users WHERE Username = '" + username + "';" );
            if ((pass).equals(rs.getString("Pass"))) {
                rs.close();
                stmt.close();
                return true;
            }
        } catch ( SQLException e ) {
            log.error(e.toString());
            return false;
        }
        return false;
    }

    public int getFreeNumber(String size, Date from, Date to) {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Rooms WHERE Size = '" + size + "';");
            while (rs.next())
            {
                String number = rs.getString("Number");
                if (!isRoomReserved(number, from, to)) {
                    return Integer.parseInt(number);
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private boolean isRoomReserved(String number, Date from, Date to) {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Records WHERE Number = '" + number + "';");
            while (rs.next())
            {
                Date reservedFrom = Date.valueOf(rs.getString("DateFrom"));
                Date reservedTo = Date.valueOf(rs.getString("DateTo"));
                if (!(from.getTime() > reservedTo.getTime() || to.getTime() < reservedFrom.getTime())) {
                    return true;
                }
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Проверяет был ли зарегистрирован пользователь ранее.
     * @return Если уже зарегистрирован - true, если нет - false.
     */
    public boolean isRegistered(String username) {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Users WHERE Username = '" + username + "';" );
            if (rs.getString("Username") != null) {
                rs.close();
                stmt.close();
                return true;
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            //log.error(e.toString());
            return false;
        }
        return false;
    }

    public ResultSet getUserRecords(String user) {
        try {
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM Records WHERE User = '" + user + "';" );
            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
    }

    public ResultSet getRecords() {
        try {
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM Records;" );
            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
    }

    public ResultSet getRooms() {
        try {
            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM Rooms;" );
            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(e.toString());
            return null;
        }
    }
}
