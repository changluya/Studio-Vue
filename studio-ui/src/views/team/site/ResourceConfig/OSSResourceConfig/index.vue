<template>
  <el-form ref="OSSUploadForm" :model="OSSUploadFormData" size="medium" label-width="140px">
    <el-form-item label="endpoint" prop="endpoint">
      <el-input :disabled="!isEdit" v-model="OSSUploadFormData.endpoint" placeholder="示例：oss-cn-beijing.aliyuncs.com" clearable :style="{width: '30%'}">
      </el-input>
    </el-form-item>
    <el-form-item label="accessKeyId" prop="accessKeyId">
      <el-input :disabled="!isEdit" v-model="OSSUploadFormData.accessKeyId" placeholder="请输入OSS申请服务的key" clearable :style="{width: '30%'}">
      </el-input>
    </el-form-item>
    <el-form-item label="accessKeySecret" prop="accessKeySecret">
      <el-input :disabled="!isEdit" v-model="OSSUploadFormData.accessKeySecret" placeholder="请输入OSS申请服务的secret" clearable :style="{width: '30%'}">
      </el-input>
    </el-form-item>
    <el-form-item label="bucketName" prop="bucketName">
      <el-input :disabled="!isEdit" v-model="OSSUploadFormData.bucketName" placeholder="请输入桶名称，示例：pictured-bedtest" clearable :style="{width: '30%'}">
      </el-input>
    </el-form-item>
    <el-form-item label="key" prop="key">
      <el-input :disabled="!isEdit" v-model="OSSUploadFormData.key" placeholder="请输入存储根路径，示例：test/studio" clearable :style="{width: '30%'}">
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
export default {
  name: "OSSResourceConfig",
  data() {
    return {
      OSSUploadFormData: {
        endpoint: 'oss-cn-beijing.aliyuncs.com',
        accessKeyId: 'xxxxkey',
        accessKeySecret: 'xxxsecret',
        bucketName: 'xxxbucketName',
        key: 'xxxkey'
      },
      isEdit: false
    }
  },
  methods: {
    testConn() {
      this.$message({
        showClose: true,
        message: '连接成功',
        type: 'success'
      })
    },
    editOssConfig() {
      if (this.isEdit) {
        this.isEdit = false;
      }else {
        this.isEdit = true;
      }
    },
    saveOssConfig() {
      if (this.isEdit) {
        this.$message({
          showClose: true,
          message: '保存成功',
          type: 'success'
        })
        this.isEdit = false
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
