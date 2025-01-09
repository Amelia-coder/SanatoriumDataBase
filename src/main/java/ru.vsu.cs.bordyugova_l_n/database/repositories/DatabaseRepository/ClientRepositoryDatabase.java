//package ru.vsu.cs.bordyugova_l_n.database.repositories.DatabaseRepository;
//
//import ru.vsu.cs.bordyugova_l_n.database.entities.Client;
//import ru.vsu.cs.bordyugova_l_n.database.repositories.InterfacesRepository.ClientRepository;
//import ru.vsu.cs.bordyugova_l_n.database.repositories.InterfacesRepository.Repository;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ClientRepositoryDatabase implements ClientRepository, Repository<Client> {
//    private final Connection connection;
//
//    public ClientRepositoryDatabase(Connection connection) {
//        this.connection = connection;
//    }
//
//    @Override
//    public void add(Client client) {
//        String sql = "INSERT INTO Client (ID, FirstName, LastName, MiddleName, Phone, Email, RoomNumber, ResortCard) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, client.getId().intValue());
//            stmt.setString(2, client.getName());
//            stmt.setString(3, client.getSurname());
//            stmt.setString(4, client.getSecondName());
//            stmt.setString(5, client.getPhone());
//            stmt.setString(6, client.getMail());
//            stmt.setInt(7, client.getIdRoom().intValue());
//            stmt.setString(8, client.getIdResortBook().toString());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void update(Client client) {
//        String sql = "UPDATE Client SET FirstName = ?, LastName = ?, MiddleName = ?, Phone = ?, Email = ?, RoomNumber = ?, ResortCard = ? WHERE ID = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, client.getName());
//            stmt.setString(2, client.getSurname());
//            stmt.setString(3, client.getSecondName());
//            stmt.setString(4, client.getPhone());
//            stmt.setString(5, client.getMail());
//            stmt.setInt(6, client.getIdRoom().intValue());
//            stmt.setString(7, client.getIdResortBook().toString());
//            stmt.setInt(8, client.getId().intValue());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void delete(Long id) {
//        String sql = "DELETE FROM Client WHERE ID = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, id.intValue());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public Client getById(Long id) {
//        String sql = "SELECT * FROM Client WHERE ID = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, id.intValue());
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
////                return new Client(
////                        (long) rs.getInt("ID"),
////                        rs.getString("FirstName"),
////                        rs.getString("LastName"),
////                        rs.getString("MiddleName"),
////                        rs.getString("Phone"),
////                        rs.getString("Email"),
////                        (long) rs.getInt("RoomNumber"),
////                        rs.getString("ResortCard")
////                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @Override
//    public List<Client> getAll() {
//        String sql = "SELECT * FROM Client";
//        List<Client> clients = new ArrayList<>();
//        try (Statement stmt = connection.createStatement()) {
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
////                clients.add(new Client(
////                        (long) rs.getInt("ID"),
////                        rs.getString("FirstName"),
////                        rs.getString("LastName"),
////                        rs.getString("MiddleName"),
////                        rs.getString("Phone"),
////                        rs.getString("Email"),
////                        (long) rs.getInt("RoomNumber"),
////                        rs.getString("ResortCard")
////                ));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return clients;
//    }
//}
