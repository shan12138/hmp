import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    username: sessionStorage.getItem('username'),
    user_id: sessionStorage.getItem('user_id'),
    realName: sessionStorage.getItem('realName'),
    robotList: sessionStorage.getItem('robotList'),
    roomList: sessionStorage.getItem('roomList'),
    websocket: null,
    msg: '暂无异常',
    isVisiable: false
  },
  getters: {
    getMsg(state) {
      return state.msg
    },
    getIsVisiable(state) {
      return state.isVisiable
    },
    getUserId(state) {
      return state.user_id
    },
    getUsername(state) {
      return state.username
    },
    getRealName(state) {
      return state.realName
    },
    getRobotList(state) {
      return state.robotList
    },
    getRoomList(state) {
      return state.roomList
    },
    getWebsocket(state) {
      return state.websocket;
    }
  },
  mutations: {
    setUserId(state, userid) {
      state.user_id = userid
    },
    setUsername(state, username) {
      state.username = username
    },
    setRealName(state, realName) {
      state.realName = realName
    },
    setRobotList(state, robotList) {
      state.robotList = robotList
    },
    setRoomList(state, roomList) {
      state.roomList = roomList
    },
    setMsg(state, msg) {
      state.msg = msg
    },
    setIsVisiable(state, status) {
      state.isVisiable = status
    },
    setWebsocket(state, websocket) {
      state.websocket = websocket
    }
  },
  actions: {
    setUserIdInfo({ commit }, userid) {
      commit('setUserId', userid)
    },
    setUsernameInfo({ commit }, username) {
      commit('setUsername', username)
    },
    setRealNameInfo({ commit }, realName) {
      commit('setRealName', realName)
    },
    setRobotListInfo({ commit }, robotList) {
      commit('setRobotList', robotList)
    },
    setRoomListInfo({ commit }, roomList) {
      commit('setRoomList', roomList)
    },
    setMsgInfo({ commit }, msg) {
      commit('setMsg', msg)
    },
    setIsVisiableInfo({ commit }, status) {
      commit('setIsVisiable', status)
    },
    async setWebsocketInfo ({ commit, state }) {
      commit('setWebsocket', await new WebSocket('ws://localhost:8080/preWarning/webSocket'))
      state.websocket.onopen = function () {
        console.log('WebSocket连接成功')
      }
      Vue.use(
        state.websocket.onmessage = function (event) {
          if (event) {
            state.isVisiable = true
            state.msg = event.data
          }
        })
      setInterval(function () {
        state.websocket.send(JSON.stringify({
          'heart': true
        }))
      }, 300000)
    }
  }
})

export default store
