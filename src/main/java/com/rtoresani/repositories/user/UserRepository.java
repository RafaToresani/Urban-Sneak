package com.rtoresani.repositories.user;

import com.rtoresani.dtos.responses.UserResponse;
import com.rtoresani.entities.user.ERole;
import com.rtoresani.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    List<User> findAllByRole(ERole eRole);
}
