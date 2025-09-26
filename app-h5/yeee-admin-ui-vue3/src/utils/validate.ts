/**
 * 邮箱
 * @param {*} s
 */
export function isEmail(s: string): boolean {
  return /^([a-zA-Z0-9._-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test(s)
}

/**
 * 手机号码
 * @param {*} s
 */
export function isMobile(s: string): boolean {
  return /^1[0-9]{10}$/.test(s)
}

/**
 * 电话号码
 * @param {*} s
 */
export function isPhone(s: string): boolean {
  return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
}

/**
 * URL地址
 * @param {*} s
 */
export function isURL(s: string): boolean {
  return /^http[s]?:\/\/.*/.test(s)
}

/**
 * 验证小写字母
 */
export function validateLowerCase(str: string): boolean {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/**
 * 验证大写字母
 */
export function validateUpperCase(str: string): boolean {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/**
 * 验证大小写字母
 */
export function validatAlphabets(str: string): boolean {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}
