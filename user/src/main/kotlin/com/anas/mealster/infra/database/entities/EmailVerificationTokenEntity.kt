package com.anas.mealster.infra.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant

@Entity
@Table(
    name = "email_verification_tokens",
    schema = "user_service"
)
class EmailVerificationTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = 0,
    @Column(nullable = false)
    var hashedToken: String,
    @ManyToOne(fetch = FetchType.LAZY)
    var user : UserEntity,
    @Column
    var usedAt: Instant,
    @CreationTimestamp
    var createdAt : Instant = Instant.now()
)