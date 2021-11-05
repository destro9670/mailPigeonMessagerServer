package ua.destro967.mailPigeon.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tokens")
public class Token{

    @Id
    @SequenceGenerator(name = "token_seq", sequenceName = "tokens_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq")
    private long id;

    @Column(name = "access", nullable = false)
    private String access;

    @Column(name = "refresh", nullable = false)
    private String refresh;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
