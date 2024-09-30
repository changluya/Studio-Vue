<template>
    <el-form ref="LocalUploadForm" :model="localUploadFormData.configValue" size="medium" label-width="100px">
      <el-form-item label="visitPath" prop="visitPath">
        <el-input :disabled="!isEdit" v-model="localUploadFormData.configValue.visitPath" placeholder="请输入资源访问uri，如：/studio/static/" clearable :style="{width: '30%'}">
        </el-input>
      </el-form-item>
      <el-form-item label="fileUploadDir" prop="fileUploadDir">
        <el-input :disabled="!isEdit" v-model="localUploadFormData.configValue.fileUploadDir" placeholder="请输入文件上传服务器路径配置，如：/opt/upload/" clearable :style="{width: '30%'}">
        </el-input>
      </el-form-item>
      <el-form-item label="ip" prop="ip">
        <el-input :disabled="!isEdit" v-model="localUploadFormData.configValue.ip" placeholder="请输入服务器ip地址或域名，如：192.168.182.122" clearable :style="{width: '30%'}">
        </el-input>
      </el-form-item>
      <el-form-item label="protocol" prop="protocol">
        <el-input :disabled="!isEdit" v-model="localUploadFormData.configValue.protocol" placeholder="请输入请求协议，如：http 或 https" clearable :style="{width: '30%'}">
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
    name: 'LocalResourceConfig',
    data() {
      return {
        localUploadFormData: {
          configId: '',
          configKey: MY_CONSTANT.siteConfigKeys.SITE_UPLOAD_FILE.configKey,
          configValue: {
            visitPath: '',
            fileUploadDir: '',
            ip: '',
            protocol: ''
          }
        },
        isEdit: false,
        // 查询参数
        queryParams: {
          configKey: MY_CONSTANT.siteConfigKeys.SITE_UPLOAD_FILE.configKey
        },
        // 测试连通性参数
        testConnParams: {
          configKey: MY_CONSTANT.siteConfigKeys.SITE_UPLOAD_FILE.configKey
        }
      }
    },
    created() {
      this.getLocalResourceConfig()
    },
    methods: {
      getLocalResourceConfig() {
        // console.log('this.queryParams=>', this.queryParams)
        siteApi.selectConfigValueByConfigKey(this.queryParams).then(data => {
          if (data.code === 200) {
            this.localUploadFormData = data.data
          }
        }).catch(err => console.log(err))
      },
      saveOssConfig() {
        // 更新配置（对于上传配置中，默认自带数据源配置测试连通性）
        siteApi.addOrUpdateSiteConfig(this.localUploadFormData).then(data => {
          if (data.code === 200) {
            this.isEdit = false
            this.$modal.msgSuccess('更新成功！')
          }
        }).catch(err => console.log(err))
      },
      testConn() {
        // 两种状态：1、编辑状态下测试连通性。2、非编辑状态下测试连通性（使用当前db中的数据源配置）
        // 对于这两种状态都直接使用当前现有的配置传递来测试连通性即可
        siteApi.testConn(this.localUploadFormData).then(data => {
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
        this.$refs['LocalUploadForm'].resetFields()
      }
    }
  }
  </script>
  
  <style scoped>
  
  </style>
  