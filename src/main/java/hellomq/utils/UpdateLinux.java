package hellomq.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateLinux {


    public static void main(String[] args) {
        SSHTool sshTool = SSHTool.getInstance();
        boolean initResult = sshTool.init("10.10.69.41", 22, "root", "passw0rd");
        if (!initResult) {
            System.out.println("修改hosts文件失败");
            sshTool.close();
        }
        /*String a =sshTool.execCmd("sed -i '/^::1.*a/d'  text\n" +
                "sed -i '/^::1.*b/d'  text\n" +
                "sh sshs\n");
        System.out.println(a);*/
        sshTool.execCmd("sh sshs\n");

//        sshTool.execCmd("sed -i '/^::1.*bocloud/d'  /etc/hosts\n" +
//                "wget  -O  /root/update.sh  http://132.7.54.144/update.sh\n" +
//                "sh /root/update.sh  2> /root/update_stderr\n");
       sshTool.close();

    }
}
