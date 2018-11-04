import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: qxiong
 * @date: 2018/10/18
 * @description:
 */
public class ExeTest {
    public static void main(String[] args) {
        String job = "python D:\\software\\datax\\datax\\bin\\datax.py D:\\software\\datax\\datax\\job\\job2.json";
        String json = "python E:\\datax\\bin\\datax.py -r oraclereader -w txtfilewriter";

        String exec = job;

        System.out.println("*");
        Process process;
        try {
            process = Runtime.getRuntime().exec(exec);
//            获取结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while (null != (line = reader.readLine())) {
                System.out.println(line);
            }
            reader.close();
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
