<template>
  <div class="app-container">
    <el-tabs type="card" v-model="activeName">
      <!--  网站基础配置栏目选项    -->
      <el-tab-pane lazy label="网站基础配置" name="first">
        <BasicConfig/>
      </el-tab-pane>
      <!--  页面配置栏目选项    -->
      <el-tab-pane lazy label="页面配置" name="second">
        <PageConfig/>
      </el-tab-pane>
      <!--  资源上传配置栏目选项    -->
      <el-tab-pane lazy label="资源上传配置" name="third">
        <ResourceConfig/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import BasicConfig from './BasicConfig/index.vue'
import PageConfig from './PageConfig/index.vue';
import ResourceConfig from './ResourceConfig/index.vue';

export default {
  name: 'Site',
  components: {
    BasicConfig,
    PageConfig,
    ResourceConfig
  },
  data() {
    return {
      activeName: 'first',
      componentKey: 0
    }
  },
  created() {
  },
  methods: {
  }
};
</script>

<style scoped>
</style>
