package net.datasa.web3.repository;

import net.datasa.web3.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository
        extends JpaRepository<PersonEntity, String> {
    //person테이블에 대한 작업. 그 테이블의 primary key는 문자열이다


}
