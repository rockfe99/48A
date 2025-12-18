import java.io.BufferedReader;
import java.io.FileReader;

public class IOtest4 {
    public static void main(String[] args) throws Exception {
        //텍스트 파일을 읽어서 화면에 출력
        BufferedReader in = new BufferedReader(new FileReader("C:\\workspace48A\\JavaTest\\src\\IOtest1.java"));
//        FileReader in = new FileReader("b.txt");
        String s;

        while(true) {
            s = in.readLine();
            if (s == null) break;
            System.out.println(s);
        }

        in.close();

    }
}
