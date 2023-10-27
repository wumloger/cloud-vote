package top.wml.resp;

import lombok.Data;

@Data
public class CommonResp {
    private String Code;

    private String msg;

    private Object data;

    public static CommonResp  success(Object data){
        CommonResp commonResp = new CommonResp();
        commonResp.setCode("200");
        commonResp.setData(data);
        return commonResp;
    }

    public static CommonResp  fail(String msg){
        CommonResp commonResp = new CommonResp();
        commonResp.setCode("500");
        commonResp.setMsg(msg);
        return commonResp;
    }
}
