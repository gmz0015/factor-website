package org.noah.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.noah.entity.*;
import org.noah.enums.CommonError;
import org.noah.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/factor")
public class FactorController {
    @Value("${image.save-path}")
    private String imageSavePath;

    @Autowired
    private UserService userService;

    @Autowired
    private FactorService factorService;

    @Autowired
    private FactorLogService factorLogService;

    @Autowired
    private FactorDataService factorDataService;

    @Autowired
    private FactorDataLogService factorDataLogService;

    @Autowired
    private FactorModelService factorModelService;

    @PostMapping("/list")
    public ResponseEntity list(@RequestBody PageEntity page) {
        log.info("Get Factor List. PageEntity={}", page);
        IPage<FactorEntity> iPage = factorService.getListByPage(page);
        return ResponseEntity.success(iPage);
    }

    @PostMapping("/list/rank")
    public ResponseEntity listRank(@RequestBody PageEntity page) {
        log.info("Get Factor Log Rank List. PageEntity={}", page);
        List<FactorLogEntity> factorLogEntities = factorLogService.getList();
        log.info("factorLogEntities:{}", factorLogEntities);
        for (FactorLogEntity factorLogEntity : factorLogEntities) {
            UserEntity userEntity = userService.getById(factorLogEntity.getUserId());
            factorLogEntity.setUsername(userEntity.getUsername());
        }
        return ResponseEntity.success(factorLogEntities);
    }

    @PostMapping("/list/log")
    public ResponseEntity listLog(@RequestBody PageEntity page) {
        log.info("Get Factor Log List. PageEntity={}", page);
        IPage<FactorLogEntity> iPage = factorLogService.getListByPage(page);
        List<FactorLogEntity> factorLogEntities = iPage.getRecords();

        log.info("factorLogEntities:{}", factorLogEntities);
        for (FactorLogEntity factorLogEntity : factorLogEntities) {
            List<Long> factorDataIdList = factorDataLogService.getDataIdByLogId(factorLogEntity.getId());
            List<FactorDataEntity> factorDataEntityList = factorDataService.listByIds(factorDataIdList);
            factorLogEntity.setDatas(factorDataEntityList);

            UserEntity userEntity = userService.getById(factorLogEntity.getUserId());
            factorLogEntity.setUsername(userEntity.getUsername());
        }
        iPage.setRecords(factorLogEntities);
        return ResponseEntity.success(iPage);
    }

    @GetMapping("/info")
    public ResponseEntity info(@RequestParam Long id) {
        log.info("Get Factor info. ID={}", id);
        FactorEntity factorEntity = factorService.getById(id);
        return ResponseEntity.success(factorEntity);
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody FactorEntity factorEntity) {
        log.info("Add Factor info. FactorEntity={}", factorEntity);
        factorEntity.setCreateTime(new Date());
        factorEntity.setUpdateTime(new Date());
        boolean result = factorService.save(factorEntity);
        return ResponseEntity.success(factorEntity);
    }

    @PostMapping("/add/log/text")
    public ResponseEntity addLogText(@RequestBody List<String> texts) {
        log.info("Add Factor Text Log. length={}", texts.size());
        Subject subject = SecurityUtils.getSubject();
        AuthEntity authEntity = (AuthEntity) subject.getPrincipal();
        if (authEntity == null) {
            return ResponseEntity.fail(CommonError.LOGIN_LOST);
        }else {
            UserEntity userEntity = (UserEntity) authEntity.getAuth();

            Random random = new Random();
            Double onlineTime = (double) random.nextInt(500) / 10;

            FactorLogEntity factorLogEntity = new FactorLogEntity();
            factorLogEntity.setTime(new Date());
            factorLogEntity.setUserId(userEntity.getId());
            factorLogEntity.setOnlineTime(onlineTime);
            factorLogService.save(factorLogEntity);

            for (String text : texts) {
                FactorDataEntity factorDataEntity = new FactorDataEntity();
                factorDataEntity.setContent(text);
                factorDataEntity.setType(1);
                factorDataService.save(factorDataEntity);

                // 保存映射关系
                FactorDataLogEntity factorDataLogEntity = new FactorDataLogEntity();
                factorDataLogEntity.setLogId(factorLogEntity.getId());
                factorDataLogEntity.setDataId(factorDataEntity.getId());
                factorDataLogService.save(factorDataLogEntity);
            }

            // 调用模型获取分数
//            ModelRequestEntity modelRequestEntity = new ModelRequestEntity();
//            modelRequestEntity.setText(texts);
//            modelRequestEntity.setOnlineTime(onlineTime);
//            modelRequestEntity.setImg(new ArrayList<>());
//            log.info(">>>>>Model Request: {}", modelRequestEntity);
//            ModelResponseEntity modelResponseEntity = factorModelService.getScore(modelRequestEntity);
//            log.info("<<<<<Model Response: {}", modelResponseEntity);
//
//            factorLogEntity.setScore(modelResponseEntity.getScore());
            factorLogEntity.setScore((double) random.nextInt(500) / 10);
            factorLogService.updateById(factorLogEntity);

            return ResponseEntity.success();
        }
    }

