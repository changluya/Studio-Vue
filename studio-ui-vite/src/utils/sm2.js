import { sm2 } from 'sm-crypto';
import { isEmpty } from './webtool';

// 可配置参数
// 1 - C1C3C2；	0 - C1C2C3；  默认为1
const cipherMode = 1

//加密
export function doSM2Encrypt(str, publicKeyQ) {
    // 若是公钥为空，那么就不进行加密
    if (isEmpty(publicKeyQ)) {
        return str;
    }
    // sm2加密处理
    let msg = str
    if (typeof str !== 'string') {
      msg = JSON.stringify(str)
    }
    // 加密结果
    let encryptData = sm2.doEncrypt(msg, publicKeyQ, cipherMode)
    //Base64编码 自行选择是否使用
    //let baseEncode = Base64.encode(encryptData)
    // 加密后的密文前需要添加04，后端才能正常解密 (不添加04，后端处理也可以)
    let encrypt = '04' + encryptData
    return encrypt
}