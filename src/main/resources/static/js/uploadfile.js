$(document).ready(function () {

    let a = $(`#upload`);

    a.css('left', "50px");

    let settings = {
        "url": "http://localhost:8080/files",
        "method": "GET",
        "timeout": 0,
        "headers": {
            "Access-Control-Allow-Origin": 'http://localhost:8080/files'
        },
    };

    $.ajax(settings).done(function (response) {
        addToList(response);
    });

    $('#upload-btn').click(function () {

        let files = $('#files')[0].files;

        if (files.length > 0) {
            let form = new FormData();
            for (let i = 0; i < files.length; i++) {
                // console.log(files[i])
                form.append('files', files[i], files[i].name)
            }

            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");

            $.ajax({
                type: 'POST',
                url: `http://localhost:8080/uploadMultipleFiles`,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                mimeType: "multipart/form-data",
                contentType: false,
                processData: false,
                timeout: 0,
                data: form,
                success: function (response) {
                    let objects = jQuery.parseJSON(response);
                    addToList(objects);
                }
            });
        }
    });

    let addToList = function (response) {
        let FilesDiv = $('#FilesDiv');
        console.log(response);

        for (let i = 0; i < response.length; i++) {
            console.log(response[i])
            let f_name = response[i].fileName;
            let f_createDate = response[i].createDate;
            let f_size = response[i].size;
            let f_link = response[i].fileDownloadUri;

            FilesDiv.append(
                `<a class="containerA" href="` + f_link + `">\n` +
                `    <div class="container container1">\n` +
                `        <span class="containerSpan">` + f_name + `</span>\n` +
                `        <span class="containerSpan">` + f_createDate.substring(0, 10) + `</span>\n` +
                `        <span class="containerSpan">` + Math.floor((f_size / 1024) * 100) / 100 + ` KB</span>\n` +
                `    </div>\n` +
                `</a>`
            )
        }
    }
});