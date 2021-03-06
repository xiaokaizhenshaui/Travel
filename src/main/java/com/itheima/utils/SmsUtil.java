package com.itheima.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class SmsUtil {

    static final String product = "Dysmsapi";//产品名称:云通信短信API产品,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";//产品域名,开发者无需替换
    static String accessKeyId = "LTAIMOat5S7QCTgy";//需要替换成自己申请的accessKeyId
    static String accessKeySecret = "NrH778cgJlSUYbe73R5QTOvP5NQCpT";//需要替换成自己申请的accessKeySecret


    /**
     * 发送短信
     *
     * @param phoneNumbers 手机号
     * @param code         验证码
     */
    public static void sendSms(String phoneNumbers, String code) {
        //sendSms(phoneNumbers, , , "{\"code\":\"" + code + "\"}");
    }

    /**
     * 发送短信
     *
     * @param phoneNumbers 要发送短信到哪个手机号
     */
    public static String sendSms(String phoneNumbers) {
        try {
            //可自助调整超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");

            //初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            //必填:待发送手机号
            request.setPhoneNumbers(phoneNumbers);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName("黑马旅游注册");
            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode("SMS_166867422");
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            String tempCode = createCode();
            request.setTemplateParam("{\"code\":\""+tempCode+"\"}");

            //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCode("90997");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            request.setOutId("yourOutId");

            //hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            if (!"OK".equals(sendSmsResponse.getCode())) {
                throw new RuntimeException(sendSmsResponse.getMessage());
            }
            return tempCode;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("发送短信失败");
        }
    }

    public static String createCode(){
        String code = "";
        Random random = new Random();
        code+=random.nextInt(9)+1;
        for (int i = 0; i < 5; i++) {
            code += random.nextInt(10);
        }
        return code;
    }

    public static void main(String[] args) {
        sendSms("18123603329");
    }
}
