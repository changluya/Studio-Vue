package com.changlu.service;

import com.changlu.utils.file.FileUpload;
import com.changlu.vo.config.ConfigVo;

public interface UploadService {

    FileUpload getFileUpload(String configKey);

    FileUpload getDefaultFileUpload();

    boolean testConn(boolean isUseDefault, ConfigVo configVo);
}
