package ua.destro967.mailPigeon.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room extends BaseEntity {
    @Id
    @SequenceGenerator(name = "room_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private long id;

    @Column(unique = true, name = "uuid", nullable = false)
    private String uuid = UUID.randomUUID().toString().toUpperCase();

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "rooms")
    private List<User> users;

    @OneToOne(mappedBy = "room")
    private Message message;

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users='" + users.toString() + '\'' +
                '}';
    }
}
