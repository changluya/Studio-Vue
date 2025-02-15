<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收录状态" prop="title">
        <el-select v-model="queryParams.inclusionFlag" placeholder="请选择收录状态" filterable clearable
                   :style="{width: '100%'}" @keyup.enter.native="handleQuery">
          <el-option v-for="(item, index) in inclusionTypeOptions" :key="index" :label="item.name"
                     :value="item.val" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="clickManagePocs"
        >成果分类管理</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="achievementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        fixed
        label="序号"
        type="index"
        width="70">
      </el-table-column>
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="成果获取时间" align="center" prop="endTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建者" align="center" prop="createUserName" />
      <el-table-column label="综合成果" align="center" prop="title" >
        <template slot-scope="scope">
          <span>{{scope.row.partUserNames.includes(',') ? '团队' : '个人'}}</span>
        </template>
      </el-table-column>
      <el-table-column label="预览图" align="center" prop="previewImg" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.previewImg" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <!--      <el-table-column label="预览图" align="center" prop="previewImg" />-->
      <el-table-column label="成果分类" align="center" prop="pocsName" />
      <el-table-column label="参与者" align="center" prop="partUserNames" width="300">
        <template slot-scope="scope">
          <span>{{scope.row.partUserNames ? scope.row.partUserNames : '暂无'}}</span>
        </template>
      </el-table-column>
      <el-table-column label="收录状态" align="center" prop="inclusionFlag" width="100">
        <template slot-scope="scope">
          <span>{{ getInclusionFlagName(scope.row.inclusionFlag) }}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column label="成果描述" align="center" prop="description" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
          <el-button
            v-if="scope.row.inclusionFlag === 1"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleApprovedInclusion(scope.row)"
          >审核通过</el-button>
          <el-button
            v-if="scope.row.inclusionFlag === 3"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleCancelInclusion(scope.row)"
          >取消收录</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改成果对话框 -->
    <el-dialog title="成果分类管理" :visible.sync="openPocs" width="1000px" append-to-body>
      <Pocs/>
    </el-dialog>

    <!-- 添加或修改成果对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title" label-width="100px">
          <el-input v-model="form.title" placeholder="请输入标题"  :style="{width: '60%'}"/>
        </el-form-item>
        <el-form-item label="预览图" prop="previewImg" label-width="100px">
          <image-upload v-model="form.previewImg" :limit="1" :fileSize="15" :tipInfo="'请上传1920x1080分辨率图片后续用于官网显示！'"/>
        </el-form-item>
        <el-form-item label="成果分类" prop="pocsId" label-width="100px">
          <el-select v-model="form.pocsId" placeholder="请选择成果分类" filterable clearable
                     :style="{width: '60%'}">
            <el-option v-for="(item, index) in pocsIdOptions" :key="index" :label="item.pocsName"
                       :value="item.id" :disabled="item.disabled"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="参与者" prop="partUserIds" label-width="100px">
          <el-select v-model="partUserArr" multiple placeholder="请选择">
            <el-option
              v-for="item in memberOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否收录" prop="chooseInclusion" label-width="100px">
          <el-switch
            v-model="chooseInclusion">
          </el-switch>
        </el-form-item>
        <el-form-item label="过程开始时间" prop="startTime" label-width="100px">
          <el-date-picker
            clearable
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择过程开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="过程结束时间" prop="endTime" label-width="100px">
          <el-date-picker
            clearable
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择过程结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="成果描述" prop="description" label-width="100px">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Pocs from "./pocs/index.vue";
import { listAchievement, getAchievement, delAchievement, addAchievement, updateAchievement, approvedInclusion, cancelInclusion } from '@/api/team/achievement'
import { getPocsMenu } from '@/api/team/pocs'
import { getMemberOptions } from "@/api/team/race";
import { getInclusionMenu } from "@/api/menu";

