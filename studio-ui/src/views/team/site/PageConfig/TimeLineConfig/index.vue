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
      <el-tooltip class="item" effect="dark" content="鼠标按住下面栏目最左侧按钮可拖拽时间线，最顶部则表示的是最新时间" placement="right-start">
        <el-image
          slot = error
          class="tipsImg"
          :src="tipsImg"
          fit="cover"></el-image>
      </el-tooltip>
    </el-row>
    <el-row class="dragTable">
      <draggable v-model="timeLineFormData.configValue" animation="500"  force-fallback="true"  handle=".move"  @start="onStart" @end="onEnd" :move="checkMove">
        <el-form v-for="(timeItem, index) in timeLineFormData.configValue" :key="index" :inline="true" :model="timeItem" class="demo-form-inline">
          <el-form-item>
            <el-image
              class="dragImg move"
              :src="dragImg"
              fit="cover"></el-image>
          </el-form-item>
          <el-form-item>
            {{index + 1}}
          </el-form-item>
          <el-form-item label="年份">
            <el-input :disabled="!isEdit" v-model="timeItem.year" placeholder="请输入年份"></el-input>
          </el-form-item>
          <el-form-item label="小标题">
            <el-input :disabled="!isEdit" v-model="timeItem.title" placeholder="请输入小标题"></el-input>
          </el-form-item>
          <el-form-item label="描述内容">
            <el-input
              :disabled="!isEdit"
              class="descInput"
              type="textarea"
              :rows="3"
              placeholder="请输入描述内容"
              :style="{width: '300px'}"
              v-model="timeItem.description">
            </el-input>
          </el-form-item>
          <el-form-item>
            <!--          <el-button type="primary" circle icon="el-icon-circle-plus-outline"></el-button>-->
            <!--          <i size="medium" class="el-icon-circle-plus-outline"></i>-->
            <!--          <el-button type="primary" icon="el-icon-circle-plus-outline" circle></el-button>-->
            <el-image
              class="subImg"
              :src="isEdit ? subImg : subDisabledImg"
              @click="subTime(index)"
              fit="cover"></el-image>
            <el-image
              class="plusImg"
              :src="isEdit ? plusImg : plusDisabledImg"
              @click="addTime(index)"
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
  name: 'TimeLineConfig',
  components: { draggable },
  props: {
    questionList: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      drag: false,
      timeLineFormData: {
        configId: '',
        configKey: MY_CONSTANT.siteConfigKeys.SITE_PAGE_TIMECONFIG.configKey,
        configValue: [
          { year: '2015', title:'g', description: '' },
          { year: 'www.itxst.com', title:'g', description: '' },
          { year: 'www.itxst.com', title:'g', description: '' },
          { year: 'www.itxst.com', title:'g', description: '' }
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
        configKey: MY_CONSTANT.siteConfigKeys.SITE_PAGE_TIMECONFIG.configKey
      },
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
          this.timeLineFormData = data.data
          console.log('this.timeLineFormData=>', this.timeLineFormData)
        }
      }).catch(err => console.log(err))
    },
    submitForm() {
      // console.log('submitForm this.timeLineFormData=>', this.timeLineFormData)
      siteApi.addOrUpdateSiteConfig(this.timeLineFormData).then(data => {
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
    addTime(index) {
      console.log('cur index=>', index)
      // splice 方法的第一个参数是开始操作的索引位置。
      // 第二个参数是要删除的元素数量，如果是插入操/plus-disabled作，这里应该是 0。
      // 第三个参数是要插入的新元素
      if (this.isEdit) {
        const newItem = { year: '', title: '', description: '' }
        this.timeLineFormData.configValue.splice(index + 1, 0, newItem)
      } else {
        this.$message({
          showClose: true,
          message: '可点击编辑按钮开启编辑！',
          type: 'info'
        })
      }
    },
    subTime(index) {
      if (this.isEdit) {
        // 检查要删除的索引是否有效
        if (index >= 0 && index < this.timeLineFormData.configValue.length) {
          // splice 方法的第一个参数是开始操作的索引位置。
          // 第二个参数是要删除的元素数量，这里应该是 1，因为我们要删除一个元素。
          this.timeLineFormData.configValue.splice(index, 1)
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
  width: 26px;
  height: 26px;
  cursor: pointer;
  position: relative;
  top: 10px;
  left: 5px;
}
</style>
