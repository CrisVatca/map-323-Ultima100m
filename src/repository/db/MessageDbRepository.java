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
    private Repository<Long,Utilizator> repoUtilizatori;

    public MessageDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Message findOne(Long aLong) {

        String sql = "select * from messages where id = ?";
        Message mess = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long from = resultSet.getLong("from_user");
                Long to = resultSet.getLong("to_user");

                String message = resultSet.getString("message");
                Timestamp timestamp = resultSet.getTimestamp("data");
                LocalDateTime data = timestamp.toLocalDateTime();

                Long reply = resultSet.getLong("reply");

                mess = new Message(from, to, message, data, reply);
                mess.setId(id);
                return mess;
                }
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

                Long from = resultSet.getLong("from_user");
                Long to = resultSet.getLong("to_user");

                String message = resultSet.getString("message");
                Timestamp timestamp = resultSet.getTimestamp("data");
                LocalDateTime data = timestamp.toLocalDateTime();

                Long reply = resultSet.getLong("reply");
                /*Message reply1=null;
                if(reply!=null){
                    reply1=findOne(Long.parseLong(reply));
                }*/

                Message mess = new Message(from, to, message, data, reply);
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

            ps.setLong(1, entity.getFrom());

            ps.setLong(2, entity.getTo());
            ps.setString(3, entity.getMessage());
            ps.setObject(4, entity.getData());

            if(entity.getReply()!=null && entity.getReply()!=0)
                ps.setLong(5,entity.getReply());
            else ps.setLong(5,0);

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
