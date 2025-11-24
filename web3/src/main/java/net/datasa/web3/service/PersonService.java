package net.datasa.web3.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web3.dto.PersonDTO;
import net.datasa.web3.entity.PersonEntity;
import net.datasa.web3.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 회원 관련 처리 서비스
 * 클래스에 대한 설명. 작성자. 작성일. 수정에 관한 정보.
 */
@Slf4j
@Transactional
@Service
public class PersonService {

    /**
     * 회원관련 DB처리 리포지토리
     */
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

    /**
     * 1명의 회원정보 조회
     * @param id    조회할 회원의 ID
     * @return      해당 ID를 가진 회원의 모든 정보. 없으면 null.
     */
    public PersonDTO selectPerson(String id) {
        PersonEntity entity = repository.findById(id).orElse(null);
        PersonDTO dto = null;

        if (entity != null) {
            dto = new PersonDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setAge(entity.getAge());
        }
        return dto;
    }

    /**
     * 모든 회원의 정보를 리스트로 리턴
     * @return 모든 회원의 정보를 담은 ArrayList 객체
     */
    public List<PersonDTO> selectAll() {
        List<PersonEntity> entityList = repository.findAll();
        List<PersonDTO> dtoList = new ArrayList<>();

        for (PersonEntity entity : entityList) {
            PersonDTO dto = new PersonDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setAge(entity.getAge());
            dtoList.add(dto);
        }
        return dtoList;
    }
}
