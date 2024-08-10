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
export default service;