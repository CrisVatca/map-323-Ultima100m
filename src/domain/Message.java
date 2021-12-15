package domain;

import java.time.LocalDateTime;
import java.util.List;

public class Message extends Entity<Long>{
    private Utilizator from;
    private List<Utilizator> to;
    private String message;
    private LocalDateTime data;
    private Long reply;

    public Message(Utilizator from, List<Utilizator> to, String message, LocalDateTime data, Long reply) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
        this.reply = reply;
    }

    public Long getReply() {
        return reply;
    }

    public void setReply(Long reply) {
        this.reply = reply;
    }

    public Utilizator getFrom() {
        return from;
    }

    public void setFrom(Utilizator from) {
        this.from = from;
    }

    public List<Utilizator> getTo() {
        return to;
    }

    public void setTo(List<Utilizator> to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
