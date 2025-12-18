//File 클래스 사용

import java.io.File;
import java.io.IOException;

public class IOtest1 {
    public static void main(String[] args) {
        /*
            a: 실제로 있는 파일의 절대경로
            b: 실제로 있는 폴더의 절대경로
            c: 아직 없는 파일의 상대경로
            d: 아직 없는 폴더의 상대경로
         */
        File a = new File("C:\\workspace48A\\web1\\build.gradle");
        File b = new File("c:\\windows");
        File c = new File("a.txt");
        File d = new File("sub");

        System.out.println(a.exists());
        System.out.println(a.isFile());
        System.out.println(a.isDirectory());
        System.out.println(a.canRead());
        System.out.println(a.getName());
        System.out.println(a.getParent());
        try {
            System.out.println(a.getCanonicalPath()); //절대경로 구함
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(a.length()); //파일 크기

        //c가 가리키는 파일을 생성하고 결과 출력
        try {
            if (c.createNewFile()) {
                System.out.println(c.getName() + " 파일 생성");
            }
            else {
                System.out.println("파일 생성 실패");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //c가 가리키는 파일 삭제
        //c.delete();

        //d가 가리키는 폴더 생성하고 결과 출력
        if ( !d.isDirectory()) {
            d.mkdirs();
        }


        System.out.println();
        System.out.println();




    }
}