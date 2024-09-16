<template>
    <div style="padding: 10px 0 0 10px">
      <!--
        draggable组件
        Github：https://github.com/SortableJS/Vue.Draggable
        documents：https://www.itxst.com/vue-draggable/tutorial.html
        class="move" 可拽动的元素设置该样式即可
      -->
      <el-row>
        <el-button type="danger" size="small" icon="el-icon-edit" @click="editForm">编辑</el-button>
        <el-button type="success" size="small" icon="el-icon-check" :disabled="!isEdit" @click="submitForm">保存</el-button>
        <el-tooltip class="item" effect="dark" content="鼠标按住下面栏目最左侧按钮可拖拽技术栈，最顶部则表示的优先展示的技术栈" placement="top-start">
            <el-image
                class="tipsImg"
                :src="tipsImg"
                fit="cover"></el-image>
        </el-tooltip>
      </el-row>
      <el-row class="dragTable">
        <draggable v-model="skillFormData.configValue" animation="500"  force-fallback="true"  handle=".move"  @start="onStart" @end="onEnd" :move="checkMove">
          <el-form v-for="(skillItem, index) in skillFormData.configValue" :key="index" :inline="true" :model="skillItem" class="demo-form-inline">
            <el-form-item>
              <el-image
                class="dragImg move"
                :src="dragImg"
                fit="cover"></el-image>
            </el-form-item>
            <el-form-item>
              {{index + 1}}
            </el-form-item>
            <el-form-item label="技术栈">
              <el-input :disabled="!isEdit" v-model="skillItem.skillName" placeholder="请输入技术栈"></el-input>
            </el-form-item>
            <el-form-item label="百分比">
                <el-progress :width="100" style="margin: 0px 50px 0px 10px" type="dashboard" :percentage="skillItem.skillPercentage" :color="customColors"></el-progress>
                <div style="margin-left: 10%;">
                    <el-button-group>
                        <el-button icon="el-icon-minus" size="mini" :disabled="!isEdit" @click="decrease(index)"></el-button>
                        <el-button icon="el-icon-plus" size="mini" :disabled="!isEdit" @click="increase(index)"></el-button>
                    </el-button-group>
                </div>
            </el-form-item>
            <el-form-item>
              <el-image
                class="subImg"
                :src="isEdit ? subImg : subDisabledImg"
                @click="subSkill(index)"
                fit="cover"></el-image>
              <el-image
                class="plusImg"
                :src="isEdit ? plusImg : plusDisabledImg"
                @click="addSkill(index)"
                fit="cover"></el-image>
            </el-form-item>
          </el-form>
        </draggable>
      </el-row>
    </div>
  </template>
  
  <script>
  import draggable from 'vuedraggable'
  import dragImg from '@/assets/images/drag.png'
  import plusImg from '@/assets/images/plus.png'
  import plusDisabledImg from '@/assets/images/plus-disabled.png'
  import subImg from '@/assets/images/sub.png'
  import subDisabledImg from '@/assets/images/sub-disabled.png'
  import tipsImg from '@/assets/images/tips.png'
  
  import siteApi from '@/api/team/site'
  import { MY_CONSTANT } from '@/utils/constants'
  
  export default {
    name: 'SiteMainPageSkillConfig',
    components: { draggable },
    data() {
      return {
        drag: false,
        skillFormData: {
          configId: '',
          configKey: MY_CONSTANT.siteConfigKeys.SITE_PAGE_SKILL_CONFIG.configKey,
          configValue: [
            { skillName: 'Java', skillPercentage: 10 },
            { skillName: '网站开发', skillPercentage: 10 },
            { skillName: '上位机', skillPercentage: 10 },
            { skillName: 'openCV', skillPercentage: 10 },
          ]
        },
        dragImg: dragImg,
        plusImg: plusImg,
        tipsImg: tipsImg,
        plusDisabledImg: plusDisabledImg,
        subImg: subImg,
        subDisabledImg: subDisabledImg,
        // 编辑状态
        isEdit: false,
        // 查询参数
        queryParams: {
          configKey: MY_CONSTANT.siteConfigKeys.SITE_PAGE_SKILL_CONFIG.configKey
        },
        // 自定义颜色
        customColors: [
          {color: '#f56c6c', percentage: 20},
          {color: '#e6a23c', percentage: 40},
          {color: '#5cb87a', percentage: 60},
          {color: '#1989fa', percentage: 80},
          {color: '#6f7ad3', percentage: 100}
        ]
      }
    },
    mounted() {
      this.getTimeLineConfig();
    },
    methods: {
      getTimeLineConfig() {
        // console.log('this.queryParams=>', this.queryParams)
        siteApi.selectConfigValueByConfigKey(this.queryParams).then(data => {
          if (data.code === 200) {
            this.skillFormData = data.data
            // console.log("skillData=>", this.skillFormData)
            let skillArr = this.skillFormData.configValue
            // 若是技术栈数组为空，默认补充1个
            if (Array.isArray(skillArr) && skillArr.length === 0) {
                this.skillFormData.configValue = [
                    { skillName: '', skillPercentage: 0  }
                ]
            }
            // console.log('getTimeLineConfig，this.skillFormData=>', this.skillFormData)
          }
        }).catch(err => console.log(err))
      },
      submitForm() {
        // console.log('submitForm this.skillFormData=>', this.skillFormData)
        siteApi.addOrUpdateSiteConfig(this.skillFormData).then(data => {
          if (data.code === 200) {
            this.isEdit = false
            this.$modal.msgSuccess('更新成功！')
          }
        }).catch(err => console.log(err))
      },
      checkMove(evt) {
        return true;
      },
      // 开始拖拽事件
      onStart(){
        this.drag=true;
      },
      //拖拽结束事件
      onEnd() {
        if (!this.isEdit)
          this.submitForm()
      },
      addSkill(index) {
        console.log('cur index=>', index)
        // splice 方法的第一个参数是开始操作的索引位置。
        // 第二个参数是要删除的元素数量，如果是插入操/plus-disabled作，这里应该是 0。
        // 第三个参数是要插入的新元素
        if (this.isEdit) {
          const newItem = { skillName: '', skillPercentage: 0  }
          this.skillFormData.configValue.splice(index + 1, 0, newItem)
        } else {
          this.$message({
            showClose: true,
            message: '可点击编辑按钮开启编辑！',
            type: 'info'
          })
        }
      },
      subSkill(index) {
        if (this.isEdit) {
          // 检查要删除的索引是否有效
          if (index >= 0 && index < this.skillFormData.configValue.length) {
            // splice 方法的第一个参数是开始操作的索引位置。
            // 第二个参数是要删除的元素数量，这里应该是 1，因为我们要删除一个元素。
            this.skillFormData.configValue.splice(index, 1)
          }
        } else {
          this.$message({
            showClose: true,
            message: '请先点击编辑按钮开启编辑模式！',
            type: 'info'
          })
        }
      },
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
      },
      increase(index) {
        console.log("this.skillFormData.configValue=>", this.skillFormData.configValue)
        let skillPercentage = this.skillFormData.configValue[index].skillPercentage
        console.log("skillPercentage=>", skillPercentage)
        skillPercentage += 10
        if (skillPercentage > 100) {
            skillPercentage = 100;
        }
        this.skillFormData.configValue[index].skillPercentage = skillPercentage
      },
      decrease(index) {
        console.log("this.skillFormData.configValue=>", this.skillFormData.configValue)
        let skillPercentage = this.skillFormData.configValue[index].skillPercentage
        console.log("skillPercentage=>", skillPercentage)
        skillPercentage -= 10;
        if (skillPercentage < 0) {
            skillPercentage = 0;
        }
        this.skillFormData.configValue[index].skillPercentage = skillPercentage
      }
    }
  }
  </script>
  
  <style scoped>
  /*定义要拖拽元素的样式*/
  .dragImg {
    width: 20px;
    height: 20px;
    position: relative;
    top: 5px;
    cursor: pointer;
  }
  .dragImg.move:hover {
    cursor: move;
  }
  .plusImg {
    width: 27px;
    height: 27px;
    cursor: pointer;
  }
  
  .subImg {
    width: 23px;
    height: 23px;
    cursor: pointer;
    position: relative;
    top: -2px;
    margin-right: 8px;
  }
  
  .dragTable {
    margin-top: 30px;
  }
  
  .tipsImg {
    width: 20px;
    height: 20px;
    cursor: pointer;
    position: relative;
    top: 5px;
    left: 8px;
  }
  </style>
  