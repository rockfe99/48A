package net.datasa.web3.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web3.dto.PersonDTO;
import net.datasa.web3.entity.PersonEntity;
import net.datasa.web3.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 회원 관련 처리 서비스
 */
@Slf4j
@Transactional
@Service
public class PersonService {

    //의존성주입.
    @Autowired
    PersonRepository repository;

    public void test() {
        PersonEntity entity = new PersonEntity();
        entity.setId("abc");
        entity.setName("이영희");
        entity.setAge(30);

        repository.save(entity);
    }

    public void insertPerson(PersonDTO dto) {
        PersonEntity entity = new PersonEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());

        repository.save(entity);
    }

    public void deletePerson(String id) {
        //테이블의 primary key 기준으로 삭제
        repository.deleteById(id);
    }
}
