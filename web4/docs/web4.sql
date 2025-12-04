-- [web4 방명록 예제 관련 테이블]

use test;

-- 테이블 삭제
drop table if exists guestbook;

-- 글 저장 테이블
create table guestbook (
    num     integer auto_increment primary key,    -- 글번호. 기본키
    name    varchar(100) not null,                 -- 작성자 이름
    password varchar(100) not null,                 -- 비밀번호
    message text not null,                          -- 게시글 내용
    inputdate timestamp default current_timestamp   -- 작성시간
);

-- 저장 예
insert into guestbook (name, password, message) values ('홍길동', '111', '안녕하세요.\n홍길동입니다.');

-- 조회
select * from guestbook;


-- 테이블 수정 SQL (컬럼 추가)
alter table guestbook
    add column recommend int default 0,     -- 추천수
    add column ip varchar(30);              -- 작성자 IP
