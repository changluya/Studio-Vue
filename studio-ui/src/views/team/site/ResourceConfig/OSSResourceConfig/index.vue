<template>
  <el-form ref="OSSUploadForm" :model="OSSUploadFormData.configValue" size="medium" label-width="140px">
    <el-form-item label="endpoint" prop="endpoint">
      <el-input :disabled="!isEdit" v-model="OSSUploadFormData.configValue.endpoint" placeholder="示例：oss-cn-beijing.aliyuncs.com" clearable :style="{width: '30%'}">
      </el-input>
    </el-form-item>
    <el-form-item label="accessKeyId" prop="accessKeyId">
      <el-input :disabled="!isEdit" v-model="OSSUploadFormData.configValue.accessKeyId" placeholder="请输入OSS申请服务的key" clearable :style="{width: '30%'}">
      </el-input>
    </el-form-item>
    <el-form-item label="accessKeySecret" prop="accessKeySecret">
      <el-input :disabled="!isEdit" v-model="OSSUploadFormData.configValue.accessKeySecret" placeholder="请输入OSS申请服务的secret" clearable :style="{width: '30%'}">
      </el-input>
    </el-form-item>
    <el-form-item label="bucketName" prop="bucketName">
      <el-input :disabled="!isEdit" v-model="OSSUploadFormData.configValue.bucketName" placeholder="请输入桶名称，示例：pictured-bedtest" clearable :style="{width: '30%'}">
      </el-input>
    </el-form-item>
    <el-form-item label="key" prop="key">
      <el-input :disabled="!isEdit" v-model="OSSUploadFormData.configValue.key" placeholder="请输入存储根路径，示例：test/studio" clearable :style="{width: '30%'}">
      </el-input>
    </el-form-item>
    <el-form-item size="large">
      <el-button type="primary" @click="testConn">测试连通性</el-button>
      <el-button type="primary" @click="editOssConfig">编辑</el-button>
      <el-button :disabled="!isEdit" type="primary" @click="saveOssConfig">保存</el-button>
      <el-button :disabled="!isEdit" type="primary" @click="resetForm">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import siteApi from '@/api/team/site'
import { MY_CONSTANT } from '@/utils/constants'

export default {
  name: 'OSSResourceConfig',
  data() {
    return {
      // oss服务配置
      // endpoint：示例：oss-cn-beijing.aliyuncs.com
      // accessKeyId：OSS申请服务的key
      // accessKeySecret：OSS申请服务的secret
      // bucketName：OSS申请服务的secret
      // key：存储根路径，示例：test/studio
      OSSUploadFormData: {
        configId: '',
        configKey: MY_CONSTANT.siteConfigKeys.SITE_UPLOAD_OSS.configKey,
        configValue: {
          endpoint: '',
          accessKeyId: '',
          accessKeySecret: '',
          bucketName: '',
          key: ''
        }
      },
      isEdit: false,
      // 查询参数
      queryParams: {
        configKey: MY_CONSTANT.siteConfigKeys.SITE_UPLOAD_OSS.configKey
      },
      // 测试连通性参数
      testConnParams: {
        configKey: MY_CONSTANT.siteConfigKeys.SITE_UPLOAD_OSS.configKey
      }
    }
  },
  created() {
    this.getOssResourceConfig()
  },
  methods: {
    getOssResourceConfig() {
      // console.log('this.queryParams=>', this.queryParams)
      siteApi.selectConfigValueByConfigKey(this.queryParams).then(data => {
        if (data.code === 200) {
          this.OSSUploadFormData = data.data
        }
      }).catch(err => console.log(err))
    },
    saveOssConfig() {
      // 更新配置（对于上传配置中，默认自带数据源配置测试连通性）
      siteApi.addOrUpdateSiteConfig(this.OSSUploadFormData).then(data => {
        if (data.code === 200) {
          this.isEdit = false
          this.$modal.msgSuccess('更新成功！')
        }
      }).catch(err => console.log(err))
    },
    testConn() {
      // 两种状态：1、编辑状态下测试连通性。2、非编辑状态下测试连通性（使用当前db中的数据源配置）
      // 对于这两种状态都直接使用当前现有的配置传递来测试连通性即可
      siteApi.testConn(this.OSSUploadFormData).then(data => {
        if (data.code === 200) {
          this.$modal.msgSuccess('测试连通成功')
        }
      }).catch(err => console.log(err))
    },
    editOssConfig() {
      if (!this.isEdit) {
        this.isEdit = true
      }
    },
    resetForm() {
      if (!this.isEdit) {
        this.$message({
          showClose: true,
          message: '请切换为编辑状态再重置！',
          type: 'info'
        })
      }
      this.$refs['OSSUploadForm'].resetFields()
    }
  }
}
</script>

<style scoped>

</style>
