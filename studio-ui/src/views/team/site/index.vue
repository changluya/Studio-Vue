<template>
  <div class="app-container">
    <el-tabs type="card" v-model="activeName" @tab-click="handleClick">
      <el-tab-pane lazy label="网站基础配置" name="first">
        <BasicConfig />
      </el-tab-pane>
      <el-tab-pane lazy label="页面配置" name="second">
        <el-tabs class="siteMain" tab-position="left">
          <el-tab-pane label="网站主页" style="height: 1000px !important;">
            <SiteMainPageConfig/>
          </el-tab-pane>
          <el-tab-pane label="网站底部栏目">
            <SiteFooterConfig/>
          </el-tab-pane>
          <el-tab-pane label="时光轴">
            <TimeLineConfig />
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
      <el-tab-pane lazy label="资源上传配置" name="third">
        <el-row style="padding: 20px">
          <span>网站资源上传配置选择</span>
          <el-select v-model="curUploadVal" placeholder="请选择">
            <el-option
              v-for="(uploadConfig, index) in uploadConfigs"
              :key="index"
              :label="uploadConfig.name"
              :value="uploadConfig.val">
            </el-option>
          </el-select>
          <el-button type="primary" @click="submitForm">保存</el-button>
        </el-row>
        <el-tabs tab-position="left">
          <el-tab-pane label="本地文件" style="padding: 20px">无需配置，即可上传到当前网站服务器！</el-tab-pane>
          <el-tab-pane label="OSS存储" style="height: 360px !important;">
            <OSSResourceConfig />
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import BasicConfig from './BasicConfig/index.vue'
//页面配置
import SiteMainPageConfig from './PageConfig/SiteMainPageConfig/index.vue'
import SiteFooterConfig from './PageConfig/SiteFooterConfig/index.vue'
import TimeLineConfig from './PageConfig/TimeLineConfig/index.vue'
//资源上传配置页面
import OSSResourceConfig from './ResourceConfig/OSSResourceConfig/index.vue'


export default {
  name: "Site",
  components: {
    BasicConfig,
    SiteMainPageConfig,
    TimeLineConfig,
    SiteFooterConfig,
    OSSResourceConfig
  },
  data() {
    return {
      activeName: 'first',
      uploadConfigs: [{
        name: '本地文件',
        val: 0
      },{
        name: 'OSS存储',
        val: 1
      }],
      //当前上传模式
      curUploadVal: 0
    };
  },
  created() {
    console.log(665)
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    submitForm() {
      this.$message({
        showClose: true,
        message: '保存成功',
        type: 'success'
      })
    }
  }
};
</script>

<style scoped>
</style>
