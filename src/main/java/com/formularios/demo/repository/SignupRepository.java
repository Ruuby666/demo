package com.formularios.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.formularios.demo.models.Signup;

public interface SignupRepository extends JpaRepository<Signup, Long> {
    boolean existsByEmail(String email);
}
