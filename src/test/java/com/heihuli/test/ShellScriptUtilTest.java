package com.heihuli.test;

import com.heihuli.util.ShellScriptUtil;
import org.junit.Test;

/**
 * @author heihuli
 */
public class ShellScriptUtilTest {

    /**
     * ShellScriptUtil.executeScript
     */
    @Test
    public void test() {
        ShellScriptUtil.ScriptExecutorContext context = new ShellScriptUtil.ScriptExecutorContext();
        context.setCommand("src/test/java/com/heihuli/file/test.sh");
        context.setParameters("黑狐狸一号", "黑狐狸二号", "黑狐狸三号");

        int i = ShellScriptUtil.executeScript(context, null);
        System.out.println(i);
    }
}
