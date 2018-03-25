package com.promptnow.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class UtilShellCmd {

    public static enum SHELL_CMD {
        check_su_binary(new String[] { "/system/xbin/which", "su" });

        String[] command;

        SHELL_CMD(String[] command) {
            this.command = command;
        }
    }

    public ArrayList<String> executeCommand(SHELL_CMD shellCmd) {
        String line = null;
        ArrayList<String> fullResponse = new ArrayList<String>();
        Process localProcess = null;
        try {
            localProcess = Runtime.getRuntime().exec(shellCmd.command);
        } catch (Exception e) {
            return null;
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                localProcess.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(
                localProcess.getInputStream()));
        try {
            while ((line = in.readLine()) != null) {
                UtilLog.d("--> Line received: " + line);
                fullResponse.add(line);
            }
        } catch (Exception e) {
            UtilLog.e(UtilLog.getStackTraceString(e));
        }
        
        try {
	        out.close();
	        in.close();
        } catch (Exception e) {
        	UtilLog.e(UtilLog.getStackTraceString(e));
        }
        UtilLog.d("--> Full response was: " + fullResponse);
        return fullResponse;
    }
}
