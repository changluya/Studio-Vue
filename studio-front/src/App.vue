<template>
  <div id="app">
    <Header/>
    <router-view v-if="isRouterAlive"></router-view>
    <Footer/>
  </div>
</template>

<script>

export default {
  provide () {
    return {
      reload: this.reload
    }
  },
  name: 'App',
  components: {
  },
  data () {
    return {
      isRouterAlive: true
    }
  },
  created() {
  },
  methods: {
    reload () {
      this.isRouterAlive = false
      this.$nextTick(function () {
        this.isRouterAlive = true
      })
    }
  },
}
</script>
 
<style scoped>

</style>