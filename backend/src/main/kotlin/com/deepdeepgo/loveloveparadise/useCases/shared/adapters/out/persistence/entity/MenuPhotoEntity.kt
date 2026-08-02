package com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "menu_photos")
@SQLRestriction("deleted_at IS NULL")
class MenuPhotoEntity(
  @Id @Column(nullable = false, updatable = false) val id: String,
  @Column(nullable = false, updatable = false) val familyId: String,
) : AuditEntity()
