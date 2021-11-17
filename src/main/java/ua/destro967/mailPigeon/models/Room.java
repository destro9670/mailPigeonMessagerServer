package ua.destro967.mailPigeon.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room{
    @Id
    @SequenceGenerator(name = "room_seq", sequenceName = "rooms_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    private long id;

    @Column(unique = true, name = "uuid", nullable = false)
    private String uuid = UUID.randomUUID().toString().toUpperCase();

    @ManyToOne
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id")
    private List<Message> messages;

    @CreatedDate
    @Column(name = "created")
    private Date created;
}
