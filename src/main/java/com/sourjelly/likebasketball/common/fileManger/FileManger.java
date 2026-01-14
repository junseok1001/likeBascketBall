package com.sourjelly.likebasketball.common.fileManger;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManger {

    public final static String FILE_UPLOAD_PATH="D:\\webInventor\\personalproject\\project\\upload";

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
        File filePart = new File(filePath);

        try{
            // 경로를 관리하는 객체 + file write를 같이 내부적으로 해준다.
            file.transferTo(filePart);

//            // 파일경로를 관리하는 객체
//            Path path = Paths.get(filePath);
//            //
//            Files.write(path, bytes);
        }catch(IOException e){
            return null;
        }


        return "/images" + directoryName + "/" + file.getOriginalFilename();
    }

    public static List<String> saveFiles(long userId, List<MultipartFile> files) {
        List<String> fileUrls = new ArrayList<>();
        if (files == null || files.isEmpty()) {
            return fileUrls;
        }

        for (MultipartFile file : files) {
            String url = saveFile(userId, file);
            if (url != null) {
                fileUrls.add(url);
            }
        }
        return fileUrls;
    }

}
