package com.sourjelly.likebasketball.common.fileManger;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManger {

    public final static String FILE_UPLOAD_PATH="D:\\webInventor\\personalproject\\project";

    public static String saveFile(long userId, MultipartFile file){

        if(file == null){
            return null;
        }

        String directoryName = "/" + userId + "_" + System.currentTimeMillis();

        String directoryPath = FILE_UPLOAD_PATH + directoryName;

        File directory = new File(directoryPath);

        if (!directory.mkdir()) {
            // 파일 생성 실패
            return null;
        }

        String filePath = directoryPath + "/" + file.getOriginalFilename();

        try{

            byte[] bytes = file.getBytes();

            // 파일경로를 관리하는 객체
            Path path = Paths.get(filePath);
            //
            Files.write(path, bytes);
        }catch(IOException e){
            return null;
        }


        return "/images" + directoryName + "/" + file.getOriginalFilename();
    }
}
