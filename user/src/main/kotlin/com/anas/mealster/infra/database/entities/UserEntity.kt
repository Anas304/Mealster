package com.anas.mealster.infra.database.entities

import com.anas.mealster.domain.model.UserId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(
    name = "users",
    schema = "users_service",
    indexes = [
        Index(name = "email_idx", columnList = "email", unique = true),
        Index(name = "username_idx", columnList = "username", unique = true)
    ]
)
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id : UserId? = null,
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false, unique = true)
    var username: String,
    @Column(nullable = false)
    var hashedPassword: String,
    @Column(nullable = false)
    var hasVerifiedEmail: Boolean = false,
    @CreationTimestamp
    var createdAt : Instant = Instant.now(),
    @UpdateTimestamp
    var updatedAt : Instant = Instant.now()
)