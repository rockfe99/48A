import java.io.FileWriter;

public class IOtest2 {
    public static void main(String[] args) throws Exception {
        //텍스트파일 만들어서 몇글자 저장

        //스트림 생성 (파일생성됨. 같은이름이 있으면 덮어씀)
        FileWriter out = new FileWriter("b.txt");
        //입력 또는 출력 작업
        out.write('가');
        out.write('\n');
        out.write('A');
        //스트림 닫기
        out.close();

    }
}
