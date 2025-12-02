package net.datasa.web4.repository;

import net.datasa.web4.entity.GuestbookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository
    extends JpaRepository<GuestbookEntity, Integer> {


}
