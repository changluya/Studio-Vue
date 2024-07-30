<template>
  <el-form ref="SiteMainForm" :model="siteMainFormData" size="medium" label-width="140px">
    <el-form-item>
      <el-button type="danger" size="small" icon="el-icon-edit" @click="editForm">编辑</el-button>
      <el-button type="success" size="small" icon="el-icon-check" :disabled="!isEdit" @click="saveForm">保存</el-button>
    </el-form-item>
    <el-col :span="24">
      <el-form-item label="关于我们" prop="teamDescription">
        <el-input
          type="textarea"
          :rows="3"
          placeholder="请输入关于我们的介绍..."
          :style="{width: '30%'}"
          :disabled="!isEdit"
          v-model="siteMainFormData.teamDescription">
        </el-input>
      </el-form-item>
    </el-col>
    <el-col :span="24">
      <el-form-item class="bannersMan" label="轮播图配置" prop="teamDescription" style="height:300px">
        <template slot-scope="scope">
          <el-table
            ref="singleTable"
            :data="siteMainFormData.bannerTableData"
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
                <ImageUpload
                  :value="scope.row.bannerImg"
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
export default {
  name: "SiteMainPageConfig",
  data() {
    return {
      //表单
      siteMainFormData: {
        teamDescription: '这里是物联网工作室，在这里不仅有学习硬件，还有学习软件的小伙伴们，我们都在前进的路上，未来值得期待！\n工作室有着丰富的学习资源，有着可以帮助你解决问题的学长学姐们以及专业指导老师，让你不断在专业领域进行探索和挖掘知识宝藏。\n',
        bannerTableData: [{
          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/071.png',
          mainTitle: '欢迎来到仿生实验室',
          subTitle: '一群志同道合的人，一起奔跑在理想的路上...'
        },{
          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/072.png',
          mainTitle: '关于我们',
          subTitle: '最好的团队，最好的我们，不负韶华，努力奋斗。'
        },{
          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/073.png',
          mainTitle: '时光轴',
          subTitle: '时间是温柔的羽毛，把过往的灰尘轻轻弹去。'
        },{
          bannerImg: 'https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2024/074.png',
          mainTitle: '团队',
          subTitle: '拍照只需要三秒，可锁住的是我们三年青春，感谢遇见！'
        }]
      },
      //编辑状态
      isEdit: false
    }
  },
  created() {
    // console.log("SiteMainPageConfig")
  },
  methods: {
    editForm() {
      if (this.isEdit) {
        this.isEdit = false;
      }else {
        this.isEdit = true;
      }
    },
    saveForm() {
      if (this.isEdit) {
        this.$message({
          showClose: true,
          message: '保存成功',
          type: 'success'
        })
        this.isEdit = false
      }
    }
  }
}
</script>

<style scoped>
>>> .el-upload-list--picture-card .el-upload-list__item {
  width: 100%;
  //height: auto;0px
  min-width: 230px;
  min-height: 150px;
}


</style>
