$(function () {
    // 导出pdf
    $('#pdfExportButton').click(function() {
        openExportWindow(window.location.origin + serverPath + "/export/pdf/show/"+ window.file);
    })

    // 导出word
    $('#wordExportButton').click(function () {
        openExportWindow(window.location.origin + serverPath + "/export/word/"+ window.file);
    })

    $('#submit-button').click(function () {
        let searchData = buildSearchParams();

        var loading = layer.load(2);
        $.ajax({
            url: serverPath + "/view/html/load/" + window.file,
            type: 'POST',
            data: searchData,
            dataType: "json",
            success: function (res) {
                let report = res.data;
                const tableContainer = $(`#keep_report_table`);
                tableContainer.empty();
                tableContainer.append(report.content);

                layer.close(loading);
            },
            error: function (response) {
                layer.close(loading);

                if (response && response.responseText) {
                    layer.open({
                        title: '错误提示',
                        content: '服务端错误：' + response.responseText
                    });
                } else {
                    layer.alert('查询失败！')
                }
            }
        });
    })

    /**
     * 构建查询参数
     *
     * @returns {{}}
     */
     function buildSearchParams() {
        let searchData = {};
        for (let fun of window.formControlFuns) {
            const json = fun.call(this);
            for (let key in json) {
                let value = json[key];
                searchData[key] = value;
            }
        }

        return searchData;
    }

    /**
     * 打开导出窗口
     *
     * @param url
     */
    function openExportWindow(url) {
        let searchData = buildSearchParams();
        let paramStr = $.param(searchData);
        if (paramStr != null && paramStr != "") {
            paramStr = "?" + paramStr;
        }

        window.open(url + paramStr, "_blank");
    }
})