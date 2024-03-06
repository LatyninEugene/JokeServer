package ru.latynin.joke.collector.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.latynin.joke.collector.domain.common.Role;
import ru.latynin.joke.collector.domain.common.Token;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

import static ru.latynin.joke.collector.domain.entity.User.COLLECTION_NAME;

@Data
@Builder
@FieldNameConstants
@Document(value = COLLECTION_NAME)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

    public static final String COLLECTION_NAME = "users";

    @Id
    @Builder.Default
    String id = UUID.randomUUID().toString();
    String email;
    String password;
    Role role;
    Token lastToken;
    Instant createdDate;
    @Builder.Default
    boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
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
        return !lastToken.isRevoked();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void updateToken(String accessToken) {
        setLastToken(Token.builder()
                .token(accessToken)
                .createdDate(Instant.now())
                .build());
    }
}
