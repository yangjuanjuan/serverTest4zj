package com.zjlab.qa.common;

public enum ApiResultCode {

    SUCCESS(100, "成功!"),
    PARAM_ERROR(201, "参数错误!"),
    TABLE_EMPTY(202, "当前节点数据表为空!!!!"),
    NO_AUTH(301, "未经授权访问!"),
    UPLOAD_ERROR(413, "文件上传失败!"),
    DOWNLOAD(412, "文件下载失败!"),
    FORBIDDEN_CONNECT(414, "兄弟节点禁止连线"),
    SYS_ERROR(500, "服务端异常!"),
    DATASET_NAME(411, "数据集名称重复，请重新命名!"),
    DATASET_INFO_NULL(415, "符合条件的数据集信息不存在"),
    DATASET_ACT_UNREAD_NULL(416, "不存在未读信息"),
    DATASET_ACT_NULL(417, "不存在数据增删操作"),
    DATA_NULL(418, "数据不存在"),
    CONTENT_ERROR(700,"上传文件内容为空，请重新选择文件!"),
    FILE_SUPPORT_ERROR(701,"当前仅支持csv和excel格式文件!"),
    JSON_FORMAT_ERROR(702,"JSON格式错误"),
    URL_ERROR(703,"url不存在"),
    REGISTER_UDF_ERROR(600,"注册UDF失败"),
    UPDATE_UDF_ERROR(601, "更新UDF失败"),
    DROP_UDF_ERROR(602, "删除UDF失败"),
    UDF_DEFINE_ERROR(603, "自定义算子输入参数和输出参数定义错误"),
    DEFINE_UDF_DUPLICATE(604, "自定义算子名或者函数名重复"),
    EXTRACT_FUNCTION_FAIL(605, "抽取函数名失败"),
    TASK_VALIDATE_FAIL(606, "feature_cols存在非数值类型校验失败!!!"),
    DANGER_PATTERN(607, "脚本包括危险模式!"),
    ALLOW_ONE_PARENT(608, "只允许唯一父节点"),
    ALLOW_TWO_PARENT(609, "只允许两个父节点"),
    ALLOW_THREE_PARENT(610, "只允许三个父节点"),
    ALLOW_PARENT_ERROR(611, "节点连线超过最大限制"),
    LINE_DUP_ERROR(612, "不允许重复连线"),
    SQL_NOT_SUPPORT(613, "SQL算子只允许select语句"),
    SQL_NOT_PARENT(614, "SQL算子必须有父节点"),
    SQL_TABLE_PERMISSION(615, "SQL关联表只允许来自父节点"),
    TASK_FORBIDDEN_COPY(616, "禁用节点不允许复制!"),
    TASK_FORBIDDEN_DEL(617, "禁用节点不允许删除"),
    NUMERIC_VALIDATE_FAIL(618, "数值类型配置校验失败，请根据右侧问号提示输入!"),
    DIMENSION_REDUCTION_VALIDATE_FAIL(619, "降维维度必须是大于0的整数，且小于等于特征列数!"),
    ISO_FOREST_PARAM_SAMPLES_ILLEGAL(620, "样本比例(max_samples)取值范围(0,1]!"),
    ISO_FOREST_PARAM_CONTAMINATION_ILLEGAL(621, "异常点比例(contamination)取值范围(0,0.5)!"),
    SAMPLE_PARAM_COUNT_ILLEGAL(622, "SAMPLE数量配置范围[1,1000000]!"),
    SCRIPT_NO_ENTRY(623, "python脚本必须定义函数begin_alg!!"),
    REACH_RUNNING_DAG(624, "当前task可达正在执行的dag图,不允许修改!!!"),
    PARENT_HAS_NO_TABLE(625, "父节点还未运行生成表，无法清洗"),
    CONTAIN_SPECIAL_CHARACTER(801, "参数不能包含特殊字符!"),
    UDF_NOT_PASS(626, "自定义算子未通过审核"),
    UDF_NO_ENTRY(627, "自定义算子未定义入口函数beginAlg"),
    UDF_PARAM_ERROR(628, "自定义算子输入输出参数定义不完整"),
    GRAPH_DATA_NOT_DONE(900, "数据正在写入图数据库,请稍后再试"),
    GRAPH_EDGE_DUP(901, "相同节点间无法连接多条边"),
    GRAPH_NO_CHILD(902, "网络构建节点无法拥有子节点"),
    GRAPH_NODE_LIMIT(903, "图节点数超过最大限制，请减少数据"),
    GRAPH_LOAD_FORMAT_ERROR(904, "文件格式不支持")
    ;

    int code;
    String message;

    ApiResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
