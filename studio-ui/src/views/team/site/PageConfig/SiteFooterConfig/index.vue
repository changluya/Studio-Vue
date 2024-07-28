<template>
  <el-form ref="SiteMainForm" :model="siteFooterFormData" size="medium" label-width="140px">
    <el-col :span="24">
      <el-form-item label="团队简介" prop="briefDescription">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="请输入团队简介描述"
          :style="{width: '30%'}"
          v-model="siteFooterFormData.briefDescription">
        </el-input>
      </el-form-item>
      <el-form-item label="联系我们-联系人" prop="contactPerson">
        <el-input v-model="siteFooterFormData.contactPerson" placeholder="请填写官网底部栏目的联系人信息" clearable :style="{width: '30%'}">
        </el-input>
      </el-form-item>
      <el-form-item label="联系我们-地址" prop="contactAddress">
        <el-input v-model="siteFooterFormData.contactAddress" placeholder="请填写官网底部栏目的地址信息" clearable :style="{width: '30%'}">
        </el-input>
      </el-form-item>
      <el-form-item label="联系我们-邮件" prop="contactEmail">
        <el-input v-model="siteFooterFormData.contactEmail" placeholder="请填写官网底部栏目的邮件信息" clearable :style="{width: '30%'}">
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
export default {
  name: "SiteFooterConfig",
  data() {
    return {
      siteFooterFormData: {
        briefDescription: '',
        contactPerson: '',
        contactAddress: '',
        contactEmail: ''
      }
    }
  },
  methods: {
    submitForm() {
      this.$message({
        showClose: true,
        message: '保存成功',
        type: 'success'
      })
    },
    resetForm() {
      this.$refs['SiteMainForm'].resetFields()
    }
  }
}
</script>

<style scoped>

</style>
