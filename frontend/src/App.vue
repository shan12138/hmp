<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App',
  computed: {
    isWarnVisiable: function () {
      return this.$store.getters.getIsVisiable
    }
  },
  watch: {
    isWarnVisiable (val, oldVal) {
      if (val === true) {
        const msg = this.$store.state.msg
        this.$alert(msg, '警告', {
          confirmButtonText: '确定',
          callback: action => {
            this.$store.dispatch('setIsVisiableInfo', false)
            this.$router.replace('./Alarm')
          }
        })
      }
    }
  }
}
</script>

<style>
html,
body,
#app {
  width: 100vw;
  height: 100vh;
  margin: 0;
}
</style>
