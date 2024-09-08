<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="获奖时间">
        <el-date-picker
          v-model="daterangeCcieGetTime"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ccieList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="序号"
        type="index"
        width="50">
      </el-table-column>
<!--      <el-table-column label="获奖证书主键id" align="center" prop="ccieId" />-->
      <el-table-column label="证书名称" align="center" prop="ccieName" width="200"/>
      <el-table-column label="获奖证书" align="center" prop="ccieImg" width="100">
        <!--    挂载预览图片    -->
        <template slot-scope="scope">
          <image-preview :src="scope.row.ccieImg" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="证书类别" align="center" prop="typeName" width="150"/>
      <el-table-column label="获奖时间" align="center" prop="ccieGetTime" width="150">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ccieGetTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="收录状态" align="center" prop="inclusionFlag" width="150">
        <template slot-scope="scope">
          <span>{{ getInclusionFlagName(scope.row.inclusionFlag) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="经验总结" align="center" prop="ccieThink" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-opportunity"
            @click="previewThink(scope.row)"
            v-hasRole="['member']"
          >预览总结</el-button>
          <el-button
        </template>
      </el-table-column>
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
            v-if="scope.row.inclusionFlag === 0 || scope.row.inclusionFlag === 2"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleApplyInclusion(scope.row)"
          >申请收录</el-button>
          <el-button
            v-if="scope.row.inclusionFlag === 1 || scope.row.inclusionFlag === 3"
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

    <!-- 预览总结对话框-->
    <el-dialog class="previewDialog" title="经验总结" :visible.sync="previewOpen" :before-close="handlePreviewClose" width="1000px" append-to-body>
      <Editor v-model="curThinkHTML" :minHeight="200" :readOnly="true"></Editor>
    </el-dialog>

    <!-- 添加或修改ZfCcie对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" :close-on-click-modal="false" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="证书名称" prop="ccieName" label-width="120px">
          <el-input v-model="form.ccieName" placeholder="请输入证书名称" :style="{width: '30%'}"/>
        </el-form-item>
        <el-form-item label="获奖证书" prop="ccieImg" label-width="120px">
          <image-upload v-model="form.ccieImg" :limit="1" :fileSize="10"/>
        </el-form-item>
        <!--    显示申请收录表单，若是已申请，那么此时会直接显示已申请    -->
        <el-form-item v-if="showApply" label="申请收录" prop="chooseInclusion" label-width="120px">
          <el-switch
            v-model="chooseInclusion">
          </el-switch>
        </el-form-item>
        <el-form-item v-else label="收录状态" prop="chooseInclusion" label-width="120px">
          已收录
        </el-form-item>
        <el-form-item label="获取类别" prop="type" label-width="120px">
          <el-select v-model="form.type" placeholder="请选择证书类别" filterable clearable>
            <el-option v-for="(item, index) in ccieTypeOptions" :key="index" :label="item.name"
                       :value="item.val" :disabled="item.disabled"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-show="form.type === 0" prop="typeName" label="自定义类别名称" label-width="120px">
          <el-input v-model="form.typeName" placeholder="请输入自定义的类别名称" :style="{width: '26%'}"/>
        </el-form-item>
        <el-form-item label="获奖时间" prop="ccieGetTime" label-width="120px">
          <el-date-picker clearable
            v-model="form.ccieGetTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择获奖时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="经验总结" prop="ccieThink" label-width="120px">
          <Editor v-model="form.ccieThink" :minHeight="200"></Editor>
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
import { listCcie, getCcie, delCcie, addCcie, updateCcie, applyInclusion, cancelInclusion  } from "@/api/own/ccie";
import { getCcieTypeMenu } from "@/api/menu";
import { getInclusionFlagName } from '@/utils/webtool.js'

export default {
  name: "Ccie",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      //选中内容名称
      names: '',
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // ZfCcie表格数据
      ccieList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显示预览弹出层
      previewOpen: false,
      // 经验总结时间范围
      daterangeCcieGetTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ccieImg: null,
        ccieGetTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ccieName: [
          { required: true, message: "证书名称不能为空", trigger: "blur" }
        ],
        ccieImg: [
          { required: true, message: "获奖证书不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "获奖类别不能为空", trigger: "blur" }
        ],
        ccieGetTime: [
          { required: true, message: "获奖时间不能为空", trigger: "blur" }
        ],
      },
      //当前思考总结的HTML
      curThinkHTML: '',
      ccieTypeOptions: [],
      // 是否申请收录
      showApply: true,
      chooseInclusion: false,
    };
  },
  watch: {
    // 监视 form 对象中的 type 属性
    'form.type': function(newVal, oldVal) {
      console.log(`form.type changed from ${oldVal} to ${newVal}`);
      // 处理分自定义的时候，typeName根据选项的name来进行取值
      if (this.form.type !== 0) {
        for (let i = 0; i < this.ccieTypeOptions.length; i++) {
          if (this.form.type === this.ccieTypeOptions[i].val) {
            this.form.typeName = this.ccieTypeOptions[i].name
          }
        }
      }
      // 必要条件如下：
      // 不能清空typeName情况：编辑界面打开时(undefined=>0)
      // 需要清空typeName情况：其他非0状态切换到0时 (1=>0)
      if (oldVal !== undefined && newVal === 0) {
        this.form.typeName = ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询ZfCcie列表 */
    getList() {
      this.loading = true;
      //构造查询对象
      this.queryParams.params = {};
      if (null != this.daterangeCcieGetTime && '' != this.daterangeCcieGetTime) {
        this.queryParams.params["beginCcieGetTime"] = this.daterangeCcieGetTime[0];
        this.queryParams.params["endCcieGetTime"] = this.daterangeCcieGetTime[1];
      }
      listCcie(this.queryParams).then(response => {
        // console.log ("response=>",response)
        this.ccieList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        ccieId: null,
        ccieName: null,
        ccieImg: null,
        ccieGetTime: null,
        ccieThink: null,
        userId: null
      };
      this.showApply = true
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.daterangeCcieGetTime = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.ccieId)
      //构造出删除项的提示内容
      let names = '';
      selection.forEach((ele,index)=>{
        if (index == selection.length-1) {
          names += ele.ccieName
        }else{
          names += ele.ccieName + ","
        }
      })
      this.names = names
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    // 获取证书类别菜单
    getCcieMenuOptions() {
      // 获取证书类别菜单
      getCcieTypeMenu().then(data => {
        console.log('getCcieTypeMenu:', data)
        this.ccieTypeOptions = data.data
        this.ccieTypeOptions.push({
          name: '自定义类别',
          val: 0
        })
      }).catch(err => console.log(err))
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true;
      this.title = "添加获奖证书";
      this.getCcieMenuOptions()
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();//重置表单
      const ccieId = row.ccieId || this.ids
      getCcie(ccieId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改获奖证书"
        // 审核通过状态
        if (response.data.inclusionFlag === 3) {
          this.showApply = false
        }
        // 申请状态
        if (response.data.inclusionFlag === 1) {  // 申请审核状态
          this.chooseInclusion = true
        }
      });
      this.getCcieMenuOptions()
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 对提交的表单进行处理
          this.processForm()
          // console.log('提交的form表单为：', this.form)
          // 使用表单是否有id来作为判断条件
          if (this.form.ccieId != null) {
            updateCcie(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false
              this.reset()
              this.getList()
            });
          } else {
            addCcie(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false
              this.reset();
              this.getList()
            })
          }
        }
      })
    },
    // 对要提交的表单进行处理
    processForm() {
      // 1、处理是否申请
      if (this.showApply) {
        this.form.inclusionFlag = this.chooseInclusion ? 1 : 0
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ccieIds = row.ccieId || this.ids;
      const names = row.ccieName || this.names
      this.$modal.confirm('是否确认删除证书名称为"' + names + '"？').then(function() {
        return delCcie(ccieIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 预览思考总结
    previewThink(row){
      this.previewOpen = true;
      this.curThinkHTML = row.ccieThink
    },
    handlePreviewClose(){
      this.previewOpen = false;
    },
    // 根据flag获取指定的收录状态
    getInclusionFlagName(inclusionFlag) {
      return getInclusionFlagName(inclusionFlag)
    },
    // 申请收录
    handleApplyInclusion(row) {
      this.$modal.confirm('是否申请证书名称为"' + row.ccieName + '"收录到系统网站展示？').then(() => {
        const form = {
          ccieId: row.ccieId
        }
        // 发起申请请求
        applyInclusion(form).then(() => {
          this.$modal.msgSuccess('申请成功！')
          this.open = false
          this.getList()
        }).catch((err) => console.log(err))
      })
    },
    // 取消收录
    handleCancelInclusion(row) {
      this.$modal.confirm('是否取消申请证书名称为"' + row.ccieName + '"收录到系统网站展示？').then(() => {
        const form = {
          ccieId: row.ccieId
        }
        cancelInclusion(form).then(() => {
          this.$modal.msgSuccess('取消成功！')
          this.open = false
          this.getList()
        }).catch((err) => console.log(err))
      })
    }
    /** 导出按钮操作 */
    // handleExport() {
    //   this.download('own/ccie/export', {
    //     ...this.queryParams
    //   }, `获奖证书_${new Date().getTime()}.xlsx`)  //对应的文件名为获奖证书_时间戳
    // }
  }
};
</script>

<style >

  /*调整预览的样式*/
  .previewDialog .ql-toolbar.ql-snow{
    display: none;
  }

  .previewDialog .editor{
    border: 1px solid #ccc !important;
  }
</style>
