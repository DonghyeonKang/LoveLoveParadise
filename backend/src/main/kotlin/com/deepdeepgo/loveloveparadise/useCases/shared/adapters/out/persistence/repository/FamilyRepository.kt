package com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository

import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.FamilyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FamilyRepository : JpaRepository<FamilyEntity, String>
