-- web5.sql

-- 회원정보 테이블 (web5_member)
create table web5_member (
     member_id varchar(30) primary key,          -- 회원을 구분하는 아이디
     member_password varchar(100) not null,      -- 비밀번호(암호화)
     member_name varchar(30) not null,           -- 회원 이름
     email varchar(50),                          -- 이메일
     phone varchar(30),                          -- 전화번호
     address varchar(200),                       -- 주소
     enabled tinyint default 1 check(enabled in (0, 1)),
        -- 계정상태. 1:사용가능, 0:사용불가능
     rolename varchar(30) default 'role_user'
        check (rolename in ('ROLE_USER', 'ROLE_ADMIN'))
        -- 사용자 구분. 'role_user', 'role_admin' 중 하나
);