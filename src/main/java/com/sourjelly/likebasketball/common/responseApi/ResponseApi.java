package com.sourjelly.likebasketball.common.responseApi;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.bridge.IMessage;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseApi<T> {

    private String result;
    private String message;

    private T data;

    public static <T> ResponseApi<T> success(String message){
        return new ResponseApi<T>("success", message, null);
    }

    public static <T> ResponseApi<T> success(String message, T data){
        return new ResponseApi<T>("success", message, data);
    }

    public static <T> ResponseApi<T> fail(String message){
        return new ResponseApi<T>("fail", message, null);
    }

    public static <T> ResponseApi<T> fail(String message, T data){
        return new ResponseApi<>("fail", message, data);
    }
}
