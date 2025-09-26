<template>
  <div class="iframe-container">
    <iframe 
      v-if="iframeUrl" 
      :src="iframeUrl" 
      frameborder="0" 
      width="100%" 
      height="100%"
      class="iframe-content"
    ></iframe>
    <div v-else class="no-content">
      <p>页面加载中...</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

const iframeUrl = computed(() => {
  return route.meta?.iframeUrl as string
})
</script>

<style scoped>
.iframe-container {
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.iframe-content {
  width: 100%;
  height: 100%;
  border: none;
}

.no-content {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #999;
}
</style>
