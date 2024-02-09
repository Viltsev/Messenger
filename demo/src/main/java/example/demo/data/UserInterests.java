package example.demo.data;

import jakarta.persistence.*;

@Entity
@Table(name = "user_interests")
public class UserInterests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interest", unique = true)
    private String interest;
}