    @PostMapping("/add/log/image")
    public ResponseEntity addLogImage(@RequestParam MultipartFile[] multipartFiles, @RequestParam Integer type) throws IOException {
        log.info("Add Factor Image Log. type={}, length={}", type, multipartFiles.length);
        Subject subject = SecurityUtils.getSubject();
        AuthEntity authEntity = (AuthEntity) subject.getPrincipal();
        if (authEntity == null) {
            return ResponseEntity.fail(CommonError.LOGIN_LOST);
        }else {
            UserEntity userEntity = (UserEntity) authEntity.getAuth();

            Random random = new Random();
            Double onlineTime = (double) random.nextInt(500) / 10;

            FactorLogEntity factorLogEntity = new FactorLogEntity();
            factorLogEntity.setTime(new Date());
            factorLogEntity.setUserId(userEntity.getId());
            factorLogEntity.setOnlineTime(onlineTime);
            factorLogService.save(factorLogEntity);

            List<String> imgs = new ArrayList<>();
            //获取文件名
            for (MultipartFile multipartFile : multipartFiles) {
                String fileName = multipartFile.getOriginalFilename();
                //获取文件后缀名。也可以在这里添加判断语句，规定特定格式的图片才能上传，否则拒绝保存。
                String suffixName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
                if (suffixName.endsWith("jpg") || suffixName.endsWith("jpeg") || suffixName.endsWith("png")) {
                    //为了避免发生图片替换，这里使用了文件名重新生成
                    fileName = UUID.randomUUID() + suffixName;
                    fileName = fileName.replace("-", "");

                    File destDir = new File(imageSavePath);
                    if (!destDir.exists()) {
                        destDir.mkdirs();
                    }
                    File uploadFile = new File(destDir.getAbsolutePath(), fileName);
                    log.info("Save file path={}", uploadFile.getAbsolutePath());
                    byte[] bytes = multipartFile.getBytes();
                    // 将图片保存到文件夹里
                    multipartFile.transferTo(uploadFile);

                    // 获取图片Base64编码
                    String base64Str = Base64.getEncoder().encodeToString(bytes);
                    imgs.add(base64Str);

                    FactorDataEntity factorDataEntity = new FactorDataEntity();
                    factorDataEntity.setImage(bytes);
                    factorDataEntity.setType(0);
                    factorDataService.save(factorDataEntity);

                    // 保存映射关系
                    FactorDataLogEntity factorDataLogEntity = new FactorDataLogEntity();
                    factorDataLogEntity.setLogId(factorLogEntity.getId());
                    factorDataLogEntity.setDataId(factorDataEntity.getId());
                    factorDataLogService.save(factorDataLogEntity);
                }else {
                    return ResponseEntity.fail(CommonError.FILE_EXTENSION_ERROR);
                }
            }

            // 调用模型获取分数
//            ModelRequestEntity modelRequestEntity = new ModelRequestEntity();
//            modelRequestEntity.setText(new ArrayList<>());
//            modelRequestEntity.setImg(imgs);
//            modelRequestEntity.setOnlineTime(onlineTime);
//            log.info(">>>>>Model Request: {}", modelRequestEntity);
//            ModelResponseEntity modelResponseEntity = factorModelService.getScore(modelRequestEntity);
//            log.info("<<<<<Model Response: {}", modelResponseEntity);
//
//            factorLogEntity.setScore(modelResponseEntity.getScore());
            factorLogEntity.setScore((double) random.nextInt(500) / 10);
            factorLogService.updateById(factorLogEntity);

            return ResponseEntity.success(true);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity edit(@RequestBody FactorEntity factorEntity) {
        log.info("Edit Factor info. FactorEntity={}", factorEntity);
        boolean result = factorService.updateById(factorEntity);
        return ResponseEntity.success(result);
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestParam Long id) {
        log.info("Delete Factor. ID={}", id);

        boolean result = factorService.removeById(id);

        return ResponseEntity.success(result);
    }

    @PostMapping("/delete/log")
    public ResponseEntity deleteLog(@RequestParam Long id) {
        log.info("Delete Factor Log. ID={}", id);

        boolean result = factorLogService.removeById(id);

        return ResponseEntity.success(result);
    }
}
