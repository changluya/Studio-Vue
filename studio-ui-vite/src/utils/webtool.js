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

//深克隆(数组、对象、其他类型)
const deepClone = function (o) {
  //判断是否是数组(数组类型实际就是object，所有需要提前进行判断)
  if (Array.isArray(o)) {
      var result = [];
      //数组
      for (let i = 0; i < o.length; i++) {
          result.push(deepClone(o[i]));//进行递归调用
      }
  } else if (typeof o == "object") {
      //需要克隆的是对象
      var result = {};
      for (let key in o) {
          result[key] = deepClone(o[key]);//进行递归调用
      }
  } else {
      //其他类型
      var result = o;
  }
  return result;
}

export { isEmpty, getInclusionFlagName, deepClone }
