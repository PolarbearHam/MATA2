package com.ssafy.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DynamicInsert
@AllArgsConstructor
@Getter
@Entity
@Builder
@NoArgsConstructor
public class Member implements UserDetails {

    @Id @Column(name = "memberId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @NotNull
    private String password;

    @Size(max = 50)
    @NotNull
    private String email;

    @Size(max = 10)
    @NotNull
    private String name;

    @CreationTimestamp
    private LocalDateTime createAt;

    @ColumnDefault("false")
    private boolean isQuit;

    @OneToMany(mappedBy = "member")
    private List<Project> projectList =new ArrayList<>();

    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<String> privilege;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.privilege.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
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
