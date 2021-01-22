package hellomq.utils;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * 执行Shell工具类
 *
 * @author JustryDeng
 * @date 2019/4/29 16:29
 */
public class SSHTool {

    private Logger logger = LoggerFactory.getLogger(SSHTool.class);

    private Session session;

    private Channel channel;

    private ChannelExec channelExec;

    private SSHTool() {
    }

    /**
     * 获取SSHTool类实例对象
     *
     * @return 实例
     * @date 2019/4/29 16:58
     */
    public static SSHTool getInstance() {
        return new SSHTool();
    }

    /**
     * 初始化
     *
     * @param ip
     *         远程Linux地址
     * @param port
     *         端口
     * @param username
     *         用户名
     * @param password
     *         密码
     * @throws JSchException
     *         JSch异常
     * @date 2019/3/15 12:41
     */
    public boolean init(String ip, Integer port, String username, String password) {
        JSch jsch = new JSch();
        try {
            jsch.getSession(username, ip, port);
            session = jsch.getSession(username, ip, port);
            session.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.connect(20 * 1000);
            logger.info("Session connected!");
            // 打开执行shell指令的通道
            channel = session.openChannel("exec");
            logger.info("open channel success!");
            channelExec = (ChannelExec) channel;
            return true;
        } catch (JSchException e) {
            logger.info("connect to :" + ip + " failed: ", e);
            close();
            return false;
        }
    }

    /**
     * 执行一条命令
     */
    public String execCmd(String command) {
        logger.info("exec command start!");
        if (session == null || channel == null || channelExec == null) {
            logger.info("please invoke init(...) first!");
            return null;
        }
        channelExec.setCommand(command);
        channel.setInputStream(null);
        channelExec.setErrStream(System.err);
        try {
            channel.connect(10 * 1000);
            logger.info("channel connected!");
            StringBuilder sb = new StringBuilder(16);
            ExecutorService executor = Executors.newSingleThreadExecutor();
            // 执行脚本
            Future<String> future = executor.submit(() ->
            {
                InputStream in = channelExec.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr);
                String buffer;
                while ((buffer = reader.readLine()) != null) {
                    sb.append("\n").append(buffer);
                }
                String result = sb.toString();
                logger.info("execute command success!");
                reader.close();
                isr.close();
                in.close();
                return result;
            });
            return future.get(20, TimeUnit.SECONDS);
        } catch (JSchException e) {
            logger.info("channel connect failed: ", e);
            return null;
        } catch (InterruptedException | ExecutionException e) {
            logger.info("execute command failed: ", e);
            return null;
        } catch (TimeoutException e) {
            logger.info("execute command timeout!");
            return null;
        }
    }

    /**
     * 释放资源
     *
     * @date 2019/3/15 12:47
     */
    public void close() {
        if (channelExec != null && channelExec.isConnected()) {
            channelExec.disconnect();
        }
        if (channel != null && channel.isConnected()) {
            channel.disconnect();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

}