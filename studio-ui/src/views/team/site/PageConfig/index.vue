<template>
  <el-tabs class="siteMain" tab-position="left"  @tab-click="handleClick">
    <el-tab-pane lazy label="网站主页" style="height: 1000px !important;" :key="refresh.index0">
      <SiteMainPageConfig/>
    </el-tab-pane>
    <el-tab-pane lazy label="网站底部栏目" :key="refresh.index1">
      <SiteFooterConfig/>
    </el-tab-pane>
    <el-tab-pane lazy label="时光轴" :key="refresh.index2">
      <TimeLineConfig />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import SiteMainPageConfig from './SiteMainPageConfig/index.vue';
import SiteFooterConfig from './SiteFooterConfig/index.vue';
import TimeLineConfig from './TimeLineConfig/index.vue';

export default {
  name: 'PageConfig',
  components: { TimeLineConfig, SiteFooterConfig, SiteMainPageConfig },
  data() {
    return {
      tabKey: 'timeLineConfig',
      // 控制组件刷新值 key规范：index + 索引值
      refresh: {
        index0: 1,
        index1: 2,
        index2: 3
      },
      // 前tab索引位置
      preTabIndex: 0
    }
  },
  methods: {
    handleClick(tab, event) {
      // tab.index：栏目选项
      // console.log(tab, event)
      if (tab.index !== this.preTabIndex) {
        this.preTabIndex = tab.index
        const tabIndex = 'index' + tab.index
        this.refresh[tabIndex] = this.refresh[tabIndex] + Math.random()
      }
    },
  }
}
</script>

<style scoped>
</style>
