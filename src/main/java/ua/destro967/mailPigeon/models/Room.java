package ua.destro967.mailPigeon.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @SequenceGenerator(name = "room_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
    private long id;

    @Column(name ="name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "rooms")
    private List<User> users;

    @OneToOne(mappedBy = "room")
    private Message message;
}
