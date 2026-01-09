package com.sourjelly.likebasketball.common.responseApi;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseApi<T> {

    private String message;
    private String result;

    private T data;
}
