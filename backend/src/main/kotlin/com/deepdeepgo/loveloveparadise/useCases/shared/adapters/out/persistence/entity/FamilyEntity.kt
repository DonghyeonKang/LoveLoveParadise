package com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "families")
@SQLRestriction("deleted_at IS NULL")
class FamilyEntity(
  @Id @Column(nullable = false, updatable = false) val id: String,
) : AuditEntity()
