package nc.vo.framework.rsa;

import core.Encoding;
import core.imp.Payload;
import org.xml.sax.SAXException;
import util.http.ReqParameter;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class NCUtil {

    public static String decode(String s){
        return new Encode().decode(s);
    }

    public static HashMap<String, String> getNCConfig(String path, Encoding encoding, Payload payload) throws ParserConfigurationException, IOException, SAXException {
        ReqParameter parameter = new ReqParameter();
        parameter.add("fileName", encoding.Encoding(path));
        byte[] result = payload.evalFunc(null, "readFile", parameter);
        return XMLParser.parserXMLForDBConfig(result);
    }

}
