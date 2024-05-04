package net.dudko.project.domain.repository;

import net.dudko.project.domain.entity.blockchain.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

    Block findTopByOrderByIdDesc();

}
