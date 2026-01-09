package com.anas.mealster.infra.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
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
    var token: String,
    // FetchType.LAZY means Hibernate will not fetch the User data from the database until 'user' is explicitly accessed in the code.
    // This prevents unnecessary JOINs or extra SELECT queries if we only need the token details
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user : UserEntity,
    @Column
    var usedAt: Instant,
    @CreationTimestamp
    var createdAt : Instant = Instant.now()
)