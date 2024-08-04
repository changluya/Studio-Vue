<template>
  <el-form ref="SiteMainForm" :model="siteFooterFormData.configValue" size="medium" label-width="140px">
    <el-col :span="24">
      <el-form-item label="团队简介" prop="briefDescription">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="请输入团队简介描述"
          :style="{width: '30%'}"
          v-model="siteFooterFormData.configValue.briefDescription">
        </el-input>
      </el-form-item>
      <el-form-item label="联系我们-联系人" prop="contactPerson">
        <el-input v-model="siteFooterFormData.configValue.contactPerson" placeholder="请填写官网底部栏目的联系人信息" clearable :style="{width: '30%'}">
        </el-input>
      </el-form-item>
      <el-form-item label="联系我们-地址" prop="contactAddress">
        <el-input v-model="siteFooterFormData.configValue.contactAddress" placeholder="请填写官网底部栏目的地址信息" clearable :style="{width: '30%'}">
        </el-input>
      </el-form-item>
      <el-form-item label="联系我们-邮件" prop="contactEmail">
        <el-input v-model="siteFooterFormData.configValue.contactEmail" placeholder="请填写官网底部栏目的邮件信息" clearable :style="{width: '30%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item size="large">
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
  name: 'SiteFooterConfig',
  data() {
    return {
      siteFooterFormData: {
        configId: '',
        configKey: MY_CONSTANT.siteConfigKeys.SITE_PAGE_FOOTERCONFIG.configKey,
        configValue: {
          briefDescription: '去年在工作室偶然听到其他工作室要做一个官网，接着趁着一时兴起然后也找了个模板来做了一个自己的官网以及一个后台管理系统，当时的后台系统只有一个登录、注册还有一个提交个人信息的页面，主要为了方便展示动态汇总团队成员。\n' +
            '该工作室系统主要面向校园工作室，可供个人及工作室团队学习使用。',
          contactPerson: '',
          contactAddress: '',
          contactEmail: ''
        }
      },
      // 查询参数
      queryParams: {
        configKey: MY_CONSTANT.siteConfigKeys.SITE_PAGE_FOOTERCONFIG.configKey
      },
    }
  },
  created() {
    this.getSiteFooterConfig()
  },
  methods: {
    getSiteFooterConfig() {
      // console.log('this.queryParams=>', this.queryParams)
      siteApi.selectConfigValueByConfigKey(this.queryParams).then(data => {
        if (data.code === 200) {
          this.siteFooterFormData = data.data
          // console.log('this.basicFormData=>', this.basicFormData)
        }
      }).catch(err => console.log(err))
    },
    submitForm() {
      siteApi.addOrUpdateSiteConfig(this.siteFooterFormData).then(data => {
        if (data.code === 200) {
          this.isEdit = false
          this.$modal.msgSuccess('更新成功！')
        }
      }).catch(err => console.log(err))
    },
    resetForm() {
      this.$refs['SiteMainForm'].resetFields()
    },
  }
}
</script>

<style scoped>

</style>
