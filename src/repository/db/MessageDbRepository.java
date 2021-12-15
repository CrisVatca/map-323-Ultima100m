package repository.db;

import domain.Message;
import domain.Utilizator;
import repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class MessageDbRepository implements Repository<Long, Message> {
    private String url;
    private String username;
    private String password;

    public MessageDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Message findOne(Long aLong) {
        Message mess = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT * from messages");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                if(Objects.equals(aLong, id)){
                    String  from = resultSet.getString("from_user");
                    String[] fromSplit = from.split(",");
                    Long idUser = Long.parseLong(fromSplit[0]);
                    Utilizator fromUser = new Utilizator(fromSplit[1],fromSplit[2]);
                    fromUser.setId(idUser);

                    String to = resultSet.getString("to_user");
                    String[] toUsers = to.split(";");
                    List<Utilizator> users = new ArrayList<>();
                    for(int i=0;i<toUsers.length;i++){
                        String[] toUsersSplit = toUsers[i].split(",");
                        Long idUser1 = Long.parseLong(toUsersSplit[0]);
                        Utilizator toUser = new Utilizator(toUsersSplit[1],toUsersSplit[2]);
                        toUser.setId(idUser1);
                        users.add(toUser);
                    }

                    String message = resultSet.getString("message");
                    Timestamp timestamp = resultSet.getTimestamp("data");
                    LocalDateTime data = timestamp.toLocalDateTime();

                    mess = new Message(fromUser, users, message, data);
                    mess.setId(id);
                }
            }
            return mess;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mess;
    }

    @Override
    public Iterable<Message> findAll() {
        Set<Message> messages = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement
                     ("SELECT * from messages");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");

                String  from = resultSet.getString("from_user");
                String[] fromSplit = from.split(",");
                Long idUser = Long.parseLong(fromSplit[0]);
                Utilizator fromUser = new Utilizator(fromSplit[1],fromSplit[2]);
                fromUser.setId(idUser);

                String to = resultSet.getString("to_user");
                String[] toUsers = to.split(";");
                List<Utilizator> users = new ArrayList<>();
                for(int i=0;i<toUsers.length;i++){
                    String[] toUsersSplit = toUsers[i].split(",");
                    Long idUser1 = Long.parseLong(toUsersSplit[0]);
                    Utilizator toUser = new Utilizator(toUsersSplit[1],toUsersSplit[2]);
                    toUser.setId(idUser1);
                    users.add(toUser);
                }

                String message = resultSet.getString("message");
                Timestamp timestamp = resultSet.getTimestamp("data");
                LocalDateTime data = timestamp.toLocalDateTime();

                Message mess = new Message(fromUser, users, message, data);
                mess.setId(id);
                messages.add(mess);
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public Message save(Message entity) {
        String sql = "insert into messages (from_user, to_user, message, data) values (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            String fromUser = "";
            fromUser += entity.getFrom().getId() + "," + entity.getFrom().getFirstName() + "," +
                    entity.getFrom().getLastName();

            ps.setString(1, fromUser);

            String toUsers = "";
            for(int i=0;i<entity.getTo().size();i++){
                if(i==entity.getTo().size()-1)
                    toUsers += entity.getTo().get(i).getId() + "," + entity.getTo().get(i).getFirstName() + "," +
                            entity.getTo().get(i).getLastName();
                else
                    toUsers += entity.getTo().get(i).getId() + "," + entity.getTo().get(i).getFirstName() + "," +
                            entity.getTo().get(i).getLastName() + ";";
            }

            ps.setString(2, toUsers);
            ps.setString(3, entity.getMessage());
            ps.setObject(4, entity.getData());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message delete(Long aLong) {
        String sql = "delete from messages where id=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, aLong);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message update(Message entity) {
        return null;
    }

    @Override
    public Map<Long, Message> getEntities() {
        return null;
    }

    @Override
    public Message setFriends(Message one) {
        return null;
    }
}
