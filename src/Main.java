import java.sql.*;
import javax.sql.*;

public class Main {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";
    public static final String DB_Driver = "com.mysql.cj.jdbc.Driver";

    public static boolean isDeleteCategory = false;
    public static boolean isUpdateNameDescription = true;

    public static void main(String[] args) {
        Connection conn = null;

        try
        {
            Class.forName (DB_Driver);
            conn = DriverManager.getConnection (DB_URL,"root","12345678");
            System.out.println ("ВКЛ\n");

            Statement statement = conn.createStatement();
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 0;");
            String sql;

            // Удаление всех значений в таблице
            sql = String.format("DELETE FROM product WHERE id_product > 0");
            statement.executeUpdate(sql);

            // Добавление значений в таблицу
            sql = String.format("INSERT INTO product (id_product, id_category, p_name, p_description, p_cost) VALUES (1, 1, 'Название товара', 'Очень длинное описание товара', 14.88)");
            statement.executeUpdate(sql);
            sql = String.format("INSERT INTO product (id_product, id_category, p_name, p_description, p_cost) VALUES (2, 2, 'Название второго товара', 'Очень длинное описание второго товара', 34.99)");
            statement.executeUpdate(sql);
            sql = String.format("INSERT INTO product (id_product, id_category, p_name, p_description, p_cost) VALUES (3, 1, 'Название третьего товара', 'Очень длинное описание третьего товара', 19.99)");
            statement.executeUpdate(sql);

            // Удаление информации о продуктах со второй категорией
            if(isDeleteCategory) {
                sql = String.format("DELETE FROM product WHERE id_category = 2");
                statement.executeUpdate(sql);
            }

            // Изменение навзвания и описания товара с id = 1
            if(isUpdateNameDescription) {
                sql = String.format("UPDATE product SET p_name = 'Название первого товара', p_description = 'Очень длинное описание первого товара' WHERE id_product = 1");
                statement.executeUpdate(sql);
            }

            // Вывод данных
            ResultSet resultSet=statement.executeQuery("SELECT * FROM product");
            System.out.println("+------------+-------------+--------------------------+---------------------------------------------+---------+");
            System.out.println("| id_product | id_category |          p_name          +                p_description                +  p_cost +");
            System.out.println("+------------+-------------+--------------------------+---------------------------------------------+---------+");
            while(resultSet.next()){
                System.out.printf("|%12d|%13d|%26s|%45s|%9.2f|\n",
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getFloat(5));
            }
            System.out.println("+------------+-------------+--------------------------+---------------------------------------------+---------+");

            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = 1;");
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close ();
                    System.out.println ("\nОТКЛ");
                }
                catch (Exception e) {}
            }
        }
    }
}