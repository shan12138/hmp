import axios from 'axios'
import { showLoading, hideLoading } from './loading'

axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8'
axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.withCredentials = true

axios.interceptors.request.use(
  config => {
    return config
  },
  error => {
    console.log(error && error.data.error.message, 'error')
    return Promise.reject(error.data.error.message)
  }
)

/* 请求拦截器（请求之前的操作） */
axios.interceptors.request.use((req) => {
  showLoading()
  return req
}, err => Promise.reject(err))

/* 请求之后的操作 */
axios.interceptors.response.use((res) => {
  hideLoading()
  return res
}, (err) => {
  hideLoading()
  return Promise.reject(err)
})

export default axios
