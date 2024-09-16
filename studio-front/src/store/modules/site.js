import { querySiteConfig } from '@/api/openSiteApi'
import { MY_CONSTANT } from '@/utils/constants';

const site = {
  state: {
    teamLogo: '',
    teamTitle: '',
    siteTitle: '',
    ISPN: '',
    siteCreateTime: ''
  },

  mutations: {
    SET_TEAM_LOGO: (state, teamLogo) => {
      // 对应index.html中去除图标
      const link = document.querySelector("link[rel*='icon']") || document.createElement('link')
      link.type = 'image/x-icon'
      link.rel = 'shortcut icon'
      link.href = teamLogo
      document.getElementsByTagName('head')[0].appendChild(link)
      state.teamLogo = teamLogo
    },
    SET_TEAM_TITLE: (state, teamTitle) => {
      document.getElementsByTagName('title')[0].innerText = teamTitle
      state.teamTitle = teamTitle
    },
    SET_SITE_TITLE: (state, siteTitle) => {
      state.siteTitle = siteTitle
    },
    SET_ISPN: (state, ISPN) => {
      state.ISPN = ISPN
    },
    SET_SITE_CREATE_TIME: (state, siteCreateTime) => {
      state.siteCreateTime = siteCreateTime
    },
  },

  actions: {
    getSiteBasicConfig({ commit }) {
      // console.log('getSiteBasicConfig=>', commit)
      // console.log('MY_CONSTANT=>', MY_CONSTANT)
      const siteConfigParms =  {
        configKey: MY_CONSTANT.SITE_CONFIG.SITE_BASIC_CONFIG.configKey
      }
      return new Promise((resolve, reject) => {
        querySiteConfig(siteConfigParms).then(res => {
          // console.log('querySiteConfig=>', res)
          const configValue = res.data.configValue
          commit('SET_TEAM_LOGO', configValue.teamLogo)
          commit('SET_TEAM_TITLE', configValue.teamTitle)
          commit('SET_SITE_TITLE', configValue.siteTitle)
          commit('SET_ISPN', configValue.ispn)
          // console.log('SET_SITE_CREATE_TIME=>', configValue.siteCreateTime)
          commit('SET_SITE_CREATE_TIME', configValue.siteCreateTime)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}

export default site
