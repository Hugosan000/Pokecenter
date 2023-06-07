package poke.center.api.domain.pokemon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Page<Pokemon> findAll(Pageable pagination);
}
