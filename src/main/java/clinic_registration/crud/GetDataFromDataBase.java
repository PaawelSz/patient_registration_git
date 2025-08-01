package clinic_registration.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GetDataFromDataBase {

    public static void main(String[] args) {
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.printf("Failed to load connector: %s%n", e.getMessage());
        }

        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/clinic_registration", "root", "Paletka123!")) {

            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM clinic_registration.patient");
                while (resultSet.next()) {
                    System.out.printf("Patient information: [%s, %s, %s, %s]%n",
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4));
                }
            }
        } catch(Exception e) {
            System.out.printf("Failed to load: %s%n", e.getMessage());
        }
    }
}
