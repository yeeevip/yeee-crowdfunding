import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
  const id = ref(0)
  const name = ref('')
  const avatar = ref('')

  function updateId(newId: number) {
    id.value = newId
  }

  function updateName(newName: string) {
    name.value = newName
  }

  function updateAvatar(newAvatar: string) {
    avatar.value = newAvatar
  }

  function reset() {
    id.value = 0
    name.value = ''
    avatar.value = ''
  }

  return {
    id,
    name,
    avatar,
    updateId,
    updateName,
    updateAvatar,
    reset
  }
})
