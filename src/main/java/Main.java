import org.activiti.engine.RepositoryService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by zhaozhirong on 2018/11/23.
 */
public class Main {
    public static void main(String[] args) throws IOException {
//        writeFile();
//        int count = readFileLineCounts();
//        System.out.println(count);
//        Long startTime = System.currentTimeMillis();
//        StringBuffer stringBuffer = new StringBuffer();
//        boolean result = validateFile("E:\\zzr\\sample.txt",stringBuffer);
//        if(!result){
//            System.out.println(stringBuffer.toString());
//        }
//        Long endTime = System.currentTimeMillis();
//        System.out.println((endTime-startTime)/1000);
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("/spring/spring-activiti.xml");
        RepositoryService repositoryService = (RepositoryService) applicationContext.getBean("repositoryService");
        String deploymentId = repositoryService
                .createDeployment()
                .addClasspathResource("student_leave.bpmn")
                .deploy()
                .getId();

    }

    public static void writeFile() throws IOException{
        int count = 300000;
        int start = 1101111000;
        File file = new File("E:\\zzr\\sample.txt");
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osr = new OutputStreamWriter(fos,"UTF-8");
        BufferedWriter bufferedWriter = new BufferedWriter(osr);
        for (int i = 0; i < count; i++){
            int result = start+i;
            System.out.println(result);
            bufferedWriter.write(String.valueOf(result)+"   "+"2018-01-11"+"    ");
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    public static int readFileLineCounts() throws IOException {
        int count = 0;
        File file = new File("E:\\zzr\\sample.txt");
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String result = "";
        while((result = br.readLine())!=null){
            System.out.println(result);
            count++;
        }
        return count;
    }

    /**
     * 校验日期是否合法
     * @param str
     * @return
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str.trim());
        } catch (ParseException e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }

    /**
     * 校验文件是否合法
     * @param filePath
     * @param stringBuffer
     * @return
     * @throws IOException
     */
    public static boolean validateFile(String filePath,StringBuffer stringBuffer) throws IOException {
        int count = 0;
        boolean isOk = true;
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String result = null;
        while((result = br.readLine())!=null){
            count++;
            String[] data = result.split("  ");
            if(data.length < 2){
                stringBuffer.append("第"+count+"行缺少数据项").append("\n");
            }else{
                if(!isValidDate(data[1])){
                    stringBuffer.append("第"+count+"行日期格式错误").append("\n");
                }
            }
        }
        if(stringBuffer.length() > 0){
            isOk = false;
        }
        return isOk;
    }
}
