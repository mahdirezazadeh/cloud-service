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
        console.log(response);
    });

    $('#upload-btn').click(function () {

        let files = $('#files')[0].files;

        if (files.length > 0) {
            let form = new FormData();
            for (let i = 0; i < files.length; i++) {
                console.log(files[i])
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
                    console.log(response);
                }
            });
        }
    });
});