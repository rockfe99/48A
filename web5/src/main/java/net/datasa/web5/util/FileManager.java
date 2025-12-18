package net.datasa.web5.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 파일 업로드, 다운로드, 삭제
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class FileManager {

    /**
     * 파일을 저장하고 저장된 파일명을 리턴한다.
     *
     * @param path 폴더의 절대경로
     * @param file 저장할 파일 정보
     * @return 저장된 파일명
     * @throws IOException 파일 저장 중 발생한 예외
     */
    public String saveFile(String path, MultipartFile file) throws IOException {

        // 디렉토리가 없으면 생성
        File directoryPath = new File(path);

        if (!directoryPath.isDirectory()) {
            directoryPath.mkdirs();
        }

        // ==== 서버에 저장할 파일명 생성 ====
        // 파일의 원래 이름
        String originalFileName = file.getOriginalFilename();
        //원래 이름의 확장자
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        //오늘 날짜를 문자열로 변환
        String dateString = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        //UUID(Universally Unique Identifier) 생성
        //UUID는 128비트 숫자로 고유한 식별자를 생성하기 위한 표준.
        //예를 들어 "123e4567-e89b-12d3-a456-426614174000" 형태
        String uuidString = UUID.randomUUID().toString();
        String fileName = dateString + "_" + uuidString + extension;

        // 파일 복사하여 저장
        File filePath = new File(directoryPath + "/" + fileName);
        file.transferTo(filePath);

        log.debug("파일 정보 : 원래 이름: {}, 저장된 이름: {}, 크기: {} bytes", file.getOriginalFilename(), fileName, file.getSize());
        return fileName;
    }

    /**
     * 지정된 경로와 파일명으로 디스크에서 파일을 삭제한다.
     *
     * @param path 파일이 위치한 폴더의 절대경로
     * @param fileName 삭제할 파일명
     * @return 파일 삭제 성공 여부
     */
    public boolean deleteFile(String path, String fileName) throws Exception {
        Path filePath = Paths.get(path, fileName);
        return Files.deleteIfExists(filePath);
    }
}
