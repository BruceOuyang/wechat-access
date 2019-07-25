package club.bugmakers.wechat_access.controller;

import club.bugmakers.wechat_access.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * AccessController
 *
 * @Author Bruce
 * @Date 2019/7/24 14:53
 * @Version 1.0
 **/
@RestController
@Slf4j
public class AccessController {

    /**
     * 微信服务接入验证接口
     * @param signature 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param echostr   随机字符串
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr, HttpServletRequest request) {

        log.info("");
        log.info("request: ");
        log.info("-----------------------------------------");
        log.info("from ip: {} - user: {} - host: {} - port: {}", request.getRemoteAddr(), request.getRemoteUser(), request.getRemoteHost(), request.getRemotePort());
        log.info("signature: {}", signature);
        log.info("timestamp: {}", timestamp);
        log.info("nonce: {}", nonce);
        log.info("echostr: {}", echostr);
        log.info("=========================================");

        String response = null;
        if (SignUtils.checkSignature(signature, timestamp, nonce)) {
            response = echostr;
        } else {
            response = "invalid";
        }

        log.info("response: {}", response);
        log.info("=========================================");


        return response;
    }
}
