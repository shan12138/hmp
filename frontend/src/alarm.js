let websocket = null
// 判断当前浏览器是否支持WebSocket
const connecting = function () {
  if ('WebSocket' in window) {
    // 建立连接，这里的/websocket ，是Servlet中注解中的那个值
    websocket = new WebSocket('ws://localhost:8080/WebSocket/preWarning')
    // 连接发生错误的回调方法
    websocket.onerror = function () {
      console.log('WebSocket连接发生错误')
    }
    // 连接成功建立的回调方法
    websocket.onopen = function () {
      console.log('WebSocket连接成功')
    }
    // websocket = new WebSocket("ws://localhost:8080/HMP/preWarning");
    // websocket = new WebSocket("ws://localhost:8080/hfut.software.hmp.edu.cn.backend.WebSocket/preWarning");
  } else {
    alert('当前浏览器 Not support websocket')
  }
}

if (websocket) {
  // 接收到消息的回调方法
  websocket.onmessage = function (event) {
    // const preWarning = document.getElementById("preWarning");
    // preWarning.innerHTML = event === undefined ? "11111111111111" : event.data;
    alert('111111111111')
    console.log(event.data + '\n')
  }
  // 连接关闭的回调方法
  websocket.onclose = function () {
    console.log('WebSocket连接关闭')
  }
}
// 监听窗口关闭事件，当窗口关闭时，主动去关闭WebSocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
  closeWebSocket()
}
// 关闭WebSocket连接
function closeWebSocket () {
  websocket.close()
}

export default {
  websocket,
  connecting
}
