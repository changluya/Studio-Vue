<template>
  <el-form ref="basicConfigForm" :model="basicFormData" size="medium" label-width="104px">
    <el-col :span="24">
      <el-form-item label="官网标题" prop="siteTitle">
        <el-input v-model="basicFormData.siteTitle" placeholder="请输入官网标题" clearable :style="{width: '20%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="团队名称" prop="teamTitle">
        <el-input v-model="basicFormData.teamTitle" placeholder="请输入团队名称" clearable :style="{width: '20%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="团队logo" prop="teamLogo">
        <ImageUpload
          :value="basicFormData.teamLogo"
          :limit="2"
        />
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="ICP备案号" prop="ISPN">
        <el-input v-model="basicFormData.ISPN" placeholder="请输入ICP备案号" clearable :style="{width: '20%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="网站创建时间" prop="siteCreateTime">
        <el-input v-model="basicFormData.siteCreateTime" placeholder="请输入网站创建时间" clearable :style="{width: '20%'}">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-col>
  </el-form>
</template>

<script>
export default {
  name: "BasicConfig",
  data() {
    return {
      //simple1:
      // basicFormData: {
      //   siteTitle: '仿生实验室',  //官网标题
      //   teamTitle: '仿生实验室',  //团队名称
      //   teamLogo: 'http://pictured-bedtest.oss-cn-beijing.aliyuncs.com/test/studio/8c44b79e-cd18-40ae-8d5a-5e2a0df303b7.png', //团队logo
      //   ISPN: '京公网安备11000002000001号',  // Internet Standard Publish Number：互联网出版备案号
      //   siteCreateTime: '2016'  //网站创建时间
      // },
      basicFormData: {
        siteTitle: '',
        teamTitle: '',
        teamLogo: '',
        ISPN: '',
        siteCreateTime: ''
      },
    }
  },
  methods: {
    resetForm() {
      this.$refs['basicConfigForm'].resetFields()
    }
  }
}
</script>

<style scoped>

</style>
