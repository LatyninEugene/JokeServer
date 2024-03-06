package ru.latynin.joke.collector.common;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.latynin.joke.collector.domain.entity.User;

import java.util.Optional;

@UtilityClass
public class SecurityUtils {

    public static User requireCurrentUser() {
        return getAuthentication()
                .filter(auth -> auth.getPrincipal() != null)
                .map(auth -> (User) auth.getPrincipal())
                .orElseThrow();
    }

    private Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }

}
