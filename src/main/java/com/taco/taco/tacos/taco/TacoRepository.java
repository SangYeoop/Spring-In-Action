package com.taco.taco.tacos.taco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface TacoRepository extends JpaRepository<Taco, Long> {

}
