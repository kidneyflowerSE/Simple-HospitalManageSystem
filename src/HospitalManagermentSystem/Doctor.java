package HospitalManagermentSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SuppressWarnings("ALL")
public class Doctor {
    private final Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    public void viewDoctors() {
        String query = "SELECT * from doctors";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors:");
            System.out.println("+------------+--------------------+---------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization      |");
            System.out.println("+------------+--------------------+---------------------+");


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");

                System.out.printf("| %-10d | %-18s | %-20s |\n", id, name, specialization);
            }
            System.out.println("+------------+--------------------+----------------------+");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id){
        String query = "select * from doctors where id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
