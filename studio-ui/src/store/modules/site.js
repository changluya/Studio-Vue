import { querySiteConfig } from '@/api/open/openSiteApi'
import { MY_CONSTANT } from '@/utils/constants';

const site = {
  state: {
    teamLogo: '',
    teamTitle: ''
  },

  mutations: {
    SET_TEAM_LOGO: (state, teamLogo) => {
      state.teamLogo = teamLogo
    },
    SET_TEAM_TITLE: (state, teamTitle) => {
      state.teamTitle = teamTitle
    }
  },

  actions: {
    getSiteBasicConfig({ commit }) {
      console.log('MY_CONSTANT=>', MY_CONSTANT)
      const siteConfigParms =  {
        configKey: MY_CONSTANT.siteConfigKeys.SITE_BASICCONFIG.configKey
      }
      return new Promise((resolve, reject) => {
        querySiteConfig(siteConfigParms).then(res => {
          console.log('querySiteConfig=>', querySiteConfig)
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
