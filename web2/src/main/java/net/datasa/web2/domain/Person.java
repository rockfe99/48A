package net.datasa.web2.domain;

import lombok.*;

@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든 변수값을 전달받아 초기화하는 생성자
//@RequiredArgsConstructor //필요한 값만(final) 전달받아 초기화하는 생성자
@Data       //Getter, Setter, ToString 포함
//@Getter
//@Setter
//@ToString
public class Person {
    //public > protected > (default) > private
    String id;
    String password;
    String name;
    String address;
}
