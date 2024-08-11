import axios from 'axios'

const service = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  timeout: 5000
})

// request 拦截器
service.interceptors.request.use(
  config => {
    // 在这里可以设置请求头、请求参数等return config
    return config
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    // 在这里处理返回数据const { data } = response
    if (response.data.code !== 200) {
      console.error('Error:', data.message)
      return Promise.reject(newError(data.message || 'Error'))
    } else {
      return response.data
    }
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

import { querySiteConfig } from '@/api/openSiteApi.js'
// 导出查询网站配置
export async function getSiteConfig(params) {
  // console.log("params=>", params)
  try {
    let configValue = {}
    // 阻塞等待该次请求结束
    await querySiteConfig(params).then((data) => {
      configValue = data.data.configValue
    });
    // console.log(configValue)
    return configValue;
  } catch (err) {
    // 错误处理
    console.error('Error in getSiteConfig:', err);
    throw err; // 重新抛出错误，或者根据需要进行错误处理
  }
}

export default service;