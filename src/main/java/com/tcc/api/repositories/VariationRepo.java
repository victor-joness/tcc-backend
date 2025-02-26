package com.tcc.api.repositories;

import com.tcc.api.models.Law;
import com.tcc.api.models.Variation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariationRepo extends JpaRepository<Variation, Long> {
}

