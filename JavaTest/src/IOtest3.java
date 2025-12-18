import java.io.FileReader;
import java.io.FileWriter;

public class IOtest3 {
    public static void main(String[] args) throws Exception {
        //텍스트 파일을 읽어서 화면에 출력
        FileReader in = new FileReader("C:\\workspace48A\\JavaTest\\src\\IOtest1.java");
//        FileReader in = new FileReader("b.txt");
        int ch;

        while(true) {
            ch = in.read();
            if (ch == -1) break;
            System.out.println((char) ch);
        }

        in.close();

    }
}
