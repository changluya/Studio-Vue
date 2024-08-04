package com.changlu.utils.file;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.changlu.common.config.file.AliyunOssConfig;
import com.changlu.common.exception.ServiceCallException;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.utils.JsonObjectUtil;
import com.changlu.enums.ConfigTypeEnum;
import com.changlu.service.SiteConfigService;
import com.changlu.vo.config.ConfigVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName AliyunOssUtil
 * @Author ChangLu
 * @Date 3/26/2022 4:51 PM
 * @Description OSS对象存储工具类
 */
@Component
@Slf4j
public class AliyunOssUtil extends AbsUploadExecutor {

    @Autowired
    private SiteConfigService siteConfigService;

    /**
     * 创建一个OSS连接
     * @return OssClient
     */
    private OSS createOssClient(AliyunOssConfig aliyunOssConfig){
        // 尝试构造OSS Client
        String endpoint = "http://" + aliyunOssConfig.getEndpoint();
        String accessKeyId = aliyunOssConfig.getAccessKeyId();
        String accessKeySecret = aliyunOssConfig.getAccessKeySecret();
        OSS oss = null;
        try {
            oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        }catch (Exception e) {
            log.error("createOssClient error", e);
        }
        return oss;
    }

    //构建上传配置类
    public AliyunOssConfig buildUploadConfig(){
        //取得指定key的结果值
        ConfigVo configVo = siteConfigService.selectConfigValueByConfigKey(ConfigTypeEnum.SITE_UPLOAD_OSS.getConfigKey());
        Object configValue = configVo.getConfigValue();
        //转为AliyunOssConfig
        AliyunOssConfig aliyunOssConfig = null;
        try {
            if (configValue instanceof AliyunOssConfig){
                aliyunOssConfig = (AliyunOssConfig) configVo.getConfigValue();
            }
        }catch (Exception e){
            log.error("build AliyunOssConfig error!", e);
        }
        return aliyunOssConfig;
    }

    /**
     * 关闭连接
     * @param ossClient
     */
    private void close(OSS ossClient){
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }

    public Map<String,String> uploadFile(File file, AliyunOssConfig aliyunOssConfig){
        // 获取配置参数
        String key = aliyunOssConfig.getKey();
        String bucketName = aliyunOssConfig.getBucketName();
        String endpoint = aliyunOssConfig.getEndpoint();
        //1、取得保存文件路径saveFilePath：拼接目标路径 + 图片名称
        String originFileName = file.getName();
        String filePrefix = UUID.randomUUID().toString();
        String saveFileName = filePrefix + originFileName.substring(file.getName().indexOf("."));
        String saveFilePath = key + saveFileName;
        OSS ossClient = null;
        try {
            ossClient = createOssClient(aliyunOssConfig);
            //2、上传文件
            //第一个参数为：桶的名称；第二个参数为路径+上你的文件名，如test/1.png，若是test/test2/1.png，也会给我们自动创建目录
            //第三个参数为：文件对象
            ossClient.putObject(new PutObjectRequest(bucketName, saveFilePath , file));
        }catch (Exception e){
            log.error("上传阿里云OSS异常!", e);
            return null;
        }finally {
            this.close(ossClient);
        }
        //3、拼接获取公共访问地址
        String publicVisitPath = "http://" + bucketName + '.' +  endpoint + "/" + saveFilePath;
        Map<String,String> result = new HashMap<>(2);
        //4、返回原文件名以及访问地址
        result.put("originName", originFileName);
        result.put("curFileName", saveFileName);
        result.put("visitUrl", publicVisitPath);
        return result;
    }

