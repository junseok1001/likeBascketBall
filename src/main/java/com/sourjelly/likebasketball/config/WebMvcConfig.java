package com.sourjelly.likebasketball.config;

import com.sourjelly.likebasketball.common.fileManger.FileManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file://" + FileManger.FILE_UPLOAD_PATH)
                .addResourceLocations("file://" + FileManger.PROFILE_UPLOAD_PATH)
                .addResourceLocations("file://" + FileManger.GOODS_MAINPHOTO_PATH)
                .addResourceLocations("file://" + FileManger.GOODS_DETAILPHOTO_PATH);
    }

}
