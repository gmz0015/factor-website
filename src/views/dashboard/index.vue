<template>
  <div class="dashboard-editor-container">
    <el-tabs class="input-card" type="border-card">
      <el-tab-pane label="Text">
        <el-form ref="formData" :model="formData" label-width="120px" size="mini">
          <el-form-item v-for="(content, index) in formData.contents" :label="'Content - ' + (index + 1) + ': '"
            :key="index" :prop="'contents.' + index + '.value'" :rules="{
                required: true,
                message: 'Content cannot be empty',
                trigger: 'blur',
              }">
            <el-input v-model="content.value" type="textarea" :autosize="{ minRows: 1, maxRows: 4 }"
              placeholder="Please input content" :show-word-limit="true"
              style="width: 90%; margin-right: 10px"></el-input>
            <el-button @click.prevent="removeContent(index)">
              Remove
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitText()" size="mini" :loading="submitButtonLoading">
              Submit
            </el-button>
            <el-button @click="addContent">Add</el-button>
            <el-button @click="resetForm()">Reset</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="Image">
        <el-upload class="upload-demo" ref="upload" action="" :on-remove="handleRemove" :file-list="fileList"
          :auto-upload="false" accept=".jpg, .jpeg, .png" :limit="10">

          <el-button slot="trigger" size="small" type="primary">Select images</el-button>
          <el-button style="margin-left: 10px" size="small" type="success" @click="submitUpload"
            :loading="submitButtonLoading">Submit</el-button>
          <div slot="tip" class="el-upload__tip">
            Only accept jpg/jpeg/png, and maximum 500kb.
          </div>
        </el-upload>
      </el-tab-pane>
    </el-tabs>
    <el-card class="table-card">
      <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%"
        height="calc(100vh - 458px)">
        <!-- <el-table-column type="expand">
          <template slot-scope="props">
            <el-descriptions :column="4" style="margin-left:20px !important;">
              <el-descriptions-item v-for="(data, index) in props.row.datas">
                <template slot="label">
                  <div v-if="data.type == 0">图片 - {{ index + 1 }}</div>
                  <div v-else-if="data.type == 1">文本 - {{ index + 1 }}</div>
                </template>
                <div v-if="data.type == 0">
                  <img height="200px" :src="'data:image/png;base64,' + data.image" alt="">
                </div>
                <div v-else-if="data.type == 1">
                  {{ data.content }}
                </div>
              </el-descriptions-item>
            </el-descriptions>
          </template>
        </el-table-column> -->

        <el-table-column label="Rank" type="index" width="80" align="center"></el-table-column>
        <el-table-column prop="username" align="center" label="UserName" min-width="120"></el-table-column>
        <el-table-column label="Online Time(min)" min-width="150px" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.totalOnlineTime | numFilter }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="totalScore" align="center" label="Score" min-width="100"></el-table-column>
        <el-table-column label="Date" min-width="150px" align="center">
          <template slot-scope="{ row }">
            <span>{{ row.time | parseTime("{y}-{m}-{d} {h}:{i}") }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="Operation" align="center" min-width="100" class-name="small-padding fixed-width">
          <template slot-scope="{ row, $index }">
            <el-popconfirm title="Confirm deleting?" @onConfirm="handleDelete(row, $index)">
              <el-button slot="reference" v-if="row.status != 'deleted'" size="mini" type="danger">
                Delete
              </el-button>
            </el-popconfirm>
          </template>
        </el-table-column> -->
      </el-table>

      <pagination v-show="total > 0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
        @pagination="getList" />
    </el-card>

    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
  </div>
</template>

<script>
import {
  factorRankList,
  factorAddLogText,
  factorAddLogImage,
  factorDeleteLog,
} from "@/api/factor";
import { parseTime } from "@/utils";
import Pagination from "@/components/Pagination"; // secondary package based on el-pagination
import permission from '@/directive/permission/index.js' // 权限判断指令

export default {
  name: "Dashboard",
  components: { Pagination },
  directives: { permission },
  filters: {
    numFilter(value) {
      // 截取当前数据到小数点后两位
      let realVal = parseFloat(value).toFixed(2)
      return realVal
    }
  },
  data() {
    return {
      submitButtonLoading: false,
      listLoading: true,
      list: [],
      listQuery: {
        page: 1,
        limit: 20,
        importance: undefined,
        title: undefined,
        type: undefined,
        sort: "+id",
      },
      total: 0,
      formData: {
        image: "",
        contents: [],
      },
      rules: {
        content: [
          { required: true, message: "请输入文本内容", trigger: "blur" },
        ],
      },
      fileList: [],
      dialogImageUrl: '',
      dialogVisible: false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.list = [];
      this.listLoading = true;
      factorRankList(this.listQuery).then((response) => {
        console.log('factorRankList', response)
        const { result } = response;
        this.list = result;
        // this.list = result.records;
        // this.total = parseInt(result.total);
        this.listLoading = false;
      });
    },
    handleRefresh() {
      this.getList();
    },
    // 重置表格
    resetForm() {
      this.$refs["formData"].resetFields();
    },
    // 删除一行
    removeContent(index) {
      if (index !== -1) {
        this.formData.contents.splice(index, 1);
      }
    },
    // 新增一行
    addContent() {
      this.formData.contents.push({
        value: "",
      });
    },
    submitText() {
      this.submitButtonLoading = true
      this.$refs["formData"].validate((valid) => {
        if (valid) {
          this.formData.type = 1;
          let texts = [];
          console.log(this.formData.contents);
          for (let text of this.formData.contents) {
            texts.push(text.value);
          }
          factorAddLogText(texts).then(() => {
            this.$notify({
              title: "Success",
              message: "提交成功",
              type: "success",
              duration: 2000,
            });
            this.getList();

            this.submitButtonLoading = false
          });
        } else {
          this.submitButtonLoading = false
        }
      });
    },
    handleDelete(row, index) {
      console.log("handleDelete", row, index);
      factorDeleteLog({ id: row.id }).then((ret) => {
        this.$notify({
          title: "Success",
          message: "删除成功",
          type: "success",
          duration: 2000,
        });
        this.getList();
      });
    },
    submitUpload() {
      this.submitButtonLoading = true
      console.log(this.fileList);
      console.log(this.$refs.upload);
      // this.$refs.upload.submit();
      // 上传数据包
      let param = new FormData();
      let files = [];
      for (let uploadFile of this.$refs.upload.uploadFiles) {
        param.append("multipartFiles", uploadFile.raw);
      }
      param.append("type", 0);
      let config = {
        headers: { "Content-Type": "multipart/form-data" },
        onUploadProgress: (progressEvent) => {
          //progressEvent.loaded:已上传文件大小
          //progressEvent.total:被上传文件的总大小
          let complete = (
            (progressEvent.loaded / progressEvent.total) *
            100
          ).toFixed(0);
          this.submitProgress = new Number(complete);
        },
      };

      this.submitButtonLoading = false
      return factorAddLogImage(config, param);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
  },
};
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .input-card {
    margin-bottom: 20px;
  }

  .table-card {}
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
