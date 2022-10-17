<template>
  <div class="home">
    <el-button type="primary" @click="dialogVisible=true">添加</el-button>
    <el-table
            :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
            style="width: 100%">
      <el-table-column
              label="编号"
              prop="id">
      </el-table-column>
      <el-table-column
              label="姓名"
              prop="name">
      </el-table-column>
      <el-table-column
              label="年龄"
              prop="age">
      </el-table-column>
      <el-table-column
              label="密码"
              prop="password">
      </el-table-column>

      <el-table-column
              label="地址"
              prop="address">
      </el-table-column>
      <el-table-column
              label="注册时间"
              prop="entryDate">
      </el-table-column>
      <el-table-column
              align="right">
        <template slot="header" slot-scope="scope">
          <el-input
                  v-model="search"
                  size="mini"
                  placeholder="输入关键字搜索"/>
        </template>
        <template slot-scope="scope">
          <el-button
                  size="mini"
                  @click="modifyData(scope.row)"
                 >修改</el-button>
          <el-popconfirm
                  @confirm="del(scope.row.id)"
                  title="这是一段内容确定删除吗？"
          >
            <el-button size="mini"
                       type="danger"
                       slot="reference">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="arr"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalNum">
    </el-pagination>
        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="30%"
                >
          <el-form :model="ruleForm" status-icon  ref="ruleForm" label-width="100px" class="demo-ruleForm">
            <el-input type="text" v-model="ruleForm.id" v-show="false"></el-input>
            <el-form-item label="姓名" prop="name">
              <el-input type="text" v-model="ruleForm.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="年龄" prop="age">
              <el-input type="text" v-model.number="ruleForm.age" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="地址" prop="address">
              <el-input v-model="ruleForm.address"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" v-model="ruleForm.password"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
          </span>
        </el-dialog>
  </div>
</template>

<script>

export default {
  data() {
    return {
      tableData: [],
      search: '',
      url:'http://localhost:8081/a',
      pageNum:1,
      pageSize:5,
      dialogVisible:false,
      ruleForm:{},
      totalNum:0,
      arr:[2,5,10,15]
    }
  },
  methods: {
  /*** 删除 **/
    del(id){
      let that = this
      this.axios.post(this.url+'/del?id='+id).then(function (res) {
          if (res.data.success){
            that.$message({
              type:'success',
              message:res.data.data
            })
            that.getList(that.pageNum);
          }else{
            that.$message({
              type:'error',
              message:res.data.message
            })
          }
      }).catch(function (res) {
          console.log(res)
      })
    },
    getList(){
      let that = this
      this.axios.post(this.url+'/list',{
        pageNum:this.pageNum,
        pageSize:this.pageSize
      }).then(function (res) {
        console.log(res)
        that.tableData=res.data.data.records
        that.totalNum=res.data.data.total
      }).catch(function (error) {
        alert('error!')
        console.log(error)
      })
    },
    submitForm(){
      console.log(this.ruleForm)
      let that = this
      if (!this.ruleForm.id){
        this.axios.post(this.url+'/add',this.ruleForm).then(function (res) {
          if (res.data.success){
            that.$message({
              type:'success',
              message:res.data.data
            })
            that.getList(that.pageNum)
            that.ruleForm={}
            that.dialogVisible=false
          }else{
            that.$message({
              type:'error',
              message:res.data.message
            })
          }
        }).catch(function(error){
          alert('error!')
        })
      }else{
        this.axios.post(this.url+'/modify',this.ruleForm).then(function (res) {
          if (res.data.success){
            that.$message({
              type:'success',
              message:res.data.data
            })
            that.getList(that.pageNum)
            that.ruleForm={}
            that.dialogVisible=false
          }else{
            that.$message({
              type:'error',
              message:res.data.message
            })
          }
        }).catch(function(error){
          alert('error!')
        })
      }
    },
    modifyData(user){
      // 将数据显示,并且不会编辑时提交
      this.ruleForm=JSON.parse(JSON.stringify(user))
      this.dialogVisible=true

    },

    /** 分页 **/
    handleSizeChange(val){
      this.pageSize=val
      this.getList();
    },
    handleCurrentChange(val){
      this.pageNum = val;
      this.getList()
    }
  },
  created() {
    this.getList();
  }
}
</script>
