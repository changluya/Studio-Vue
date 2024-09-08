import { getToken } from '@/utils/auth'

export default {
  "uploadAction": import.meta.env.VITE_API_URL + '/zf/api/upload',
  "uploadFilesAction": import.meta.env.VITE_API_URL + '/zf/api/upload/files'
};
