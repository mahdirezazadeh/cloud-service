$(document).ready(function () {

    let w = $(`#btnBox`);
    let x = $(`#login`);
    let y = $(`#register`);
    let z = $(`#btn`);

    $('#login-but').click(function () {
        let username = $('#usernameLogin').val();
        let password = $('#passwordLogin').val();

        let auth = "Basic " + btoa(username + ":" + password);

        $.ajax({
            type: 'GET',
            url: `http://localhost:8080/login`,
            timeout: 0,
            headers: {
                Authorization: auth
            },
            success: function (response) {
                console.log(response);
                window.location.replace('http://localhost:8080/main');
            },
            error: function () {
                console.log("error-login")
            }
        });
    });

    $('#register-but').click(function () {
        let User_ID = $('#User_ID').val();
        let Password = $('#Password_register').val();
        let Email = $('#Email').val();
        let First_Name = $('#First_Name').val();
        let Last_Name = $('#Last_Name').val();
        let Phone_Number = $('#Phone_Number').val();

        let token = $("meta[name='_csrf']").attr("content");
        let header = $("meta[name='_csrf_header']").attr("content");

        let userSignUpDTO = {
            "username": User_ID,
            "password": Password,
            "email": Email,
            "firstName": First_Name,
            "lastName": Last_Name,
            "phoneNumber": Phone_Number
        };

        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            'type': 'POST',
            'url': "http://localhost:8080/sign-up",
            'data': JSON.stringify(userSignUpDTO),
            'dataType': 'json',
            'success': function (response) {
                let username = response.username;
                $('#usernameLogin').val(username);
                getLogForm();
            },
            'error': function (error) {
                console.log(error.responseText);
            }
        });
    });


    $('#register-but-form').click(function () {
        getLogForm();
    });

    let getLogForm = function () {
        x.css("left", "-400px");
        y.css("left", "50px");
        z.css("left", "110px");
        $('#box').css('height', '590px')
    }


    $('#login-but-form').click(function () {
        getRegForm();
    });

    let getRegForm = function () {
        x.css("left", "50px");
        y.css("left", "450px");
        z.css("left", "0px");
        $('#box').css('height', '450px')
    }

});