export default {
  name: 'Achievements',
  components: { Pocs },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 成果表格数据
      achievementList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否显示成果分类弹出层
      openPocs: false,
      // 删除标志，0表示未删除，1表示删除时间范围
      daterangeStartTime: [],
      // 删除标志，0表示未删除，1表示删除时间范围
      daterangeEndTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        description: null,
        inclusionFlag: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          { required: true, message: '标题不能为空', trigger: 'blur' }
        ]
      },
      // 参与者数组列表
      partUserArr: [],
      // 是否收录
      chooseInclusion: false,
      // 下拉选项
      // 1、成果分类
      pocsIdOptions: [],
      // 2、成员分类选项
      memberOptions: [],
      // 3、收录状态选项
      inclusionTypeOptions: [],
    }
  },
  created() {
    this.getList()
    // 获取收录菜单
    this.getInclusionMenuOptions()
  },
  methods: {
    getMenu() {
      // 获取成果分类菜单
      getPocsMenu().then(res => {
        // console.log('getPocsMenu=>', res)
        this.pocsIdOptions = res.data
        // console.log('pocsIdOptions=>', this.pocsIdOptions)
      }).catch((err) => console.log(err))
      // 获取成员选项
      getMemberOptions().then(response => {
        //构造选项数组
        const mOptions = []
        response.data.forEach((user) => {
          mOptions.push({
            "label": user.realName,
            "value": user.userId
          })
        })
        this.memberOptions = mOptions
      });
    },
    /** 查询成果列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {}
      if (this.daterangeStartTime != null && this.daterangeStartTime != '') {
        this.queryParams.params['beginStartTime'] = this.daterangeStartTime[0]
        this.queryParams.params['endStartTime'] = this.daterangeStartTime[1]
      }
      if (this.daterangeEndTime != null && this.daterangeEndTime != '') {
        this.queryParams.params['beginEndTime'] = this.daterangeEndTime[0]
        this.queryParams.params['endEndTime'] = this.daterangeEndTime[1]
      }
      listAchievement(this.queryParams).then(response => {
        this.achievementList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        previewImg: null,
        pocsId: null,
        partUserIds: null,
        startTime: null,
        endTime: null,
        description: null,
        inclusionFlag: 0
      }
      this.partUserArr = []
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeStartTime = []
      this.daterangeEndTime = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加成果'
      // 获取菜单信息
      this.getMenu()
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      // 获取菜单信息
      this.getMenu()
      // 获取成果详情
      getAchievement(id).then(response => {
        this.form = response.data
        // 参与成员id字符串转为数组，如："1,2" => ["1","2"]，后面的map(Number)意思是["1","2"]=>[1,2]
        if (response.data.partUserIds && response.data.partUserIds.length > 0) {
          this.partUserArr = response.data.partUserIds.split(",").map(Number)
        }
        // 是否收录
        this.chooseInclusion = response.data.inclusionFlag === 3
        this.open = true
        this.title = '修改成果'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          // 对提交的表单进行处理
          this.processForm()
          // console.log('this.form=>', this.form)
          if (this.form.id != null) {
            updateAchievement(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addAchievement(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    // 对要提交的表单进行处理
    processForm() {
      // console.log('this.partUserArr=>', this.partUserArr)
      // 1、处理参赛成员：将数组[1,2] => "1,2"来传递到后台保存
      this.form.partUserIds = this.partUserArr.join(',')
      // 2、处理是否申请
      this.form.inclusionFlag = this.chooseInclusion ? 3 : 0
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const title = row.title
      this.$modal.confirm('是否确认删除成果名称为"' + title + '"的数据项？').then(function () {
        return delAchievement(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    // 根据flag获取指定的收录状态
    getInclusionFlagName(inclusionFlag) {
      let name = '';
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
    },
    // 审核通过
    handleApprovedInclusion(row) {
      this.$modal.confirm('是否审核通过成果名称为"' + row.title + '"的成果，审核通过的成果将收录到系统网站展示！').then(() => {
        const form = {
          id: row.id
        }
        // 发起申请请求
        approvedInclusion(form).then(() => {
          this.$modal.msgSuccess('申请成功！')
          this.open = false
          this.getList()
        }).catch((err) => console.log(err))
      })
    },
    // 取消收录
    handleCancelInclusion(row) {
      this.$modal.confirm('是否取消申请成果名称为"' + row.title + '"的成果收录到系统网站展示？').then(() => {
        const form = {
          id: row.id
        }
        cancelInclusion(form).then(() => {
          this.$modal.msgSuccess('取消成功！')
          this.open = false
          this.getList()
        }).catch((err) => console.log(err))
      })
    },
    // 获取证书类别菜单
    getInclusionMenuOptions() {
      // 获取收录类别菜单
      getInclusionMenu().then(data => {
        // console.log('getInclusionMenu:', data)
        this.inclusionTypeOptions = data.data
      }).catch(err => console.log(err))
    },
    // 点击管理成果分类
    clickManagePocs() {
      this.openPocs = true
    }
  }
}
</script>

<style scoped>
>>> .el-upload-list--picture-card .el-upload-list__item {
  width: 240px;
  min-height: 80px;
}
</style>
