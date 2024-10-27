import { querySiteConfig } from '@/api/open/openSiteApi'
import { MY_CONSTANT } from '@/utils/constants';

const site = {
  state: {
    teamLogo: '',
    teamTitle: ''
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
      document.getElementsByTagName('title')[0].innerText = teamTitle + '后台管理系统'
      state.teamTitle = teamTitle
    }
  },

  actions: {
    getSiteBasicConfig({ commit }) {
      // console.log('MY_CONSTANT=>', MY_CONSTANT)
      const siteConfigParms =  {
        configKey: MY_CONSTANT.siteConfigKeys.SITE_BASIC_CONFIG.configKey
      }
      return new Promise((resolve, reject) => {
        querySiteConfig(siteConfigParms).then(res => {
          // console.log('querySiteConfig=>', querySiteConfig)
          const configValue = res.data.configValue
          commit('SET_TEAM_LOGO', configValue.teamLogo)
          commit('SET_TEAM_TITLE', configValue.teamTitle)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}

export default site
