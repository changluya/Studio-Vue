<template>
  <div>
    <live2d
      :style="style"
      :model="['Potion-Maker/Pio', 'school-2017-costume-yellow']"
      :direction="direction"
      :size="size"
    ></live2d>
  </div>
</template>

<script>
import live2d from 'vue-live2d'

export default {
    name: 'Live2d',
    components: {
      live2d
    },
    data () {
      return {
        direction: 'right',
        style: 'position: fixed;bottom: 0;right: 20px;z-index: 1;font-size: 0;-webkit-transform: translateY(3px);transform: translateY(3px);',
        width: 500,
        height: 500,
        size: 260,
        tips: {
          mouseover: [{
            selector: '.vue-live2d',
            texts: ['这样可以修改默认语句']
          }]
        }
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