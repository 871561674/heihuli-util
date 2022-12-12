package com.heihuli.test;

import com.heihuli.util.SshUtil;
import org.junit.Test;

/**
 * @author heihuli
 */
public class SshUtilTest {

    @Test
    public void test() {
        SshUtil.SshHostLoginInfo info = new SshUtil.SshHostLoginInfo();
        info.setHost("114.132.45.62");
        info.setPort(22);
        info.setUsername("root");
        info.setPassword("Heihuli123");
        boolean b = SshUtil.checkConnection(info.getUsername(), info.getPassword(), info.getHost(), info.getPort());
        System.out.println(b);
        // true

        SshUtil.ExecResult execResult = SshUtil.execCommand("mkdir /heihuli", info);
        System.out.println(execResult);
        // ExecResult{stdout='', stderr='', exitCode=0}
    }
}
