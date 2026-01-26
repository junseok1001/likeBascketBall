package com.sourjelly.likebasketball.goods;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoodsController {

    @GetMapping("/goods")
    public String goodsView(){
        return "goods/goods";
    }

    @GetMapping("/goods/form")
    public String formView(){
        return "goods/goodsForm";
    }
    @GetMapping("/chat")
    public String chat(){
        return "/chat/chat";
    }


}
