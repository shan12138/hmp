<template>
  <div style="text-align:center">
    <el-button type="primary" @click="start()">连接</el-button>
    <el-button type="primary" @click="playPause()">播放/暂停</el-button>
    <el-button type="primary" @click="hangup()">挂断</el-button>
    <br>
    <video ref="visitor" class='video' playsinline autoplay muted>
      您的浏览器不支持 HTML5 video 标签。
    </video>
  </div>
</template>

<script>
export default {
  data () {
    return {
      mediaStreamConstraints: {
        audio: false,
        video: false
      },
      mediaStreamTrack: '',
      localStream: ''
    }
  },
  methods: {
    start () {
      const options = this.mediaStreamConstraints.checked ? { audio: false, video: true } : { audio: false, video: true }
      navigator.mediaDevices
        .getUserMedia(options)
        .then(this.gotLocalMediaStream)
        .catch(this.handleLocalMediaStreamError)
    },
    playPause () {
      const localVideo = this.$refs.visitor
      if (localVideo.paused) {
        localVideo.play()
      } else {
        localVideo.pause()
      }
    },
    hangup () {
      this.mediaStreamTrack && this.mediaStreamTrack.stop()
    },
    gotLocalMediaStream (mediaStream) {
      const localVideo = this.$refs.visitor
      this.localStream = mediaStream
      this.mediaStreamTrack = typeof mediaStream.stop === 'function' ? mediaStream : mediaStream.getTracks()[0]
      // 将视频流赋给
      localVideo.srcObject = mediaStream
    },
    handleLocalMediaStreamError (error) {
      console.log('navigator.getUserMedia error: ', error)
    }
  }
}
</script>

<style scoped>
  .video {
    width: 700px;
    height: 700px;
    margin-top: -60px;
  }
</style>
