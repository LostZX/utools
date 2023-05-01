package ssh;

import com.jcraft.jsch.*;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SSHClient{
    public String hostname;

    public String username;
    public String password;

    public Session session;
    public Channel channel;
    public int port;


    public SSHClient(HashMap<String, String> conn){
        this(conn.get("host"), conn.get("username"), conn.get("password"), Integer.parseInt(conn.get("port")));
    }

    public SSHClient(String hostname, String username, String password) {
        this(hostname, username, password, 22);
    }


    public SSHClient(String hostname, String username, String password, int port) {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.port = port;
    }


    public String execute(String command) throws JSchException, IOException {
        JSch jSch= new JSch();
        this.session = jSch.getSession(username, hostname, port);
        this.session.setPassword(password);
        this.session.setConfig("StrictHostKeyChecking", "no");
        this.session.connect();

        this.channel = session.openChannel("exec");
        ((ChannelExec)channel).setCommand(command);
        channel.setInputStream(null);
        ((ChannelExec)channel).setErrStream(System.err);
        InputStream in = channel.getInputStream();
        channel.connect();
        byte[] buffer = new byte[1024];
        StringBuilder output = new StringBuilder();
        while (true) {
            int len = in.read(buffer);
            if (len < 0) break;
            output.append(new String(buffer, 0, len, StandardCharsets.UTF_8));
        }

        this.close();
        return output.toString();
    }

    private void close(){
        this.channel.disconnect();
        this.session.disconnect();
    }

    public static HashMap<String, String> parserUrl(String sshConnection) {
        String username = null;
        String host = null;
        String port = null;
        String password = null;
        HashMap<String, String> sshInfo = new HashMap<>();

        // Find the first occurrence of "@".
        int firstAtIdx = sshConnection.indexOf("@");
        if (firstAtIdx == -1) {
            return sshInfo;
        }

        username = sshConnection.substring(0, firstAtIdx);

        int firstColonIdx = sshConnection.indexOf(":");
        if (firstColonIdx == -1) {
            int secondAtIdx = sshConnection.indexOf("@", firstAtIdx + 1);
            if (secondAtIdx == -1) {
                return sshInfo;
            }
            host = sshConnection.substring(firstAtIdx + 1, secondAtIdx);
            password = sshConnection.substring(secondAtIdx + 1);
        } else {
            host = sshConnection.substring(firstAtIdx + 1, firstColonIdx);

            int secondAtIdx = sshConnection.indexOf("@", firstAtIdx + 1);
            if (secondAtIdx == -1) {
                return sshInfo;
            }
            port = sshConnection.substring(firstColonIdx + 1, secondAtIdx);
            password = sshConnection.substring(secondAtIdx + 1);
        }

        if (port == null){
            port = "22";
        }
        sshInfo.put("username", username);
        sshInfo.put("host", host);
        sshInfo.put("port", port);
        sshInfo.put("password", password);
        return sshInfo;
    }

    public static void main(String[] args) throws JSchException, IOException {
        SSHClient sshClient = new SSHClient("124.223.103.77","root","ding8046552@");
        sshClient.execute("ifconfig");
    }
}
