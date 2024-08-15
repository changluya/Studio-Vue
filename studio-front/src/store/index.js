import Vue from 'vue'
import Vuex from 'vuex'
import site from './modules/site'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    site 
  },
  getters
})

export default store
