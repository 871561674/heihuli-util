package com.heihuli.util;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * ssh工具
 *
 * @author heihuli
 */
public class SshUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SshUtil.class);

    /**
     * 如果提供的用户名有误，对端的 SSH server 会有一个 FAIL_DELAY，通常会是 2-3s；这导致这个函数会有 1s 延时才返回，定义在下面的 setTimeout 函数。
     * <p>
     * <b>注意：调用方必须负责将 session.disconnect()。</b>
     */
    public static Session getConnectedSession(SshHostLoginInfo sshHostLoginInfo) {
        JSch jsch = new JSch();

        Session session;
        try {
            session = jsch.getSession(sshHostLoginInfo.getUsername(), sshHostLoginInfo.getHost(), sshHostLoginInfo.getPort());
        } catch (JSchException e) {
            LOG.error("jsch.getSession failed", e);
            throw new RuntimeException(e);
        }

        session.setPassword(sshHostLoginInfo.getPassword());
        // 不要检查 host key，因为我们不会提前把要连接主机的 host key 加进来
        session.setConfig("StrictHostKeyChecking", "no");
        // 不要重试了，失败就失败
        session.setConfig("MaxAuthTries", "1");
        // 仅使用密码去尝试登陆；默认会有四种方式（参考 JSch 类的源码），验证起来很慢
        session.setConfig("PreferredAuthentications", "password");

        // Ciphers, kex algorithm, signatures 参数选择，参考了 Mozilla 的 OpenSSH guideline：
        // https://infosec.mozilla.org/guidelines/openssh.html
        // 选取的是 Modern OpenSSH 6.7+，CentOS 7 带的 OpenSSH 都符合这个要求。

        // 原本想要加快速度，但是发现没什么卵用
        // session.setConfig("CheckCiphers", "aes256-ctr");
        // session.setConfig("CheckKexes", "ecdh-sha2-nistp521");
        // session.setConfig("CheckSignatures", "ecdsa-sha2-nistp521");

        try {
            // Timeout in ms
            session.setTimeout(1000);
        } catch (JSchException e) {
            LOG.error("jsch session.setTimeout failed", e);
            throw new RuntimeException(e);
        }

        try {
            session.connect();
        } catch (JSchException e) {
            LOG.error("jsch connect failed", e);
            if ("Auth fail".equals(e.getMessage())) {
                LOG.debug("SSH Auth failed, {}", sshHostLoginInfo);
            }
            throw new RuntimeException(e);
        }
        return session;
    }

    /**
     * 检测连通性
     * @param userName
     * @param password
     * @param host
     * @param port
     * @return
     */
    public static boolean checkConnection(String userName, String password, String host, int port) {
        JSch jsch = new JSch();
        Session session = null;
        try {
            session = jsch.getSession(userName, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("MaxAuthTries", "1");
            session.setConfig("PreferredAuthentications", "password");
            session.setTimeout(1000);
            LOG.debug("SSH Connection status success, userName: {}, host: {}, port: {}", userName, host, port);
            session.connect();
            return true;
        } catch (Exception e) {
            LOG.error("SSH Connection status is failure, userName: {}, host: {}, port: {}", userName, host, port, e);
            return false;
        } finally {
            if (session != null) session.disconnect();
        }
    }

    /**
     * 执行远程命令
     * @param command
     * @param sshHostLoginInfo
     * @return
     */
    public static ExecResult execCommand(String command, SshHostLoginInfo sshHostLoginInfo){
        Session session = null;
        Channel channel = null;
        try {
            session = SshUtil.getConnectedSession(sshHostLoginInfo);

            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            ByteArrayOutputStream stdout = new ByteArrayOutputStream();
            ByteArrayOutputStream stderr = new ByteArrayOutputStream();
            channel.setInputStream(null);
            channel.setOutputStream(stdout);
            ((ChannelExec) channel).setErrStream(stderr);

            // in milli-seconds (1E-3 second)
            channel.connect(1000);
            while (!channel.isClosed()) {
                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    // safely ignored
                }
            }

            String stdoutString, stderrString;
            try {
                stdoutString = stdout.toString("UTF-8");
            } catch (UnsupportedEncodingException e) {
                stdoutString = stdout.toString();
            }
            try {
                stderrString = stderr.toString("UTF-8");
            } catch (UnsupportedEncodingException e) {
                stderrString = stderr.toString();
            }
            return new ExecResult(stdoutString, stderrString, channel.getExitStatus());
        } catch (JSchException e) {
            LOG.error("SSH operation failed", e);
            throw new RuntimeException();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    public static class ExecResult {
        private String stdout;
        private String stderr;
        private Integer exitCode;

        public String getStdout() {
            return stdout;
        }

        public void setStdout(String stdout) {
            this.stdout = stdout;
        }

        public String getStderr() {
            return stderr;
        }

        public void setStderr(String stderr) {
            this.stderr = stderr;
        }

        public Integer getExitCode() {
            return exitCode;
        }

        public void setExitCode(Integer exitCode) {
            this.exitCode = exitCode;
        }

        public ExecResult(String stdout, String stderr, Integer exitCode) {
            this.stdout = stdout;
            this.stderr = stderr;
            this.exitCode = exitCode;
        }

        public ExecResult() {
        }

        @Override
        public String toString() {
            return "ExecResult{" +
                    "stdout='" + stdout + '\'' +
                    ", stderr='" + stderr + '\'' +
                    ", exitCode=" + exitCode +
                    '}';
        }
    }

    public static class SshHostLoginInfo {
        private String host;
        private Integer port;
        private String username;
        private String password;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
