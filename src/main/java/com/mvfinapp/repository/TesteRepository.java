package com.mvfinapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvfinapp.model.TesteModel;

@Repository
public interface TesteRepository extends JpaRepository<TesteModel, Long> {

}