    /**
     * 上传文件
     * @param file MultipartFile类型
     * @return
     */
    public Map<String,String> uploadFile(MultipartFile file)throws ServiceCallException {
        // 获取OSS的Client配置参数
        AliyunOssConfig aliyunOssConfig = this.buildUploadConfig();
        //获取配置参数
        String key = aliyunOssConfig.getKey();
        String bucketName = aliyunOssConfig.getBucketName();
        String endpoint = aliyunOssConfig.getEndpoint();
        //1、取得保存文件路径saveFilePath：拼接目标路径 + 图片名称
        String originFileName = file.getOriginalFilename();
        String filePrefix = UUID.randomUUID().toString();
        String saveFileName = null;
        //管理系统上传头像时，由于是裁剪之后上传，所以文件名为blob，所以这里不取出文件后缀
        if (originFileName.equals("blob")){
            saveFileName = filePrefix + ".png";
        }else{
            saveFileName = filePrefix + originFileName.substring(originFileName.indexOf("."));
        }
        String saveFilePath = key + saveFileName;
        OSS ossClient = null;
        try {
            ossClient = createOssClient(aliyunOssConfig);
            //2、上传文件
            //第一个参数为：桶的名称；第二个参数为路径+上你的文件名，如test/1.png，若是test/test2/1.png，也会给我们自动创建目录
            //第三个参数为：文件对象
            /**
             * 阿里云OSS 默认图片上传ContentType是image/jpeg
             * 图片链接后，图片是下载链接，而并非在线浏览链接
             * 这里在上传的时候要解决ContentType的问题，将其改为image/jpg
             */
            InputStream is = file.getInputStream();
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType("image/jpg");
            ossClient.putObject(new PutObjectRequest(bucketName, saveFilePath , is, meta));
        }catch (Exception e){
            log.error("上传阿里云OSS异常!", e);
        }finally {
            this.close(ossClient);
        }
        //3、拼接获取公共访问地址
        String publicVisitPath = "http://" + bucketName + '.' +  endpoint + "/" + saveFilePath;
        Map<String,String> result = new HashMap<>(2);
        //4、返回原文件名以及访问地址
        result.put("originName",originFileName);
        result.put("visitUrl",publicVisitPath);
        return result;
    }


    /**
     * 删除文件
     * @param fileName 待删除的文件名
     * @return
     */
    public void deleteFile(String fileName)throws ServiceCallException{
        // 获取OSS的Client配置参数
        AliyunOssConfig aliyunOssConfig = this.buildUploadConfig();
        // 删除文件
        deleteFile(fileName, aliyunOssConfig);
    }

    public void deleteFile(String fileName, AliyunOssConfig aliyunOssConfig) {
        // 获取配置参数
        String key = aliyunOssConfig.getKey();
        String bucketName = aliyunOssConfig.getBucketName();
        //1、拼接待删除路径
        String deleteFilePath = key + fileName;
        OSS ossClient = null;
        try {
            ossClient = createOssClient(aliyunOssConfig);
            //2、删除文件
            ossClient.deleteObject(bucketName, deleteFilePath);
        }catch (Exception e){
            throw new ServiceCallException("删除阿里云OSS文件异常",e);
        }finally {
            this.close(ossClient);
        }
    }

    /**
     * 测试连通性
     * @param defaultConfig 是否使用默认数据源配置
     * @param config 传入的配置项
     * @return
     */
    @Override
    public boolean testConn(boolean defaultConfig, Object config) {
        // 根据枚举类来进行单独转换，当前config可能是LinkedHashMap
        if (config instanceof LinkedHashMap){
            // 序列化为json之后，转为指定Enum的配置类
            String configValue = JSONObject.toJSONString(config);
            // 转为当前阿里OSS数据源的配置类
            config = JsonObjectUtil.transferJsonToObject(configValue, ConfigTypeEnum.SITE_UPLOAD_OSS.getPojoClazz());
        }
        AliyunOssConfig aliyunOssConfig = null;
        // 使用默认数据库中配置项
        if (defaultConfig) {
            aliyunOssConfig = this.buildUploadConfig();
        }else {
            // 若是根据传来的配置文件来进行测试连通性
            if (!(config instanceof AliyunOssConfig)) {
                throw new ServiceException("配置项匹配有误，无法转换！");
            }
            aliyunOssConfig = (AliyunOssConfig) config;
        }
        // 验证是否能够通过测试连通
        try {
            // 创建临时文件
            File tempFile = createTempFile();
            // 上传oss文件
            Map<String, String> result = this.uploadFile(tempFile, aliyunOssConfig);
            String fileName = result.get("curFileName");
            // 删除oss文件
            this.deleteFile(fileName, aliyunOssConfig);
        }catch (Exception ex) {
            log.error("请检查配置参数，测试连通性异常！", ex);
            return false;
        }
        return true;
    }

    // 创建临时文件
    public File createTempFile(){
        byte[] data = "Hello, World!".getBytes();
        InputStream is = new ByteArrayInputStream(data);
        File tempFile = null;
        try {
            tempFile = File.createTempFile("tempFile-" + UUID.randomUUID(), ".txt");
        } catch (IOException e) {
            log.error("createTempFile error！", e);
            return null;
        }
        // 将字节数组写入临时文件中
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[data.length];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }catch (IOException ex) {
            log.error("创建临时文件失败！", ex);
        }
        return tempFile;
    }

}
