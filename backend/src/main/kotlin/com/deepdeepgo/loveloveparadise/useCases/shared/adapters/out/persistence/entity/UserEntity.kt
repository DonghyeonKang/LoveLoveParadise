package com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "users")
@SQLRestriction("deleted_at IS NULL")
class UserEntity(
  @Id @Column(nullable = false, updatable = false) val id: String,
  @Column(nullable = false, unique = true) val email: String,
  @Column(nullable = false) val password: String,
  @Column(nullable = false) val name: String,
  @Column(nullable = false) val familyId: String,
) : AuditEntity()
