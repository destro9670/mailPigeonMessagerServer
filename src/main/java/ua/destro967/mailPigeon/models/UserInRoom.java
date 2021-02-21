package ua.destro967.mailPigeon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_in_rooms")
public class UserInRoom extends BaseEntity {
    @Id
    @SequenceGenerator(name = "users_in_rooms_seq", sequenceName = "users_in_rooms_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_in_rooms_seq")
    private long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(targetEntity = Room.class)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;


}
