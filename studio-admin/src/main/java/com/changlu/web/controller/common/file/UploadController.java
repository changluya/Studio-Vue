package com.changlu.web.controller.common.file;

import com.changlu.common.domain.ResponseResult;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.exception.ServiceCallException;
import com.changlu.common.utils.JsonObjectUtil;
import com.changlu.common.utils.sm2.SM2AnnoationUtil;
import com.changlu.enums.ConfigTypeEnum;
import com.changlu.service.UploadService;
import com.changlu.utils.file.FileUpload;
import com.changlu.vo.config.ConfigVo;
import com.changlu.web.env.EnvironmentContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UploadTestController
 * @Author ChangLu
 * @Date 5/6/2022 4:49 PM
 * @Description 通用上传工具控制器
 */
@RestController
@Slf4j
@RequestMapping("/api/zf/api")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private EnvironmentContext env;

    /**
     * 测试指定上传模式的连通性
     * @param configVo
     * @return
     */
    @PostMapping("/testConn")
    public ResponseResult testConn(@RequestBody ConfigVo configVo) {
        if (configVo != null) {
            // 将json串转为指定的配置类
            String json = JsonObjectUtil.transferObjectToJson(configVo.getConfigValue());
            Class clazz = ConfigTypeEnum.getConfigTypeEnum(configVo.getConfigKey()).getPojoClazz();
            configVo.setConfigValue(JsonObjectUtil.transferJsonToObject(json, clazz));
            // 统一对配置类包含有sm2注解属性字段解密（其中的对象应当是指定的包含有枚举类的配置类类型）
            SM2AnnoationUtil.deserializerSM2(configVo.getConfigValue(), env.getSM2PrivateKey(), env.getSM2PublicKey());
        }
        boolean res = uploadService.testConn(false, configVo);
        if (res) {
            return ResponseResult.success("测试连通成功！");
        }else {
            return ResponseResult.error("测试连通失败，请检查配置！");
        }
    }


    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public ResponseResult uploadFile(@RequestParam("file") MultipartFile file){
        FileUpload fileUpload = uploadService.getDefaultFileUpload();
        Map<String,String> visitUrl = null;
        try {
            visitUrl = fileUpload.uploadFile(file);
        }catch (ServiceCallException e){
            throw new ServiceException("上传失败！",e);
        }
        return ResponseResult.success("上传成功！", visitUrl);
    }

    /**
     * 上传多张图片
     * @param files
     * @return
     */
    @PostMapping("/upload/files")
    public ResponseResult uploadFiles(@RequestParam("file")MultipartFile[] files){
        FileUpload fileUpload = uploadService.getDefaultFileUpload();
        List<Map<String,String>> visitUrls = new ArrayList<>(files.length);
        try {
            for (MultipartFile file : files) {
                Map<String,String> visitUrl = fileUpload.uploadFile(file);
                visitUrls.add(visitUrl);
            }
        }catch (ServiceCallException e){
            throw new ServiceException("上传失败！",e);
        }
        return ResponseResult.success("上传成功！", visitUrls);
    }

    /**
     * 删除图片
     * @param fileName 文件名称如：b8809d28-82ec-4b06-af5f-8d3d7a16107c.jpg
     * @return
     */
    @DeleteMapping("/deleFile/{fileName}")
    public ResponseResult deleteFile(@PathVariable("fileName")String fileName){
        FileUpload fileUpload = uploadService.getDefaultFileUpload();
        //若是url地址，那么进行筛选
        if (!ObjectUtils.isEmpty(fileName)) {
            try {
                fileUpload.deleteFile(fileName);
            } catch (ServiceCallException e) {
                throw new ServiceException("删除失败！",e);
            }
        }
        return ResponseResult.success("删除成功！", null);
    }

}
