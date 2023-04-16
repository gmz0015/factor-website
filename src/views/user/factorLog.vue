<template>
    <div class="app-container">
        <el-card class="box-card">
            <div class="filter-container">
                <el-button :loading="listLoading" class="filter-item" type="primary" plain icon="el-icon-refresh"
                    @click="handleRefresh" size="small">
                    Refresh
                </el-button>
            </div>

            <el-table :key="tableKey" v-loading="listLoading" :data="list" border fit highlight-current-row
                style="width: 100%;" @sort-change="sortChange" height="calc(100vh - 275px)">
                <el-table-column type="expand">
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
                </el-table-column>

                <el-table-column label="ID" prop="id" sortable="custom" align="center" min-width="180"
                    :class-name="getSortClass('id')">
                    <template slot-scope="{row}">
                        <span>{{ row.id }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="UserName" min-width="150px" align="center">
                    <template slot-scope="{row}">
                        <span>{{ row.username }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="Score" class-name="status-col" min-width="100">
                    <template slot-scope="{row}">
                        {{ row.score }}
                    </template>
                </el-table-column>
                <el-table-column label="Online Time" class-name="status-col" min-width="120">
                    <template slot-scope="{row}">
                        {{ row.onlineTime }}
                    </template>
                </el-table-column>
                <el-table-column label="Date" min-width="150px" align="center">
                    <template slot-scope="{row}">
                        <span>{{ row.time | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="Operation" align="center" min-width="100" class-name="small-padding fixed-width">
                    <template slot-scope="{ row, $index }">
                        <el-popconfirm title="Confirm deleting?" @confirm="handleDelete(row, $index)">
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
    </div>
</template>
  
<script>
import {
    factorLogList,
    factorDeleteLog
} from "@/api/factor";
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
    name: 'ComplexTable',
    components: { Pagination },
    directives: {},
    filters: {
        statusFilter(status) {
            const statusMap = {
                0: 'primary',
                1: 'warning'
            }
            return statusMap[status]
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
                sort: '+id'
            },
            statusMap: {
                0: 'Login',
                1: 'Logout'
            },
        }
    },
    created() {
        this.getList()
    },
    methods: {
        getList() {
            this.listLoading = true
            factorLogList(this.listQuery).then(response => {
                const { result } = response
                console.log(result)
                this.list = result.records
                this.total = parseInt(result.total)

                this.listLoading = false
            })
        },
        handleRefresh() {
            this.list = []
            this.getList()
        },
        handleFilter() {
            this.listQuery.page = 1
            this.getList()
        },
        handleModifyStatus(row, status) {
            this.$message({
                message: 'Success',
                type: 'success'
            })
            row.status = status
        },
        sortChange(data) {
            const { prop, order } = data
            if (prop === 'id') {
                this.sortByID(order)
            }
        },
        sortByID(order) {
            if (order === 'ascending') {
                this.listQuery.sort = '+id'
            } else {
                this.listQuery.sort = '-id'
            }
            this.handleFilter()
        },
        resetTemp() {
            this.temp = {
                id: undefined,
                importance: 1,
                remark: '',
                timestamp: new Date(),
                title: '',
                status: 'published',
                type: ''
            }
        },
        handleCreate() {
            this.resetTemp()
            this.dialogStatus = 'create'
            this.dialogFormVisible = true
            this.$nextTick(() => {
                this.$refs['dataForm'].clearValidate()
            })
        },
        createData() {
            this.$refs['dataForm'].validate((valid) => {
                if (valid) {
                    this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
                    this.temp.author = 'vue-element-admin'
                    createArticle(this.temp).then(() => {
                        this.list.unshift(this.temp)
                        this.dialogFormVisible = false
                        this.$notify({
                            title: 'Success',
                            message: 'Created Successfully',
                            type: 'success',
                            duration: 2000
                        })
                    })
                }
            })
        },
        handleUpdate(row) {
            let tasks = []
            tasks.push(getRoles())
            tasks.push(userInfo({ 'id': row.id }))
            Promise.all(tasks).then(
                (results) => {
                    console.log('getRoles', results[0])
                    this.roleList = results[0].result

                    console.log('userInfo', results[1])
                    this.temp = results[1].result
                    this.dialogFormVisible = true
                }
            )

            // this.temp = Object.assign({}, row) // copy obj
            // this.temp.timestamp = new Date(this.temp.timestamp)
            this.dialogStatus = 'update'

            this.$nextTick(() => {
                this.$refs['dataForm'].clearValidate()
            })

        },
        updateData() {
            this.$refs['dataForm'].validate((valid) => {
                if (valid) {
                    const tempData = Object.assign({}, this.temp)
                    // tempData.timestamp = +new Date(tempData.timestamp) // change Thu Nov 30 2017 16:41:05 GMT+0800 (CST) to 1512031311464
                    console.log('tempData', tempData)
                    userEdit(tempData).then(() => {
                        this.dialogFormVisible = false
                        this.$notify({
                            title: 'Success',
                            message: 'Update Successfully',
                            type: 'success',
                            duration: 2000
                        })
                        this.getList()
                    })
                }
            })
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
        formatJson(filterVal) {
            return this.list.map(v => filterVal.map(j => {
                if (j === 'timestamp') {
                    return parseTime(v[j])
                } else {
                    return v[j]
                }
            }))
        },
        getSortClass: function (key) {
            const sort = this.listQuery.sort
            return sort === `+${key}` ? 'ascending' : 'descending'
        }
    }
}
</script>
  
  
<style lang="scss" scoped>
.app-container {
    padding: 16px;
    background-color: rgb(240, 242, 245);
    position: relative;
}
</style>
  