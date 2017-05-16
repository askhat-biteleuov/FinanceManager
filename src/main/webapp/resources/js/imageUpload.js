$(document).ready(function () {
    var options = {
        type: 'POST',
        beforeSend: function (request) {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            request.setRequestHeader(header, token);
        },
        url:'/profile/avatar',
        contentType: 'application/json; charset=UTF-8',
        success:  function (data) {
            console.log(data);
        }
    };

    // $('.uploadImage').ajaxForm(options);
    $('.uploadImage').on('submit', function (event) {
        event.preventDefault();
        $(this).ajaxSubmit(options);
    });
    // $('.uploadImage').on('submit', function (event) {
    //     var formData = new FormData(this);
    //     event.preventDefault();
    //
    //     $.ajax({
    //         type: 'POST',
    //         beforeSend: function (request) {
    //             var token = $("meta[name='_csrf']").attr("content");
    //             var header = $("meta[name='_csrf_header']").attr("content");
    //             request.setRequestHeader(header, token);
    //         },
    //         contentType: 'application/json; charset=UTF-8',
    //         url: "/profile/avatar",
    //         data: formData
    //     }).done(function (data) {
    //         console.log("image saved")
    //     });
});
