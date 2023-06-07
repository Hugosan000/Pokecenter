package poke.center.api.domain.pokemon;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "pokemon")
@Entity(name = "Pokemon")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pokemon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
