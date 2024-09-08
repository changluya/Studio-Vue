<template>
  <div class="app-container">
    <el-tabs type="card" v-model="activeName">
      <!--  学院管理    -->
      <el-tab-pane lazy label="学院管理" name="first">
        <Academy/>
      </el-tab-pane>
      <!--  年级管理    -->
      <el-tab-pane lazy label="年级管理" name="second">
        <Grade/>
      </el-tab-pane>
      <!--  专业管理    -->
      <el-tab-pane lazy label="专业管理" name="third">
        <Major/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Academy from "./academy/index.vue";
import Grade from "./grade/index.vue";
import Major from "./major/index.vue";

export default {
  name: 'Eduction',
  components: { Academy, Grade, Major },
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
}
</script>

<style scoped>

</style>
