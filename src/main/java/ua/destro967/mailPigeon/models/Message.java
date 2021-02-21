package ua.destro967.mailPigeon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message extends BaseEntity {
    @Id
    @SequenceGenerator(name = "message_seq", sequenceName = "messages_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    private long id;

    @Column(name = "type", nullable = false)
    private short typeId;

    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "data", nullable = false)
    private byte[] data;

    @Column(name = "read_status", nullable = false)
    private boolean isRead;

    @Column(name = "send_status", nullable = false)
    private boolean isSend;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "target_room_id", referencedColumnName = "id")
    private Room room;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", isRead=" + isRead +
                ", isSend=" + isSend +
                ", user=" + user.getUsername() +
                ", room=" + room.getName() +
                '}';
    }
}
