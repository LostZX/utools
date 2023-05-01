package seeyon;

import core.Encoding;
import core.imp.Payload;
import util.http.ReqParameter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Properties;
import com.seeyon.ctp.util.TextEncoder;

public class SeeyonUtil {


    public static String decode(String encpass) {
        return TextEncoder.decode(encpass);
    }

    public static Properties getSeeyonConf(String path, Encoding encoding, Payload payload) throws IOException {
        ReqParameter parameter = new ReqParameter();
        parameter.add("fileName", encoding.Encoding(path));
        byte[] result = payload.evalFunc(null, "readFile", parameter);
        Properties properties = new Properties();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result);
        properties.load(byteArrayInputStream);
        return properties;
    }

}
