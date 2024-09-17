<template>
  <el-form ref="OSSUploadForm" :model="OSSUploadFormData.configValue" size="medium" label-width="140px">
    <el-form-item label="endpoint" prop="endpoint">
      <el-input :disabled="!isEdit" v-model="OSSUploadFormData.configValue.endpoint" placeholder="示例：oss-cn-beijing.aliyuncs.com" clearable :style="{width: '30%'}">
      </el-input>
    </el-form-item>
    <el-form-item label="accessKeyId" prop="accessKeyId">
      <el-input show-password :disabled="!isEdit" v-model="OSSUploadFormData.configValue.accessKeyId" placeholder="请输入OSS申请服务的key" clearable :style="{width: '30%'}">
      </el-input>
    </el-form-item>
    <el-form-item label="accessKeySecret" prop="accessKeySecret">
      <el-input show-password :disabled="!isEdit" v-model="OSSUploadFormData.configValue.accessKeySecret" placeholder="请输入OSS申请服务的secret" clearable :style="{width: '30%'}">
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
import { getSM2PublicKeyQ } from '@/api/index'
import { MY_CONSTANT } from '@/utils/constants'
import { doSM2Encrypt } from '@/utils/sm2'
import { deepClone } from '@/utils/webtool'

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
          // data.data 对应响应中的data
          this.tempSaveLastOssConfig(data.data)
        }
      }).catch(err => console.log(err))
    },
    tempSaveLastOssConfig(formData) {
      this.OSSUploadFormData = formData
      // 保存最新一次修改的内容
      this.lastOSSUploadFormData = {
        accessKeyId: this.OSSUploadFormData.configValue.accessKeyId,
        accessKeySecret: this.OSSUploadFormData.configValue.accessKeySecret
      }
    },
    async saveOssConfig() {
      // 获取到公钥Q
      let publicKeyQ = ''
      await getSM2PublicKeyQ().then(data => {
        if (data.code === 200) {
          publicKeyQ = data.data
        }
      }).catch(err => console.log(err))
      // 阻塞获取到公钥Q
      // console.log("publicKeyQ=>", publicKeyQ)

      // 构造请求对象
      let saveFormData = this.buildSaveOssConfig(this.lastOSSUploadFormData, this.OSSUploadFormData.configValue, publicKeyQ)

      // 打印当前构造出来的表单
      // console.log("saveFormData=>", saveFormData)
      // 更新配置（对于上传配置中，默认自带数据源配置测试连通性）
      siteApi.addOrUpdateSiteConfig(saveFormData).then(data => {
        if (data.code === 200) {
          this.isEdit = false
          this.$modal.msgSuccess('更新成功！')
          this.tempSaveLastOssConfig(saveFormData)
        }
      }).catch(err => console.log(err))
    },
    // 对比最新一次获取到
    buildSaveOssConfig(lastOssData, curOssData, publicKeyQ) {
      // console.log("lastOssData=>", lastOssData)
      // console.log("curOssData=>", curOssData)
      // 比较上次内容是否有过修改。如果没有修改，那么会进行sm2加密处理
      let last_accessKeyId = lastOssData.accessKeyId
      let last_accessKeySecret = lastOssData.accessKeySecret
      // 当前动态绑定的表单
      let cur_accessKeyId = curOssData.accessKeyId
      let cur_accessKeySecret = curOssData.accessKeySecret

      // 深拷贝当前的form表单参数
      let saveFormData = deepClone(this.OSSUploadFormData)
      // 若是用户修改过、当前并没有sm2加密处理
      if (last_accessKeyId !== cur_accessKeyId || cur_accessKeyId.substring(0,2) !== '04') {
        saveFormData.configValue.accessKeyId = doSM2Encrypt(cur_accessKeyId, publicKeyQ)
      }
      if (last_accessKeySecret !== cur_accessKeySecret || cur_accessKeySecret.substring(0,2) !== '04') {
        saveFormData.configValue.accessKeySecret = doSM2Encrypt(cur_accessKeySecret, publicKeyQ)
      }
      return saveFormData
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
