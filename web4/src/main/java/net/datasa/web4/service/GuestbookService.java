package net.datasa.web4.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web4.dto.GuestbookDTO;
import net.datasa.web4.entity.GuestbookEntity;
import net.datasa.web4.repository.GuestbookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class GuestbookService {

    final GuestbookRepository repository;

    /**
     * 방명록 정보 저장
     * @param dto 저장할 글 정보 (이름, 비번, 내용)
     */
    public void write(GuestbookDTO dto) {
        //1. 기본생성자로 생성 후 값 세팅
        //GuestbookEntity entity = new GuestbookEntity();
        //entity.setName(dto.getName());
        //2. 모든 값을 전달받는 생성자를 이용해서 생성
        //GuestbookEntity entity2 = new GuestbookEntity(
        // 0, dto.getName(), dto.getPassword(), dto.getMessage(), null);
        //3. 빌더를 이용해서 생성
        GuestbookEntity entity = GuestbookEntity.builder()
                .name(dto.getName())
                .password(dto.getPassword())
                .message(dto.getMessage())
                .build();

        repository.save(entity);
    }

    /**
     * 방명록 글을 모두 조회.
     * @return 방명록 글 목록
     */
    public List<GuestbookDTO> getList() {
        //정렬 기준
        Sort sort = Sort.by(Sort.Direction.DESC, "num");
        List<GuestbookEntity> entityList = repository.findAll(sort);
        List<GuestbookDTO> dtoList = new ArrayList<GuestbookDTO>();

        for (GuestbookEntity entity : entityList) {
            GuestbookDTO dto = GuestbookDTO.builder()
                    .num(entity.getNum())
                    .name(entity.getName())
                    .password(entity.getPassword())
                    .message(entity.getMessage())
                    .inputdate(entity.getInputdate())
                    .build();
            dtoList.add(dto);
        }
        return dtoList;
    }
}
