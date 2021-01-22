package hellomq.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateLinux {


    public static void main(String[] args) {
        SSHTool sshTool = SSHTool.getInstance();
        boolean initResult = sshTool.init("192.168.6.196", 22, "root", "passw0rd");
        if (!initResult) {
            System.out.println("修改hosts文件失败");
            sshTool.close();
        }
       sshTool.execCmd("sed -i '/^::1.*local/d'  text");
       sshTool.close();

    }
}
