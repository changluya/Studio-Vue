<template>
  <div>
    <el-row style="padding: 20px">
      <span>网站资源上传配置选择</span>
      <el-select v-model="uploadFormData.configValue" placeholder="请选择">
        <el-option
          v-for="(uploadConfig, index) in uploadConfigs"
          :key="index"
          :label="uploadConfig.name"
          :value="uploadConfig.val">
        </el-option>
      </el-select>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </el-row>
    <el-tabs tab-position="left" @tab-click="handleClick">
      <el-tab-pane lazy label="本地文件" :key="refresh.index0" style="padding: 20px">无需配置，即可上传到当前网站服务器！</el-tab-pane>
      <el-tab-pane lazy label="OSS存储" :key="refresh.index1" style="height: 360px !important;">
        <OSSResourceConfig />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import OSSResourceConfig from './OSSResourceConfig/index.vue'

import siteApi from '@/api/team/site'
import { MY_CONSTANT } from '@/utils/constants'
import {ref} from "@vue/composition-api";

export default {
  name: 'ResourceConfig',
  components: { OSSResourceConfig },
  data() {
    return {
      // 当前上传模式
      uploadFormData: {
        configId: '',
        configKey: MY_CONSTANT.siteConfigKeys.SITE_UPLOAD_OPTION.configKey,
        configValue: ''
      },
      uploadConfigs: [{
        name: '本地文件',
        val: 'site.upload.file'
      },{
        name: 'OSS存储',
        val: 'site.upload.oss'
      }],
      // 查询参数
      queryParams: {
        configKey: MY_CONSTANT.siteConfigKeys.SITE_UPLOAD_OPTION.configKey
      },
      // 控制组件刷新值 key规范：index + 索引值
      refresh: {
        index0: 1,
        index1: 2
      },
      // 前tab索引位置
      preTabIndex: 0
    }
  },
  created() {
    this.getUploadOption();
  },
  methods: {
    ref,
    getUploadOption() {
      // console.log('this.queryParams=>', this.queryParams)
      siteApi.selectConfigValueByConfigKey(this.queryParams).then(data => {
        if (data.code === 200) {
          this.uploadFormData = data.data
        }
      }).catch(err => console.log(err))
    },
    submitForm() {
      siteApi.addOrUpdateSiteConfig(this.uploadFormData).then(data => {
        if (data.code === 200) {
          this.isEdit = false
          this.$modal.msgSuccess('更新成功！')
        }
      }).catch(err => console.log(err))
    },
    handleClick(tab, event) {
      // tab.index：栏目选项
      // console.log(tab, event)
      if (tab.index !== this.preTabIndex) {
        this.preTabIndex = tab.index
        const tabIndex = 'index' + tab.index
        this.refresh[tabIndex] = this.refresh[tabIndex] + Math.random()
      }
    },
  }
}
</script>

<style scoped>

</style>
