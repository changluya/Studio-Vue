<template>
  <el-form ref="basicConfigForm" :model="basicFormData.configValue" size="medium" label-width="104px">
    <el-col :span="24">
      <el-form-item label="官网标题" prop="=siteTitle">
        <el-input v-model="basicFormData.configValue.siteTitle" placeholder="请输入官网标题" clearable :style="{width: '20%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="团队名称" prop="teamTitle">
        <el-input v-model="basicFormData.configValue.teamTitle" placeholder="请输入团队名称" clearable :style="{width: '20%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="团队logo" prop="teamLogo">
        <image-upload v-model="basicFormData.configValue.teamLogo" :limit="1" :fileSize="10"/>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="ICP备案号" prop="ispn">
        <el-input v-model="basicFormData.configValue.ispn" placeholder="请输入ICP备案号" clearable :style="{width: '20%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="网站创建时间" prop="siteCreateTime">
        <el-input v-model="basicFormData.configValue.siteCreateTime" placeholder="请输入网站创建时间" clearable :style="{width: '20%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-col>
  </el-form>
</template>

<script>
import siteApi from '@/api/team/site'
import { MY_CONSTANT } from '@/utils/constants'

export default {
  name: 'BasicConfig',
  data() {
    return {
      // simple1:
      // basicFormData: {
      //   siteTitle: '仿生实验室',  //官网标题
      //   teamTitle: '仿生实验室',  //团队名称
      //   teamLogo: 'http://pictured-bedtest.oss-cn-beijing.aliyuncs.com/test/studio/8c44b79e-cd18-40ae-8d5a-5e2a0df303b7.png', //团队logo
      //   ISPN: '京公网安备11000002000001号',  // Internet Standard Publish Number：互联网出版备案号
      //   siteCreateTime: '2016'  //网站创建时间
      // },
      basicFormData: {
        configId: '',
        configKey: MY_CONSTANT.siteConfigKeys.SITE_BASICCONFIG.configKey,
        configValue: {
          siteTitle: '',
          teamTitle: '',
          teamLogo: '',
          ispn: '',
          siteCreateTime: ''
        }
      },
      timeLogo: '',
      // 查询参数
      queryParams: {
        configKey: MY_CONSTANT.siteConfigKeys.SITE_BASICCONFIG.configKey
      }
    }
  },
  created() {
    this.getBasicConfig();
  },

  methods: {
    resetForm() {
      this.$refs['basicConfigForm'].resetFields()
    },
    // 获取网站基础配置
    getBasicConfig() {
      // console.log('this.queryParams=>', this.queryParams)
      siteApi.selectConfigValueByConfigKey(this.queryParams).then(data => {
        // console.log('query data:', data)
        if (data.code === 200) {
          this.basicFormData = data.data
          // console.log('this.basicFormData=>', this.basicFormData)
        }
      }).catch(err => console.log(err))
    },
    // 提交表单
    submitForm() {
      siteApi.addOrUpdateSiteConfig(this.basicFormData).then(data => {
        if (data.code === 200) {
          this.$modal.msgSuccess('更新成功！')
        }
      }).catch(err => console.log(err))
    }
  }
}
</script>

<style scoped>

</style>
