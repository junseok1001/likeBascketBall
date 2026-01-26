package com.sourjelly.likebasketball.common.fileManger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileManger {

    public final static String FILE_UPLOAD_PATH="D:\\webInventor\\personalproject\\project\\upload\\clubDetailPhoto";
    public final static String PROFILE_UPLOAD_PATH="D:\\webInventor\\personalproject\\project\\upload\\clubProfile";
    public final static String GOODS_MAINPHOTO_PATH="D:\\webInventor\\personalproject\\project\\upload\\goodsMainPhoto";
    public final static String GOODS_DETAILPHOTO_PATH="D:\\webInventor\\personalproject\\project\\upload\\goodsDetailPhoto";

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

    public static String saveProfile(long userId, MultipartFile file){

        if(file == null){
            return null;
        }

        String directoryName = "/" + userId + "_" + System.currentTimeMillis();

        String directoryPath = PROFILE_UPLOAD_PATH + directoryName;

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

    public static boolean removeFile(String imagePath){
        // image넣는게 필수가 아니여서
        // image 경로가 null 이라면 false 기능수행 정지
        if(imagePath == null){
            return false;
        }
        //원본 file 이름으로 바꾸기
        String fullFilePath = FILE_UPLOAD_PATH + imagePath.replace("/images", "");

        // 파일 경로를 관리해주는 객체
        Path path = Paths.get(fullFilePath);
        // 해당 directory의 부모 directory 가져오기
        Path directoryPath = path.getParent();
        log.info(directoryPath.toString());

        try {
            Files.delete(path);
            Files.delete(directoryPath);
        } catch (IOException e) {
            return false;
        }

        return true;

    }

    public static boolean removeProfile(String imagePath){
        // image넣는게 필수가 아니여서
        // image 경로가 null 이라면 false 기능수행 정지
        if(imagePath == null){
            return false;
        }
        //원본 file 이름으로 바꾸기
        String fullFilePath = PROFILE_UPLOAD_PATH + imagePath.replace("/images", "");

        // 파일 경로를 관리해주는 객체
        Path path = Paths.get(fullFilePath);
        // 해당 directory의 부모 directory 가져오기
        Path directoryPath = path.getParent();

        try {
            Files.delete(path);
            Files.delete(directoryPath);
        } catch (IOException e) {
            return false;
        }

        return true;
    }


    public static String saveGoodsMain(long userId, MultipartFile file){

        if(file == null){
            return null;
        }

        String directoryName = "/" + userId + "_" + System.currentTimeMillis();

        String directoryPath = GOODS_MAINPHOTO_PATH + directoryName;

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

    public static String saveGoodsDetail(long userId, MultipartFile file){

        if(file == null){
            return null;
        }

        String directoryName = "/" + userId + "_" + System.currentTimeMillis();

        String directoryPath = GOODS_DETAILPHOTO_PATH + directoryName;

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





}
