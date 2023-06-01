package poke.center.api.domain.role;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "role")
@Entity(name = "Role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Role(String name) {
        this.name = name;
    }
}
