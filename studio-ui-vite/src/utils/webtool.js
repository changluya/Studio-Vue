/**
 * 判断是否为空
 * @param {} str
 * @returns boolean
 */
const isEmpty = function (str) {
  if (str === undefined || str === '' || str.trim() === '') {
    return true
  }
  return false
}

// 根据flag获取指定的收录状态
const getInclusionFlagName = function(inclusionFlag) {
  let name = ''
  if (inclusionFlag === 0) {
    name = '未收录'
  } else if (inclusionFlag === 1) {
    name = '申请中'
  } else if (inclusionFlag === 2) {
    name = '收录打回'
  } else if (inclusionFlag === 3) {
    name = '收录通过'
  }
  return name
}

export { isEmpty, getInclusionFlagName }
