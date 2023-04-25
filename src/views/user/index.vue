<template>
  <div class="app-container">
    <el-card class="box-card">
      <div class="filter-container">
        <!-- <el-button class="filter-item" style="margin-left: 10px" type="primary" icon="el-icon-edit" @click="handleCreate"
          size="small">
          Add
        </el-button> -->
        <el-button :loading="listLoading" class="filter-item" type="primary" plain icon="el-icon-refresh"
          @click="handleRefresh" size="small">
          Refresh
        </el-button>
      </div>

      <el-table :key="tableKey" v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%"
        @sort-change="sortChange" height="calc(100vh - 275px)">
        <el-table-column label="ID" prop="id" sortable="custom" align="center" min-width="150"
          :class-name="getSortClass('id')">
          <template slot-scope="{ row }">
            <span>{{ row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column label="UserName" min-width="150px" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.username }}</span>
          </template>
        </el-table-column>
        <el-table-column label="Status" class-name="status-col" min-width="100">
          <template slot-scope="{ row }">
            <el-tag :type="row.status | statusFilter">
              {{ statusMap[row.status] }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Online Time(min)" class-name="status-col" min-width="100">
          <template slot-scope="{ row }">
            <span>{{ row.onlineTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="Operation" align="center" min-width="230" class-name="small-padding fixed-width">
          <template slot-scope="{ row, $index }">
            <el-button type="primary" size="mini" @click="handleUpdate(row)" style="margin-right: 20px;">
              Edit
            </el-button>
            <el-popconfirm title="Confirm Deleting" @confirm="handleDelete(row, $index)">
              <el-button slot="reference" v-if="row.status != 'deleted'" size="mini" type="danger">
                Delete
              </el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>

      <pagination v-show="total > 0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
        @pagination="getList" />
    </el-card>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="right" label-width="100px"
        style="width: 400px; margin-left: 50px">
        <el-form-item label="UserName" prop="username">
          <el-input v-model="temp.username" />
        </el-form-item>
        <el-form-item label="Status">
          <el-radio-group v-model="temp.status" size="small">
            <el-radio-button label="0">disable</el-radio-button>
            <el-radio-button label="1">enable</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="role" prop="roles">
          <el-checkbox-group v-model="temp.roles" size="small">
            <el-checkbox-button v-for="item in roleList" :key="item.id" :label="item.id" :disabled="!item.status">
              {{ item.name }}
            </el-checkbox-button>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false"> Cancel </el-button>
        <el-button type="primary" @click="dialogStatus === 'create' ? createData() : updateData()">
          Submit
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  userList,
  userInfo,
  userEdit,
  userDelete,
  userRegister,
} from "@/api/user";
import { getRoles } from "@/api/role";
import { parseTime } from "@/utils";
import Pagination from "@/components/Pagination"; // secondary package based on el-pagination

const calendarTypeOptions = [
  { key: "CN", display_name: "China" },
  { key: "US", display_name: "USA" },
  { key: "JP", display_name: "Japan" },
  { key: "EU", display_name: "Eurozone" },
];

// arr to obj, such as { CN : "China", US : "USA" }
const calendarTypeKeyValue = calendarTypeOptions.reduce((acc, cur) => {
  acc[cur.key] = cur.display_name;
  return acc;
}, {});

export default {
  name: "ComplexTable",
  components: { Pagination },
  directives: {},
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: "success",
        0: "danger",
      };
      return statusMap[status];
    },
    typeFilter(type) {
      return calendarTypeKeyValue[type];
    },
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: "+id",
      },
      importanceOptions: [1, 2, 3],
      calendarTypeOptions,
      sortOptions: [
        { label: "ID Ascending", key: "+id" },
        { label: "ID Descending", key: "-id" },
      ],
      statusOptions: ["published", "draft", "deleted"],
      statusMap: {
        0: "disable",
        1: "enable",
      },
      showReviewer: false,
      temp: {
        id: undefined,
        importance: 1,
        remark: "",
        timestamp: new Date(),
        title: "",
        type: "",
        status: "published",
      },
      roleList: [
        {
          id: 1,
          name: "admin",
          status: 1,
        },
        {
          id: 2,
          name: "visitor",
          status: 1,
        },
      ],
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "Edit",
        create: "Add",
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        username: [
          {
            required: true,
            message: "Please input username",
            trigger: "change",
          },
        ],
        status: [
          {
            required: true,
            message: "Please select status",
            trigger: "change",
          },
        ],
        roles: [
          { required: true, message: "Please select roles", trigger: "blur" },
        ],
      },
      downloadLoading: false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.listLoading = true;
      userList(this.listQuery).then((response) => {
        const { result } = response;
        this.list = result.records;
        this.total = parseInt(result.total);

        this.listLoading = false;
      });
    },
    handleRefresh() {
      this.list = [];
      this.getList();
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: "Success",
        type: "success",
      });
      row.status = status;
    },
    sortChange(data) {
      const { prop, order } = data;
      if (prop === "id") {
        this.sortByID(order);
      }
    },
    sortByID(order) {
      if (order === "ascending") {
        this.listQuery.sort = "+id";
      } else {
        this.listQuery.sort = "-id";
      }
      this.handleFilter();
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        importance: 1,
        remark: "",
        timestamp: new Date(),
        title: "",
        status: "published",
        type: "",
      };
    },
    handleCreate() {
      this.resetTemp();
      // getRoles().then((response) => {
      //   console.log("getRoles", response);
      //   this.roleList = response.result;

      //   this.dialogFormVisible = true;
      // });
      this.dialogStatus = "create";

      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          userRegister(this.loginForm).then(
            (ret) => {
              this.$message({
                message: "Register success",
                type: "success",
              });
            },
            (err) => {
              this.$message.error("Register fail");
            }
          );
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    handleUpdate(row) {
      let tasks = [];
      tasks.push(getRoles());
      tasks.push(userInfo({ id: row.id }));
      Promise.all(tasks).then((results) => {
        console.log("getRoles", results[0]);
        this.roleList = results[0].result;

        console.log("userInfo", results[1]);
        this.temp = results[1].result;
        this.dialogFormVisible = true;
      });

      // this.temp = Object.assign({}, row) // copy obj
      // this.temp.timestamp = new Date(this.temp.timestamp)
      this.dialogStatus = "update";

      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    updateData() {
      this.$refs["dataForm"].validate((valid) => {
        if (valid) {
          const tempData = Object.assign({}, this.temp);
          // tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
          console.log("tempData", tempData);
          userEdit(tempData).then(() => {
            this.dialogFormVisible = false;
            this.$notify({
              title: "Success",
              message: "Update Successfully",
              type: "success",
              duration: 2000,
            });
            this.getList();
          });
        }
      });
    },
    handleDelete(row, index) {
      console.log("handleDelete", row, index);
      userDelete({ id: row.id }).then((ret) => {
        this.$notify({
          title: "Success",
          message: "Delete Successfully",
          type: "success",
          duration: 2000,
        });
        this.getList();
      });
    },
    formatJson(filterVal) {
      return this.list.map((v) =>
        filterVal.map((j) => {
          if (j === "timestamp") {
            return parseTime(v[j]);
          } else {
            return v[j];
          }
        })
      );
    },
    getSortClass: function (key) {
      const sort = this.listQuery.sort;
      return sort === `+${key}` ? "ascending" : "descending";
    },
  },
};
</script>

<style lang="scss" scoped>
.app-container {
  padding: 16px;
  background-color: rgb(240, 242, 245);
  position: relative;
}
</style>
