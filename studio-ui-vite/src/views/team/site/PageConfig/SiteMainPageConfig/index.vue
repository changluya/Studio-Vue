<template>
  <el-form ref="SiteMainForm" :model="siteMainFormData.configValue" size="medium" label-width="140px">
    <el-form-item>
      <el-button type="danger" size="small" icon="el-icon-edit" @click="editForm">编辑</el-button>
      <el-button type="success" size="small" icon="el-icon-check" :disabled="!isEdit" @click="submitForm">保存</el-button>
    </el-form-item>
    <el-col :span="24">
      <el-form-item label="关于我们" prop="teamDescription">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="请输入关于我们的介绍..."
          :style="{width: '60%'}"
          :disabled="!isEdit"
          v-model="siteMainFormData.configValue.teamDescription">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="我们的使命" prop="teamMission">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="请输入关于我们的使命..."
          :style="{width: '60%'}"
          :disabled="!isEdit"
          v-model="siteMainFormData.configValue.teamMission">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="我们的计划" prop="teamPlan">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="请输入关于我们的计划..."
          :style="{width: '60%'}"
          :disabled="!isEdit"
          v-model="siteMainFormData.configValue.teamPlan">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item label="我们的愿景" prop="teamVision">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="请输入关于我们的愿景..."
          :style="{width: '60%'}"
          :disabled="!isEdit"
          v-model="siteMainFormData.configValue.teamVision">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item class="bannersMan" label="轮播图配置" prop="teamDescription" style="height:300px">
        <template slot-scope="scope">
          <el-table
            ref="singleTable"
            :data="siteMainFormData.configValue.bannerTableData"
            highlight-current-row
            style="width: 100%">
            <el-table-column
              label="序号"
              type="index"
              width="120">
            </el-table-column>
            <!--              property="bannerImg"-->
            <el-table-column
              label="Banner图"
              width="250">
              <template slot-scope="scope">
                <image-upload
                  v-model="scope.row.bannerImg"
                  :limit="1"
                  :disabled="!isEdit"
                  :tipInfo="'请上传1920x1080分辨率图片官网显示！'"
                  style="font-size: 10px"
                />
              </template>
            </el-table-column>
            <el-table-column
              label="主标题"
              width="300">
              <template slot-scope="scope">
                <el-input :disabled="!isEdit" v-model="scope.row.mainTitle" placeholder="请输入主标题" clearable :style="{width: '100%'}">
                </el-input>
              </template>
            </el-table-column>
            <el-table-column
              label="副标题"
              width="400">
              <template slot-scope="scope">
                <el-input :disabled="!isEdit" v-model="scope.row.subTitle" placeholder="请输入副标题" clearable :style="{width: '100%'}">
                </el-input>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-form-item>
    </el-col>
  </el-form>
</template>

<script>
import siteApi from '@/api/team/site'
import { MY_CONSTANT } from '@/utils/constants'

export default {
  name: 'SiteMainPageConfig',
  data() {
    return {
      // 表单：simple1
      siteMainFormData: {
        configId: '',
        configKey: MY_CONSTANT.siteConfigKeys.SITE_PAGE_MAIN_CONFIG.configKey,
        configValue: {
          teamDescription: '',
          teamMission: '', 
          teamPlan: '',
          teamVision: '',
          bannerTableData: [{
            bannerImg: '',
            mainTitle: '',
            subTitle: ''
          },{
            bannerImg: '',
            mainTitle: '',
            subTitle: ''
          },{
            bannerImg: '',
            mainTitle: '',
            subTitle: ''
          },{
            bannerImg: '',
            mainTitle: '',
            subTitle: ''
          }]
        }
      },
      // 查询参数
      queryParams: {
        configKey: MY_CONSTANT.siteConfigKeys.SITE_PAGE_MAIN_CONFIG.configKey
      },
      // 编辑状态
      isEdit: false
    }
  },
  created() {
    this.getMainPageConfig()
  },
  methods: {
    getMainPageConfig() {
      // console.log('this.queryParams=>', this.queryParams)
      siteApi.selectConfigValueByConfigKey(this.queryParams).then(data => {
        if (data.code === 200) {
          this.siteMainFormData = data.data
          // console.log('this.basicFormData=>', this.basicFormData)
        }
      }).catch(err => console.log(err))
    },
    // 编辑表单
    editForm() {
      if (this.isEdit) {
        this.isEdit = false;
      }else {
        this.isEdit = true;
      }
    },
    submitForm() {
      if (this.isEdit) {
        console.log('this.siteMainFormData=>', this.siteMainFormData)
        siteApi.addOrUpdateSiteConfig(this.siteMainFormData).then(data => {
          if (data.code === 200) {
            this.isEdit = false
            this.$modal.msgSuccess('更新成功！')
          }
        }).catch(err => console.log(err))
      }
    }
  }
}
</script>

<style scoped>
::v-deep .el-upload-list--picture-card .el-upload-list__item {
  width: 100%;
  min-width: 230px;
  min-height: 150px;
}
</style>
