package poke.center.api.domain.user;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import poke.center.api.domain.role.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Table(name = "user")
@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String login;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "userRole",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<Role> roles = new HashSet<>();

    public User(UserRegisterData data) {
        this.name = data.name();
        this.login = data.login();
        this.password = data.password();
    }


    public void registerAsNurse() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        this.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        });
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
