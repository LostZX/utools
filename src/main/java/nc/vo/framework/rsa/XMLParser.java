package nc.vo.framework.rsa;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {

    public static HashMap<String, String> parserXMLForDBConfig(byte[] xml) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xml));

        Element root = doc.getDocumentElement();
        NodeList dataSourceNodes = root.getElementsByTagName("dataSource");

        HashMap<String, String> dbconfig = new HashMap<>();

        for (int i = 0; i < dataSourceNodes.getLength(); i++) {
            Node dataSourceNode = dataSourceNodes.item(i);
            if (dataSourceNode.getNodeType() == Node.ELEMENT_NODE) {
                Element dataSourceElement = (Element) dataSourceNode;
                String databaseUrl = dataSourceElement.getElementsByTagName("databaseUrl").item(0).getTextContent();
                String user = dataSourceElement.getElementsByTagName("user").item(0).getTextContent();
                String password = dataSourceElement.getElementsByTagName("password").item(0).getTextContent();

                dbconfig.put("databaseUrl", databaseUrl);
                dbconfig.put("user", user);
                dbconfig.put("password", password);
            }
        }
        return dbconfig;
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String xml = "<?xml version=\"1.0\" encoding='gb2312'?>\n" +
                "\t<root>\n" +
                "\t\t<enableHotDeploy>false</enableHotDeploy>\n" +
                "\t\t<domain>\n" +
                "\t\t\t<server>\n" +
                "\t\t\t\t<javaHome>$JAVA_HOME</javaHome>\n" +
                "\t\t\t\t<name>server</name>\n" +
                "\t\t\t\t<jvmArgs>-server -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=7194 -server -Xms5g -Xmx20g -XX:PermSize=128m -XX:MaxPermSize=512m -Djava.awt.headless=true -Dfile.encoding=GBK -Dnc.resultset.max=2000000 -Duser.timezone=GMT+02:00</jvmArgs>\n" +
                "\t\t\t\t<servicePort>8005</servicePort>\n" +
                "\t\t\t\t<http>\n" +
                "\t\t\t\t\t<address>172.16.129.248</address>\n" +
                "\t\t\t\t\t<port>8866</port>\n" +
                "\t\t\t\t</http>\n" +
                "\t\t\t\t<keystoreFile>./bin/yonyouserver.jks</keystoreFile>\n" +
                "\t\t\t\t<keystorePass>\n" +
                "\t\t\t\t</keystorePass>\n" +
                "\t\t\t\t<enableAio>false</enableAio>\n" +
                "\t\t\t</server>\n" +
                "\t\t</domain>\n" +
                "\t\t<isEncode>true</isEncode>\n" +
                "\t\t<internalServiceArray>\n" +
                "\t\t\t<name>StartTomcat</name>\n" +
                "\t\t\t<serviceClassName>nc.bs.tomcat.startup.BootStrapTomcatService</serviceClassName>\n" +
                "\t\t\t<accessDemandRight>15</accessDemandRight>\n" +
                "\t\t\t<startService>true</startService>\n" +
                "\t\t\t<keyService>false</keyService>\n" +
                "\t\t\t<serviceOptions>start|stop</serviceOptions>\n" +
                "\t\t</internalServiceArray>\n" +
                "\t\t<internalServiceArray>\n" +
                "\t\t\t<name>EJB_SERVICE</name>\n" +
                "\t\t\t<serviceClassName>nc.bs.mw.naming.EJBContainerService</serviceClassName>\n" +
                "\t\t\t<accessDemandRight>15</accessDemandRight>\n" +
                "\t\t\t<startService>true</startService>\n" +
                "\t\t\t<keyService>false</keyService>\n" +
                "\t\t\t<serviceOptions>start|stop</serviceOptions>\n" +
                "\t\t</internalServiceArray>\n" +
                "\t\t<TransactionManagerProxyClass>uap.mw.trans.UAPTransactionManagerProxy</TransactionManagerProxyClass>\n" +
                "\t\t<UserTransactionClass>uap.mw.trans.UAPUserTransanction</UserTransactionClass>\n" +
                "\t\t<TransactionManagerClass>uap.mw.trans.UAPTransactionManager</TransactionManagerClass>\n" +
                "\t\t<SqlDebugSetClass>nc.bs.mw.sql.UFSqlObject</SqlDebugSetClass>\n" +
                "\t\t<XADataSourceClass>uap.mw.ds.UAPDataSource</XADataSourceClass>\n" +
                "\t\t<dataSource>\n" +
                "\t\t\t<dataSourceName>design</dataSourceName>\n" +
                "\t\t\t<oidMark>MZ</oidMark>\n" +
                "\t\t\t<databaseUrl>jdbc:oracle:thin:@172.16.129.247:1521/xgl</databaseUrl>\n" +
                "\t\t\t<user>nc65</user>\n" +
                "\t\t\t<password>jlehfdffcfmohiag</password>\n" +
                "\t\t\t<driverClassName>oracle.jdbc.OracleDriver</driverClassName>\n" +
                "\t\t\t<databaseType>ORACLE11G</databaseType>\n" +
                "\t\t\t<maxCon>50</maxCon>\n" +
                "\t\t\t<minCon>1</minCon>\n" +
                "\t\t\t<dataSourceClassName>nc.bs.mw.ejb.xares.IerpDataSource</dataSourceClassName>\n" +
                "\t\t\t<xaDataSourceClassName>nc.bs.mw.ejb.xares.IerpXADataSource</xaDataSourceClassName>\n" +
                "\t\t\t<conIncrement>0</conIncrement>\n" +
                "\t\t\t<conInUse>0</conInUse>\n" +
                "\t\t\t<conIdle>0</conIdle>\n" +
                "\t\t\t<dualFlag>false</dualFlag>\n" +
                "\t\t\t<checkConn>false</checkConn>\n" +
                "\t\t\t<desAllConn>false</desAllConn>\n" +
                "\t\t\t<checkTime>300</checkTime>\n" +
                "\t\t\t<preSql>\n" +
                "\t\t\t</preSql>\n" +
                "\t\t\t<preStateNum>5</preStateNum>\n" +
                "\t\t</dataSource>\n" +
                "\t\t<fdbPath>fdb</fdbPath>\n" +
                "\t\t<tokenSeed>7efb6671d8d885fe030a6ae3363d1192</tokenSeed>\n" +
                "\t\t<priviledgedToken>31ca8a6e0414634a7a13be7d1e58d1f4</priviledgedToken>\n" +
                "\t\t<isTraditionalDeploy>false</isTraditionalDeploy>\n" +
                "\t\t<isTokenBindIP>false</isTokenBindIP>\n" +
                "\t\t<securityDataSource>design</securityDataSource>\n" +
                "\t\t<maxConcurrentTimes>0</maxConcurrentTimes>\n" +
                "\t\t<overTime>0</overTime>\n" +
                "\t\t<usefulTime>0</usefulTime>\n" +
                "\t</root>";

        byte[] xmlBytes = xml.getBytes();

        HashMap<String, String> map = XMLParser.parserXMLForDBConfig(xmlBytes);
        System.out.println(map);
    }
}
