package com.sourjelly.likebasketball.goods;

import com.sourjelly.likebasketball.common.global.CustomException;
import com.sourjelly.likebasketball.common.global.ErrorCode;
import com.sourjelly.likebasketball.goods.dto.DetailGoods;
import com.sourjelly.likebasketball.goods.dto.ShowGoods;
import com.sourjelly.likebasketball.goods.service.GoodsService;
import com.sourjelly.likebasketball.user.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/goods")
    public String goodsView(Model model){

        List<ShowGoods> showGoodsList = goodsService.findGoodsByCreatedDesc();
        model.addAttribute("goodsList", showGoodsList);
        return "goods/goods";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String detailView(
            @PathVariable long goodsId
            , Model model
            , HttpSession session){


        DetailGoods detailGoods = goodsService.findGoodsByGoodsId(goodsId);
        model.addAttribute("detailGoods", detailGoods);
        User user = (User)session.getAttribute("user");
        if(user == null){
            return "redirect:/main";
        }


        return "/goods/goodsDetail";
    }

    @GetMapping("/goods/form")
    public String formView(){
        return "/goods/goodsForm";
    }


    @GetMapping("/chat")
    public String chat(){
        return "/chat/chat";
    }


}
