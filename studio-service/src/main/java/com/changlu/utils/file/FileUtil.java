package com.changlu.utils.file;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.changlu.common.config.file.AliyunOssConfig;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.config.file.LocalUploadConfig;
import com.changlu.common.utils.JsonObjectUtil;
import com.changlu.common.utils.file.FileUtils;
import com.changlu.enums.ConfigTypeEnum;
import com.changlu.service.SiteConfigService;
import com.changlu.vo.config.ConfigVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName FileUploadUtil
 * @Author ChangLu
 * @Date 4/22/2022 9:23 PM
 * @Description 文件工具类
 */
@Component
@Slf4j
public class FileUtil extends AbsUploadExecutor {

    @Autowired
    private SiteConfigService siteConfigService;

    /**
     * 上传资源
     *
     * @return 访问地址
     */
    @Override
    public Map<String,String> uploadFile(MultipartFile multipartFile) {
        LocalUploadConfig localUploadConfig = this.buildUploadConfig();
        String originFileName = multipartFile.getOriginalFilename();//原文件名
        String uuid = UUID.randomUUID().toString();
        String suffix = null;
        if (originFileName.equals("blob")) {
            suffix = ".png";
        }else{
            suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        }
        String newFileName = uuid + suffix;//待保存的文件名
        File uploadFile = new File(localUploadConfig.fileUploadDir);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
        try {
            multipartFile.transferTo(new File(localUploadConfig.fileUploadDir + File.separator + newFileName));
        } catch (IOException e) {
            throw new ServiceException("本地文件上传失败");
        }
        //最终访问路径
        String visitResourcePath = localUploadConfig.protocol + "://" + localUploadConfig.ip + ":" + localUploadConfig.port + localUploadConfig.visitPath + newFileName;
        Map<String,String> result = new HashMap<>(2);
        //4、返回原文件名以及访问地址
        result.put("originName",originFileName);
        result.put("visitUrl",visitResourcePath);
        return result;
    }

    public Map<String,String> uploadFile(File sourceFile, LocalUploadConfig localUploadConfig){
        String originFileName = sourceFile.getName();
        String uuid = UUID.randomUUID().toString();
        String suffix = null;
        if (originFileName.equals("blob")) {
            suffix = ".png";
        }else{
            suffix = originFileName.substring(originFileName.lastIndexOf("."));
        }
        String newFileName = uuid + suffix;//待保存的文件名
        File fileDirectory = new File(localUploadConfig.fileUploadDir);
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
        File targetFile = new File(localUploadConfig.fileUploadDir + File.separator + newFileName);
        try {
            FileUtils.copyFile(sourceFile, targetFile);
        } catch (IOException e) {
            throw new ServiceException("本地文件上传失败");
        }
        //最终访问路径
        String visitResourcePath = localUploadConfig.protocol + "://" + localUploadConfig.ip + ":" + localUploadConfig.port + localUploadConfig.visitPath + newFileName;
        Map<String,String> result = new HashMap<>(2);
        //4、返回原文件名以及访问地址
        result.put("originName",originFileName);
        result.put("curFileName", newFileName);
        result.put("visitUrl",visitResourcePath);
        return result;
    }

    /**
     * 上传多个资源
     * @param files
     * @return
     */
    public List<Map<String,String>> saveFiles(MultipartFile[] files){
        List<Map<String,String>> visitUrls = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            Map<String, String> visitUrl = uploadFile(file);
            visitUrls.add(visitUrl);
        }
        return visitUrls;
    }

    /**
     * 删除资源
     * @param filename 文件名
     * @return
     */
    public void deleteFile(String filename) {
        LocalUploadConfig localUploadConfig = this.buildUploadConfig();
        deleteFile(filename, localUploadConfig);
    }

    /**
     * 删除资源
     * @param filename 文件名
     * @return
     */
    public void deleteFile(String filename, LocalUploadConfig localUploadConfig) {
        //若是带有链接，那么去除链接
        if (filename.contains("/")) {
            filename = filename.substring(filename.lastIndexOf("/") + 1);
        }
        String filePath = localUploadConfig.fileUploadDir + File.separator + filename;
        final File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                log.info("删除图片：{}成功！", file.getName());
                return;
            }
            log.error("删除图片：{}失败！", file.getName());
        }
    }

    @Override
    public boolean testConn(boolean defaultConfig, Object config) {
        LocalUploadConfig localUploadConfig = null;
        // 使用默认数据库中配置项
        if (defaultConfig) {
            localUploadConfig = this.buildUploadConfig();
        }else {
            // 根据枚举类来进行单独转换，当前config可能是LinkedHashMap
            if (config instanceof LinkedHashMap){
                // 序列化为json之后，转为指定Enum的配置类
                String configValue = JSONObject.toJSONString(config);
                // 转为当前阿里OSS数据源的配置类
                config = JsonObjectUtil.transferJsonToObject(configValue, ConfigTypeEnum.SITE_UPLOAD_FILE.getPojoClazz());
            }
            // 若是根据传来的配置文件来进行测试连通性
            if (!(config instanceof LocalUploadConfig)) {
                throw new ServiceException("配置项匹配有误，无法转换！");
            }
            localUploadConfig = (LocalUploadConfig) config;
        }
        // 验证是否能够通过测试连通
        try {
            // 创建临时文件
            File tempFile = FileUtils.createTempFile();
            // 上传oss文件
            Map<String, String> result = this.uploadFile(tempFile, localUploadConfig);
            String fileName = result.get("curFileName");
            // 删除文件
            this.deleteFile(fileName, localUploadConfig);
        }catch (Exception ex) {
            log.error("请检查配置参数，测试连通性异常！", ex);
            return false;
        }
        return true;
    }

    public LocalUploadConfig buildUploadConfig() {
        //取得指定key的结果值
        ConfigVo configVo = siteConfigService.selectConfigValueByConfigKey(ConfigTypeEnum.SITE_UPLOAD_FILE.getConfigKey());
        Object configValue = configVo.getConfigValue();
        //转为LocalUploadConfig
        LocalUploadConfig localUploadConfig = null;
        try {
            if (configValue instanceof LocalUploadConfig){
                localUploadConfig = (LocalUploadConfig) configVo.getConfigValue();
            }
        }catch (Exception e){
            log.error("build LocalUploadConfig error!", e);
        }
        return localUploadConfig;
    }

    /**
     * 删除多个资源
     * @param fileNames
     */
    public void deleteFiles(String[] fileNames) {
        if (!ObjectUtils.isEmpty(fileNames)) {
            for (String fileName : fileNames) {
                deleteFile(fileName);
            }
        }
    }

    /**
     * 删除多个资源
     * @param fileNames
     */
    public void deleteFiles(List<String> fileNames) {
        if (!ObjectUtils.isEmpty(fileNames)) {
            for (String fileName : fileNames) {
                deleteFile(fileName);
            }
        }
    }

}
