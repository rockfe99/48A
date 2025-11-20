package net.datasa.web3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity     //DB의 테이블과 매핑되는 클래스
@Table(name = "person") //person테이블
public class PersonEntity {
    @Id     //primary key
    @Column(name="id", nullable = false, length=30) //테이블의 컬럼과 매핑됨
    String id;

    @Column
    String name;

    @Column
    Integer age;
}
