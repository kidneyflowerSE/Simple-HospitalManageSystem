package HospitalManagermentSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

@SuppressWarnings("ALL")
public class Patient {
    private final Connection connection;
    private final Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.print("Enter the name of the patient: ");
        String name = scanner.nextLine();
        scanner.nextLine();
        System.out.print("Enter the age of the patient: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the gender of the patient: ");
        String gender = scanner.nextLine();

        try{
            String query = "INSERT INTO patients (name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient added successfully");
            }
            else {
                System.out.println("Patient not added");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatient() {
        String query = "select * from patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients:");
            System.out.println("+------------+--------------------+----------+----------+");
            System.out.println("| Patient Id | Name               | Age      | Gender   |");
            System.out.println("+-------------------------------------------------------+");


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                System.out.printf("| %-10d | %-18s | %-8d | %-8s |\n", id, name, age, gender);
            }
            System.out.println("+------------+--------------------+----------+----------+");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id){
        String query = "select * from patients where id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
