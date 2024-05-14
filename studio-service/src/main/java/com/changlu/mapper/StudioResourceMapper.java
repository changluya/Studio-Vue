package com.changlu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changlu.system.pojo.StudioResourceModel;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface StudioResourceMapper extends BaseMapper<StudioResourceModel> {


    int deleteResources(@Param("resFlag")String resFlag,@Param("tableIds") Long[] tableIds);

}
