<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="证书名称" prop="ccieName">
        <el-input
          v-model="queryParams.ccieName"
          placeholder="请输入证书名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="realName">
        <el-input
          v-model="queryParams.realName"
          placeholder="请输入姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年级" prop="gradeId">
        <el-select v-model="queryParams.gradeId" placeholder="请选择年级" filterable clearable
                   :style="{width: '100%'}" @keyup.enter.native="handleQuery">
          <el-option v-for="(item, index) in gradeIdOptions" :key="index" :label="item.gradeNum"
                     :value="item.gradeId" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="专业" prop="majorId">
        <el-select v-model="queryParams.majorId" placeholder="请选择专业" filterable clearable
                   :style="{width: '100%'}" @keyup.enter.native="handleQuery">
          <el-option v-for="(item, index) in majorIdOptions" :key="index" :label="item.majorName"
                     :value="item.majorId" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
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
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['team:ccie:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
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
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
        >导出</el-button>
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
      <el-table-column label="获奖时间" align="center" prop="ccieGetTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.ccieGetTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="证书名称" align="center" prop="ccieName" width="200"/>
      <el-table-column label="证书类别" align="center" prop="typeName" width="200"/>
<!--      <el-table-column label="获奖证书主键id" align="center" prop="ccieId" />-->
      <el-table-column label="姓名" align="center" prop="realName" width="120"/>
      <el-table-column label="年级" align="center" prop="gradeNum" width="150"/>
      <el-table-column label="专业" align="center" prop="majorName" width="180"/>
      <el-table-column label="获奖证书" align="center" prop="ccieImg" width="200">
        <template slot-scope="scope">
          <image-preview :src="scope.row.ccieImg" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasRole="['manage', 'teacher']"
          >修改</el-button>
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--          >删除</el-button>-->
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

    <!-- 添加或修改ZfCcie对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="证书名称" prop="ccieName">
          <el-input v-model="form.ccieName" placeholder="请输入证书名称" />
        </el-form-item>
        <el-form-item label="获取类别" prop="type" label-width="120px">
          <el-select v-model="form.type" placeholder="请选择证书类别" filterable clearable>
            <el-option v-for="(item, index) in ccieTypeOptions" :key="index" :label="item.name"
                       :value="item.val" :disabled="item.disabled"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-show="form.type === 0" prop="typeName" label="自定义类别名称" label-width="120px">
          <el-input v-model="form.typeName" placeholder="请输入自定义的类别名称" :style="{width: '60%'}"/>
        </el-form-item>
        <el-form-item label="获奖时间" prop="ccieGetTime" label-width="120px">
          <el-date-picker clearable
                          v-model="form.ccieGetTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择获奖时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-hasRole="['manage', 'teacher']">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getCcie, delCcie, updateCcie } from "@/api/own/ccie";
import { listCcie } from "@/api/team/ccie";
import { getCcieTypeMenu } from "@/api/menu";
import infoApi from "@/api/own/info";

export default {
  name: "Ccie",
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
      // ZfCcie表格数据
      ccieList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 用户id时间范围
      daterangeCcieGetTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ccieName: null,
        ccieGetTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ccieName: [
          { required: true, message: "证书名称不能为空", trigger: "blur" }
        ],
      },
      //菜单信息
      //[{},{}]格式
      majorIdOptions: [],
      gradeIdOptions: [],
      //提示信息
      name: "",
      ccieTypeOptions: []
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
    this.getList();
    this.getMenu();
  },
  methods: {
    /** 查询ZfCcie列表 */
    getList() {
      this.loading = true
      this.queryParams.params = {};
      if (null != this.daterangeCcieGetTime && '' != this.daterangeCcieGetTime) {
        this.queryParams.params["beginCcieGetTime"] = this.daterangeCcieGetTime[0];
        this.queryParams.params["endCcieGetTime"] = this.daterangeCcieGetTime[1];
      }
      listCcie(this.queryParams).then(response => {
        this.ccieList = response.rows;
        this.total = response.total;
        this.loading = false;
      })
    },
    // 查询菜单信息
    getMenu() {
      // 获取证书信息
      infoApi.getMenu().then(data => {
        // console.log("getMenus:",data)
        let results = data.data
        this.majorIdOptions = results.majors
        this.gradeIdOptions = results.grades
        this.gradeIdOptions.forEach((grade)=>grade.gradeNum = grade.gradeNum + "级")
      }).catch(err => console.log(err))
    },
    // 获取证书类别菜单
    getCcieMenuOptions() {
      // 获取证书类别菜单
      getCcieTypeMenu().then(data => {
        // console.log('getCcieTypeMenu:', data)
        this.ccieTypeOptions = data.data
        this.ccieTypeOptions.push({
          name: '自定义类别',
          val: 0
        })
      }).catch(err => console.log(err))
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
      this.resetForm("form");
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
      this.single = selection.length!==1
      this.multiple = !selection.length
      //统计选中的名称
      const arr = []
      selection.forEach((sele)=>{
        arr.push(`${sele.realName}的【${sele.ccieName}】证书`)
      })
      this.name = arr.join(',')
    },
    /** 新增按钮操作 */
    // handleAdd() {
    //   this.reset();
    //   this.open = true;
    //   this.title = "添加ZfCcie";
    // },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const ccieId = row.ccieId || this.ids
      getCcie(ccieId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改证书";
      });
      this.getCcieMenuOptions()
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.ccieId != null) {
            updateCcie(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
              this.reset()
            });
          }
          // else {
          //   addCcie(this.form).then(response => {
          //     this.$modal.msgSuccess("新增成功");
          //     this.open = false;
          //     this.getList();
          //     this.reset()
          //   });
          // }
        }
      });
    },
    /** 删除按钮操作 */
    // handleDelete(row) {
    //   console.log("row=>",row)
    //   if (row.ccieId) {
    //     this.name = `${row.realName}的【${row.ccieName}】证书`
    //   }
    //   const ccieIds = row.ccieId || this.ids;
    //   this.$modal.confirm('是否确认删除' + this.name + '"的数据项？').then(function() {
    //     return delCcie(ccieIds);
    //   }).then(() => {
    //     this.getList();
    //     this.$modal.msgSuccess("删除成功");
    //   }).catch(() => {});
    // },
    /** 导出按钮操作 */
    handleExport() {
      this.download('team/ccie/export', {
        ...this.queryParams
      }, `证书统计_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
