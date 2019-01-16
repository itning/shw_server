package top.yunshu.shw.server.cas;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.itning.cas.ICasConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cas配置实现
 *
 * @author itning
 */
@Component
public class CasConfigImpl implements ICasConfig {
    private static final Logger logger = LoggerFactory.getLogger(CasConfigImpl.class);
    private static final String CAS_HEADER = "Authorization";

    @Override
    public Map<String, String> analysisBody2Map(String body) {
        Map<String, String> map = new HashMap<>(16);
        try {
            Document doc = DocumentHelper.parseText(body);
            Node successNode = doc.selectSingleNode("//cas:authenticationSuccess");
            if (successNode != null) {
                List<Node> attributesNode = doc.selectNodes("//cas:attributes/*");
                attributesNode.forEach(defaultElement -> map.put(defaultElement.getName(), defaultElement.getText()));
                logger.debug("Get Map: " + map);
            } else {
                //认证失败
                logger.error("AUTHENTICATION failed : cas:authenticationSuccess Not Found");
            }
        } catch (Exception e) {
            logger.error("AUTHENTICATION failed and Catch Exception: ", e);
        }
        return map;
    }

    @Override
    public boolean isLogin(HttpServletResponse resp, HttpServletRequest req) {
        return StringUtils.isNotBlank(req.getHeader(CAS_HEADER));
    }

    @Override
    public boolean needSetMapSession() {
        return false;
    }
}
