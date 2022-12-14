package com.heihuli.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * shell脚本执行工具
 *
 * @author heihuli
 */
public class ShellScriptUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ShellScriptUtil.class);

    public static final long COMMAND_TIMEOUT = 10 * 60 * 1000L;

    /**
     * 执行本地脚本
     * @param context
     * @param timeout
     * @return
     */
    public static int executeScript(ScriptExecutorContext context, Long timeout) {
        CommandLine commandLine = CommandLine.parse(context.getCommand());

        if (context.getParameters() != null && context.getParameters().size() > 0) {
            for (int i = 0; i < context.getParameters().size(); i++)
                commandLine.addArgument(context.getParameters().get(i));
        }

        long start = System.currentTimeMillis();
        int exitCode = -1;
        ByteArrayOutputStream outputStream = null;
        ByteArrayOutputStream errorStream = null;
        try {
            if (timeout == null) {
                timeout = COMMAND_TIMEOUT;
            }
            ExecuteWatchdog executeWatchdog = new ExecuteWatchdog(timeout);
            DefaultExecutor executor = new DefaultExecutor();

            executor.setWatchdog(executeWatchdog);
            executor.setExitValue(0);
            // 本地记录，可以摘除
            outputStream = new ByteArrayOutputStream();
            errorStream = new ByteArrayOutputStream();
            executor.setStreamHandler(new PumpStreamHandler(outputStream, errorStream));

            exitCode = executor.execute(commandLine);
            return exitCode;
        } catch (Throwable t) {
            LOG.error("[ShellScriptUtil] executeScript failed, commandline: {}, params: {}, errMsg: {}", context.getCommand(), context.getParameters(), t);
            return -1;
        } finally {
            long cost = System.currentTimeMillis() - start;
            try {
                LOG.info("[ShellScriptUtil] Execute success, commandline: {}, Finish with exitCode: {}, cost: {} ms", context.getCommand(), exitCode, cost);
                if (outputStream != null && errorStream != null) {
                    LOG.info("[ShellScriptUtil] Execute info: {}. Execute errMsg: {}", outputStream.toString("utf-8"), errorStream.toString("utf-8"));
                }
            } catch (UnsupportedEncodingException e) {
                LOG.error("[ShellScriptUtil] Output log failed", e);
            }
            StreamUtil.closeAll(outputStream, errorStream);
        }
    }

    /**
     * 脚本上下文
     */
    public static class ScriptExecutorContext {
        private String command;
        private List<String> parameters;

        public ScriptExecutorContext() {
            this.parameters = new ArrayList<String>();
        }

        public ScriptExecutorContext(String command, List<String> parameters) {
            this.command = command;
            this.parameters = parameters;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public List<String> getParameters() {
            return parameters;
        }

        public void setParameters(String... params) {
            if (params.length > 0)
                for (String param : params) {
                    this.parameters.add(param);
                }
        }

        public void addParameters(List<String> params) {
            if (params.size() > 0)
                for (String param : params) {
                    this.parameters.add(param);
                }
        }

        @Override
        public String toString() {
            return "ScriptExecutorContext [command=" + command + ", parameters=" + parameters + "]";
        }
    }

}
